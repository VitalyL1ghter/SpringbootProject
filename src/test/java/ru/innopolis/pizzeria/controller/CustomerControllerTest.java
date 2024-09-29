package ru.innopolis.pizzeria.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.innopolis.pizzeria.dto.CustomerDto;
import ru.innopolis.pizzeria.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ru.innopolis.pizzeria.utils.UtilsJson.asJsonString;


@DisplayName(value = "Customer Controller test")

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private final List<CustomerDto> customers = new ArrayList<>();

    @BeforeEach
    public void initialization() {
        CustomerDto firstCustomer = new CustomerDto();
        firstCustomer.setCustomerId(20L);
        firstCustomer.setFirstName("Олег");
        firstCustomer.setLastName("Моськин");
        firstCustomer.setPhoneNumber("89179000000");
        firstCustomer.setAddressMail("ol@mail.ru");
        firstCustomer.setCity("Казань");
        firstCustomer.setStreet("Павлюхина");
        firstCustomer.setNumberHouseAndApartment("10/10");
        firstCustomer.setSoftDeletedFlag(false);

        CustomerDto secondCustomer = new CustomerDto();
        secondCustomer.setCustomerId(30L);
        secondCustomer.setFirstName("Петр");
        secondCustomer.setLastName("Петров");
        secondCustomer.setPhoneNumber("89179111111");
        secondCustomer.setAddressMail("pet@yandex.ru");
        secondCustomer.setCity("Казань");
        secondCustomer.setStreet("Мавлютова");
        secondCustomer.setNumberHouseAndApartment("20/20");
        secondCustomer.setSoftDeletedFlag(false);

        customers.add(firstCustomer);
        customers.add(secondCustomer);

    }

    @AfterEach
    public void tearDown() {
        customers.clear();
    }

    @DisplayName(value = "Получение всех покупателей")
    @Test
    void getAll() throws Exception {
        Mockito.when(this.customerService.getAll()).thenReturn(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @DisplayName(value = "Поиск покупателя по ID")
    @Test
    void getCustomersById() throws Exception {

        Mockito.when(this.customerService.getCustomerById(20L)).thenReturn(customers.get(0));

        mockMvc.perform(get("/customers/findById/{customer-id}", 20L))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.customerId").value(20L))
                .andExpect(jsonPath("$.firstName").value("Олег"))
                .andExpect(jsonPath("$.lastName").value("Моськин"))
                .andExpect(jsonPath("$.phoneNumber").value("89179000000"))
                .andExpect(jsonPath("$.addressMail").value("ol@mail.ru"))
                .andExpect(jsonPath("$.city").value("Казань"))
                .andExpect(jsonPath("$.street").value("Павлюхина"))
                .andExpect(jsonPath("$.numberHouseAndApartment").value("10/10"));


    }

    @DisplayName(value = "Добавление покупателя")
    @Test
    void addNewCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(40L);
        customerDto.setFirstName("Илья");
        customerDto.setLastName("Козлов");
        customerDto.setPhoneNumber("89179555555");
        customerDto.setAddressMail("il@yandex.ru");
        customerDto.setCity("Казань");
        customerDto.setStreet("Декабристов");
        customerDto.setNumberHouseAndApartment("79/34");
        customerDto.setSoftDeletedFlag(false);
        customers.add(customerDto);
        when(this.customerService.addCustomer(customerDto)).thenReturn(customerDto);


        mockMvc.perform(post("/customers/add")
                        .content(asJsonString(customerDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(40L))
                .andExpect(jsonPath("$.firstName").value("Илья"))
                .andExpect(jsonPath("$.lastName").value("Козлов"))
                .andExpect(jsonPath("$.phoneNumber").value("89179555555"))
                .andExpect(jsonPath("$.addressMail").value("il@yandex.ru"))
                .andExpect(jsonPath("$.city").value("Казань"));

    }

    @DisplayName(value = "Тест на обновление данных покупателя")
    @Test
    void updateCustomerId() throws Exception {
        CustomerDto customerDtoNew = customers.get(0);
        customerDtoNew.setPhoneNumber("89179777777");
        customerDtoNew.setAddressMail("yyyy@mail.ru");
        when(customerService.updateCustomer(20L, customerDtoNew)).thenReturn(customerDtoNew);
        mockMvc.perform(put("/customers/update/{customer-id}", 20L)
                        .content(asJsonString(customerDtoNew))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(20L))
                .andExpect(jsonPath("$.phoneNumber").value("89179777777"))
                .andExpect(jsonPath("$.addressMail").value("yyyy@mail.ru"))
                .andExpect(status().isAccepted());

    }

    @DisplayName(value = "Тест на удаление")

    @Test
    void hardDeleteById() throws Exception {

        willDoNothing().given(this.customerService).deleteCustomerById(20L);

        mockMvc.perform(delete("/customers/delete/{customer-id}", customers.get(0).getCustomerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }

    @DisplayName(value = "Тест на soft удаление")
    @Test
    void softDeleteById() throws Exception {
        willDoNothing().given(this.customerService).softDeleteCustomerById(30L);
        mockMvc.perform(
                        put("/customers/softDelete/{customer-id}", 30L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }
}
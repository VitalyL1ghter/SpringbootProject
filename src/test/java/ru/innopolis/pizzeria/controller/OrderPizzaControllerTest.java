package ru.innopolis.pizzeria.controller;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.innopolis.pizzeria.dto.CustomerDto;
import ru.innopolis.pizzeria.dto.OrderPizzaAllDto;
import ru.innopolis.pizzeria.dto.OrderPizzaDto;
import ru.innopolis.pizzeria.dto.PizzaDto;
import ru.innopolis.pizzeria.service.OrderPizzaService;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.innopolis.pizzeria.utils.UtilsJson.asJsonString;

@DisplayName(value = "OrderPizza Controller test")
@WebMvcTest(OrderPizzaController.class)
class OrderPizzaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OrderPizzaService orderPizzaService;

    private final List<OrderPizzaDto> orders = new ArrayList<>();

    private final List<OrderPizzaAllDto> ordersNew = new ArrayList<>();


    @BeforeEach
    public void init() {
        PizzaDto firstPizza = PizzaDto.builder()
                .pizzaId(10L)
                .pizzaName("Папая")
                .ingredients("помидоры, сыр, колбаса")
                .size("40 см")
                .costPizza(300)
                .build();
        PizzaDto secondPizza = PizzaDto.builder()
                .pizzaId(20L)
                .pizzaName("Острая")
                .ingredients("Сыр, грибы, чили перец, ветчина")
                .size("32 см")
                .costPizza(600)
                .build();


        CustomerDto
                firstCustomer = new CustomerDto();
        firstCustomer.setCustomerId(20L);
        firstCustomer.setFirstName("Олег");
        firstCustomer.setLastName("Моськин");
        firstCustomer.setPhoneNumber("89179000000");
        firstCustomer.setAddressMail("ol@mail.ru");
        firstCustomer.setSoftDeletedFlag(false);
        firstCustomer.setCity("Казань");
        firstCustomer.setStreet("Павлюхина");
        firstCustomer.setNumberHouseAndApartment("10/10");

        CustomerDto secondCustomer = new CustomerDto();
        secondCustomer.setCustomerId(30L);
        secondCustomer.setFirstName("Петр");
        secondCustomer.setLastName("Петров");
        secondCustomer.setPhoneNumber("89179111111");
        secondCustomer.setAddressMail("pet@yandex.ru");
        secondCustomer.setSoftDeletedFlag(false);
        secondCustomer.setCity("Казань");
        secondCustomer.setStreet("Мавлютова");
        secondCustomer.setNumberHouseAndApartment("20/20");


        OrderPizzaDto firstOrderPizza = OrderPizzaDto.builder()
                .orderPizzaId(1L)
                .customerId(firstCustomer.getCustomerId())
                .pizzaId(firstPizza.getPizzaId())
                .amount(1)
                .build();

        OrderPizzaDto secondOrderPizza = OrderPizzaDto.builder()
                .orderPizzaId(2L)
                .customerId(secondCustomer.getCustomerId())
                .pizzaId(secondPizza.getPizzaId())
                .amount(2)
                .build();
        orders.add(firstOrderPizza);
        orders.add(secondOrderPizza);

        OrderPizzaAllDto orderPizzaAllDto = OrderPizzaAllDto.builder()
                .ordersPizza(orders)
                .customer(firstCustomer)
                .customer(secondCustomer)
                .build();
        ordersNew.add(orderPizzaAllDto);

    }


    @AfterEach
    public void tearDown() {
        orders.clear();
    }

    @DisplayName(value = "Тест на получение всех заказов")
    @Test
    void getAllOrders() throws Exception {
        when(this.orderPizzaService.getAll()).thenReturn(ordersNew);
        mockMvc.perform(get("/orders"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(1));

    }

    @DisplayName(value = "Тест на добавление заказа")
    @Test
    void addNewOrder() throws Exception {

        OrderPizzaDto orderPizzaDto = new OrderPizzaDto();
        orderPizzaDto.setOrderPizzaId(3L);
        orderPizzaDto.setCustomerId(20L);
        orderPizzaDto.setPizzaId(10L);
        orderPizzaDto.setAmount(2);

        when(orderPizzaService.addOrderPizza(orderPizzaDto)).thenReturn(orderPizzaDto);
        mockMvc.perform(post("/orders/add")
                        .content(asJsonString(orderPizzaDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    @DisplayName(value = "Тест на получение заказа по ID")
    @Test
    void getOrderById() throws Exception {

        when(this.orderPizzaService.getOrderPizzaById(1L)).thenReturn(orders.get(0));

        mockMvc.perform(get("/orders/findById/{order-id}", 1L))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.orderPizzaId").value(1L))
                .andExpect(jsonPath("$.customerId").value(20L))
                .andExpect(jsonPath("$.pizzaId").value(10L))
                .andExpect(jsonPath("$.amount").value(1));


    }

    @DisplayName(value = "Тест на удаление заказа")
    @Test
    void deleteOrderById() throws Exception {


        willDoNothing().given(this.orderPizzaService).deleteOrderPizzaById(2L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/delete/{order-id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}
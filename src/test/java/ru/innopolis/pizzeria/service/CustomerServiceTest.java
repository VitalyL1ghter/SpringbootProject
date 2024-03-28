package ru.innopolis.pizzeria.service;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.innopolis.pizzeria.dto.CustomerDto;
import ru.innopolis.pizzeria.model.Customer;
import ru.innopolis.pizzeria.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName(value = "Customer Service test")
@SpringBootTest
class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService = new CustomerServiceImpl(customerRepository);
    private final List<Customer> customers = new ArrayList<>();
    private Customer firstCustomer;
    private Customer secondCustomer;


    @BeforeEach
    public void initialization() {
        firstCustomer = new Customer();
        firstCustomer.setCustomerId(20L);
        firstCustomer.setFirstName("Олег");
        firstCustomer.setLastName("Моськин");
        firstCustomer.setPhoneNumber("89179000000");
        firstCustomer.setAddressMail("ol@mail.ru");
        firstCustomer.setSoftDeletedFlag(false);
        firstCustomer.setCity("Казань");
        firstCustomer.setStreet("Павлюхина");
        firstCustomer.setNumberHouseAndApartment("10/10");


        secondCustomer = new Customer();
        secondCustomer.setCustomerId(30L);
        secondCustomer.setFirstName("Петр");
        secondCustomer.setLastName("Петров");
        secondCustomer.setPhoneNumber("89179111111");
        secondCustomer.setAddressMail("pet@yandex.ru");
        secondCustomer.setSoftDeletedFlag(false);
        secondCustomer.setCity("Казань");
        secondCustomer.setStreet("Мавлютова");
        secondCustomer.setNumberHouseAndApartment("20/20");
        customers.add(firstCustomer);
        customers.add(secondCustomer);

    }

    @AfterEach
    public void tearDown() {
        customers.clear();
    }

    @Test
    @DisplayName(value = "Поиск всех пользоателей")
    void getAll() {

        when(customerRepository.findAll()).
                thenReturn(customers);
        assertNotNull(customers);
        Assertions.assertEquals(customers.size(), 2);
        Assertions.assertEquals(customers.get(0).getFirstName(), "Олег");

    }

    @Test
    @DisplayName(value = "Тест на получение всех пользователей")
    void getCustomerById() {

        when(customerRepository.findById(20L)).thenReturn(Optional.of(firstCustomer));
        CustomerDto customerFound = customerService.getCustomerById(20L);

        assertNotNull(customerFound);
        Assertions.assertEquals(firstCustomer.getCustomerId(), customerFound.getCustomerId());
        Assertions.assertEquals(firstCustomer.getFirstName(), customerFound.getFirstName());
        Assertions.assertEquals(firstCustomer.getPhoneNumber(), customerFound.getPhoneNumber());
        Assertions.assertEquals(firstCustomer.getCity(), customerFound.getCity());

    }

    @Test
    @DisplayName(value = "Тест на добавление пользователя")
    void addCustomer() {
        Customer thirdCustomer = new Customer();
        when(customerRepository.save(thirdCustomer)).thenReturn(thirdCustomer);

        thirdCustomer.setCustomerId(50L);
        thirdCustomer.setFirstName("Елена");
        thirdCustomer.setLastName("Петрова");
        thirdCustomer.setPhoneNumber("89179333444");
        thirdCustomer.setAddressMail("el@mail.ru");
        thirdCustomer.setSoftDeletedFlag(false);
        thirdCustomer.setCity("Казань");
        thirdCustomer.setStreet("Арбузова");
        thirdCustomer.setNumberHouseAndApartment("144/56");
        Assertions.assertEquals(thirdCustomer.getLastName(), "Петрова");

    }
    @Test
    @DisplayName(value = "Тест на изменение данных пользователя")
    void updateCustomer() {

        customerRepository.save(secondCustomer);
        secondCustomer.setAddressMail("rrr@vvv.ru");
        verify(customerRepository).save(secondCustomer);
        Assertions.assertEquals(secondCustomer.getAddressMail(), "rrr@vvv.ru");

    }

    @Test
    @DisplayName(value = "Тест на удаление пользователя по ID")
    void deleteCustomerById() {
        when(customerRepository.findById(20L)).thenReturn(Optional.of(firstCustomer));

        customerService.deleteCustomerById(firstCustomer.getCustomerId());

        verify(customerRepository, Mockito.times(1))
                .deleteById(firstCustomer.getCustomerId());

    }

    @Test
    @DisplayName(value = "Тест на soft удаление пользователя")
    void softDeleteCustomerById() {
        when(customerRepository.findById(secondCustomer.getCustomerId()))
                .thenReturn(Optional.of(secondCustomer));

        customerService.softDeleteCustomerById(secondCustomer.getCustomerId());

        verify(customerRepository, times(1))
                .findById(secondCustomer.getCustomerId());
        Assertions.assertEquals(secondCustomer.getSoftDeletedFlag(), true);
    }
}
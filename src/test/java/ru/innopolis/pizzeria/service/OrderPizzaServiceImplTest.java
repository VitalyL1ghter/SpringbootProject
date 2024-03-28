package ru.innopolis.pizzeria.service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.innopolis.pizzeria.dto.OrderPizzaDto;
import ru.innopolis.pizzeria.model.Customer;
import ru.innopolis.pizzeria.model.OrderPizza;
import ru.innopolis.pizzeria.model.Pizza;
import ru.innopolis.pizzeria.repository.CustomerRepository;
import ru.innopolis.pizzeria.repository.OrderPizzaRepository;
import ru.innopolis.pizzeria.repository.PizzaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName(value = "OrderPizza Service test")
@SpringBootTest
class OrderPizzaServiceImplTest {

    @MockBean
    private OrderPizzaRepository orderPizzaRepository;

    @MockBean
    private PizzaRepository pizzaRepository;

    @MockBean
    private CustomerRepository customerRepository;
    @Autowired
    private OrderPizzaService orderPizzaService = new OrderPizzaServiceImpl(orderPizzaRepository, pizzaRepository, customerRepository);
    private final List<OrderPizza> orderPizzas = new ArrayList<>();
    private OrderPizza firstOrderPizza;
    private OrderPizza secondOrderPizza;

    private final List<Customer> customers = new ArrayList<>();

    private Customer firstCustomer;
    private Customer secondCustomer;

    private final List<Pizza> pizzas = new ArrayList<>();
    private Pizza firstPizza;
    private Pizza secondPizza;

    @BeforeEach
    public void init() {

        firstPizza = Pizza.builder()
                .pizzaId(10L)
                .pizzaName("Папая")
                .ingredients("помидоры, сыр, колбаса")
                .size("40 см")
                .costPizza(300)
                .build();

        secondPizza = Pizza.builder()
                .pizzaId(20L)
                .pizzaName("Острая")
                .ingredients("Сыр, грибы, чили перец, ветчина")
                .size("32 см")
                .costPizza(600)
                .build();

        pizzas.add(firstPizza);
        pizzas.add(secondPizza);

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

        firstOrderPizza = OrderPizza.builder()
                .orderPizzaId(1L)
                .customer(firstCustomer)
                .pizza(firstPizza)
                .amount(1)
                .dateOrder(LocalDateTime.now())
                .build();

        secondOrderPizza = OrderPizza.builder()
                .orderPizzaId(2L)
                .customer(secondCustomer)
                .pizza(secondPizza)
                .amount(2)
                .dateOrder(LocalDateTime.now())
                .build();
        orderPizzas.add(firstOrderPizza);
        orderPizzas.add(secondOrderPizza);

    }


    @AfterEach
    public void tearDown() {
        pizzas.clear();
        customers.clear();
        orderPizzas.clear();
    }


    @Test
    void getAll() {
        when(orderPizzaRepository.findAll()).
                thenReturn(orderPizzas);
        assertNotNull(orderPizzas);
        Assertions.assertEquals(orderPizzas.size(), 2);

    }

    @DisplayName(value = "Тест на поиск заказа по ID")
    @Test
    void getOrderPizzaById() {
        when(orderPizzaRepository.findById(2L)).thenReturn(Optional.of(secondOrderPizza));
        OrderPizzaDto orderPizzaDto = orderPizzaService.getOrderPizzaById(2L);

        Assertions.assertNotNull(orderPizzaDto);
    }


    @DisplayName(value = "Тест на добавление заказа")
    @Test
    void addOrderPizza() {
        OrderPizza thirdOrderPizza = new OrderPizza();
        when(orderPizzaRepository.save(thirdOrderPizza)).thenReturn(thirdOrderPizza);

        thirdOrderPizza.setCustomer(firstCustomer);
        thirdOrderPizza.setPizza(firstPizza);
        thirdOrderPizza.setAmount(3);
        thirdOrderPizza.setDateOrder(LocalDateTime.now());
        thirdOrderPizza.setOrderPizzaId(3L);
        Assertions.assertEquals(thirdOrderPizza.getCustomer().getCustomerId(), 20L);
        Assertions.assertEquals(thirdOrderPizza.getPizza().getPizzaId(), 10L);

    }

    @DisplayName(value = "Тест на удаление заказа по ID")
    @Test
    void deleteOrderPizzaById() {

        when(orderPizzaRepository.findById(2L)).thenReturn(Optional.of(secondOrderPizza));
        orderPizzaService.deleteOrderPizzaById(secondOrderPizza.getOrderPizzaId());
        verify(orderPizzaRepository, Mockito.times(1))
                .deleteById(secondOrderPizza.getOrderPizzaId());
    }
}
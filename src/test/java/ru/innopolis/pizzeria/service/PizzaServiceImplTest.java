package ru.innopolis.pizzeria.service;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.innopolis.pizzeria.dto.PizzaDto;
import ru.innopolis.pizzeria.model.Pizza;
import ru.innopolis.pizzeria.repository.PizzaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DisplayName(value = "Pizza Service test")
@SpringBootTest
class PizzaServiceImplTest {

    @MockBean
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaService pizzaService = new PizzaServiceImpl(pizzaRepository);

    private final List<Pizza> pizzas = new ArrayList<>();
    private Pizza firstPizza;
    private Pizza secondPizza;

    @BeforeEach
    public void initPizza() {
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
    }

    @AfterEach
    public void tearDown() {
        pizzas.clear();
    }

    @DisplayName(value = "Тест на получение всех пицц")
    @Test
    void getAll() {
        when(pizzaRepository.findAll()).
                thenReturn(pizzas);
        assertNotNull(pizzas);
        Assertions.assertEquals(pizzas.size(), 2);
        Assertions.assertEquals(pizzas.get(0).getPizzaName(), "Папая");

    }

    @DisplayName(value = "Тест на получение пиццы по ID")
    @Test
    void getPizzaById() {
        when(pizzaRepository.findById(20L)).thenReturn(Optional.of(firstPizza));
        PizzaDto pizzaFound = pizzaService.getPizzaById(20L);

        assertNotNull(pizzaFound);
        Assertions.assertEquals(firstPizza.getPizzaId(), pizzaFound.getPizzaId());
        Assertions.assertEquals(firstPizza.getPizzaName(), pizzaFound.getPizzaName());
        Assertions.assertEquals(firstPizza.getSize(), pizzaFound.getSize());
        Assertions.assertEquals(firstPizza.getCostPizza(), pizzaFound.getCostPizza());

    }

    @DisplayName(value = "Тест на изменение данных пиццы")
    @Test
    void updatePizza() {
        pizzaRepository.save(secondPizza);
        secondPizza.setCostPizza(900);
        verify(pizzaRepository).save(secondPizza);
        Assertions.assertEquals(secondPizza.getCostPizza(), 900);

    }

    @DisplayName(value = "Тест на добавление пиццы")
    @Test
    void addPizza() {
        Pizza thirdPizza = new Pizza();
        when(pizzaRepository.save(thirdPizza)).thenReturn(thirdPizza);

        thirdPizza.setPizzaId(40L);
        thirdPizza.setPizzaName("Сытная");
        thirdPizza.setIngredients("Сыр, лук, томаты");
        thirdPizza.setSize("40 см");
        thirdPizza.setCostPizza(600);
        Assertions.assertEquals(thirdPizza.getPizzaName(), "Сытная");
        Assertions.assertEquals(thirdPizza.getCostPizza(), 600);


    }

    @DisplayName(value = "Тест на удаление пиццы по ID")
    @Test
    void deletePizzaById() {
        when(pizzaRepository.findById(20L)).thenReturn(Optional.of(firstPizza));
        pizzaService.deletePizzaById(firstPizza.getPizzaId());
        verify(pizzaRepository, Mockito.times(1))
                .deleteById(firstPizza.getPizzaId());

    }
}
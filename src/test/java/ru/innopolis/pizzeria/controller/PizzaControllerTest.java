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
import ru.innopolis.pizzeria.dto.PizzaDto;
import ru.innopolis.pizzeria.service.PizzaService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.innopolis.pizzeria.Utils.UtilsJson.asJsonString;

@DisplayName(value = "Pizza Controller test")

@WebMvcTest(PizzaController.class)
class PizzaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PizzaService pizzaService;

    private final List<PizzaDto> pizzas = new ArrayList<>();

    @BeforeEach
    public void initialization() {
        PizzaDto firstPizza = new PizzaDto();
        firstPizza.setPizzaId(20L);
        firstPizza.setPizzaName("Мясая");
        firstPizza.setIngredients("Помидоры, сыр, огурцы, перец");
        firstPizza.setSize("32 см");
        firstPizza.setCostPizza(100);


        PizzaDto secondPizza = new PizzaDto();
        secondPizza.setPizzaId(30L);
        secondPizza.setPizzaName("Семейная");
        secondPizza.setIngredients("Томаты, оливки, сыр, анчоус");
        secondPizza.setSize("40 см");
        secondPizza.setCostPizza(200);

        pizzas.add(firstPizza);
        pizzas.add(secondPizza);

    }

    @AfterEach
    public void tearDown() {
        pizzas.clear();
    }

    @DisplayName(value = "Тест на получение всех пицц")
    @Test
    void getPizzaFindAll() throws Exception {
        Mockito.when(this.pizzaService.getAll()).thenReturn(pizzas);

        mockMvc.perform(get("/pizzas"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    void addNewPizza() throws Exception {
        PizzaDto pizzaDto = new PizzaDto();
        pizzaDto.setPizzaId(40L);
        pizzaDto.setPizzaName("Ассорти");
        pizzaDto.setIngredients("Томаты, колбаса, оливки, сыр");
        pizzaDto.setSize("40 см");
        pizzaDto.setCostPizza(300);
        pizzas.add(pizzaDto);
        when(this.pizzaService.addPizza(pizzaDto)).thenReturn(pizzaDto);

        mockMvc.perform(post("/pizzas/add")
                        .content(asJsonString(pizzaDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pizzaId").value(40L))
                .andExpect(jsonPath("$.pizzaName").value("Ассорти"))
                .andExpect(jsonPath("$.ingredients").value("Томаты, колбаса, оливки, сыр"))
                .andExpect(jsonPath("$.size").value("40 см"))
                .andExpect(jsonPath("$.costPizza").value(300));


    }

    @DisplayName(value = "Тест на обновление пиццы")
    @Test
    void updatePizza() throws Exception {

        PizzaDto pizzaDtoNew = pizzas.get(0);
        pizzaDtoNew.setPizzaId(20L);
        pizzaDtoNew.setPizzaName("Лайт");
        pizzaDtoNew.setCostPizza(600);
        when(pizzaService.updatePizza(20L, pizzaDtoNew)).thenReturn(pizzaDtoNew);
        mockMvc.perform(put("/pizzas/update/{pizza-id}", 20L)
                        .content(asJsonString(pizzaDtoNew))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pizzaId").value(20L))
                .andExpect(jsonPath("$.pizzaName").value("Лайт"))
                .andExpect(jsonPath("$.costPizza").value(600))
                .andExpect(status().isAccepted());

    }

    @DisplayName(value = "Тест на удалление по ID")

    @Test
    void deletePizzaById() throws Exception {

        willDoNothing().given(this.pizzaService).deletePizzaById(20L);
        mockMvc.perform(delete("/pizzas/delete/{pizza-id}", pizzas.get(0).getPizzaId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());


    }

}
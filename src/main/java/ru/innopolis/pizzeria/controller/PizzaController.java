package ru.innopolis.pizzeria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.pizzeria.dto.PizzaDto;
import ru.innopolis.pizzeria.service.PizzaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pizzas")
@Tag(name = "Контроллер пицц", description = "Контроллер CRUD работы с пиццами на сайте")

public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping()
    @Operation(summary = "Получение списка всех пицц")
    public ResponseEntity<List<PizzaDto>> getPizzaFindAll() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(pizzaService.getAll());
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление пиццы", description = "Заполнить данные пиццы")
    public ResponseEntity<PizzaDto> addNewPizza(@Valid @RequestBody PizzaDto pizza) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pizzaService.addPizza((pizza)));
    }

    @PutMapping("/update/{pizza-id}")
    @Operation(summary = "Изменнение данных пиццы", description = "Введите изменяемые данные")
    public ResponseEntity<PizzaDto> updatePizza(@Valid @RequestBody PizzaDto pizza,
                                                @PathVariable("pizza-id") @Parameter(description = "Идентификатор пиццы")Long id) {

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(pizzaService.updatePizza(id, pizza));
    }

    @DeleteMapping("/delete/{pizza-id}")
    @Operation(summary = "Удаление пиццы", description = "Задать ID пиццы")
    public ResponseEntity<List<PizzaDto>> deleteDeleteById(@PathVariable("pizza-id") @Parameter(description = "Идентификатор пиццы")Long id) {
        pizzaService.deletePizzaById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(pizzaService.getAll());

    }
}

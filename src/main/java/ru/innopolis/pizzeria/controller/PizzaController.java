package ru.innopolis.pizzeria.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.pizzeria.dto.PizzaDto;
import ru.innopolis.pizzeria.service.PizzaService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pizzas")

public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping()
    public ResponseEntity<List<PizzaDto>> getPizzaFindAll() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(pizzaService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<PizzaDto> addNewPizza(@RequestBody PizzaDto pizza) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pizzaService.addPizza((pizza)));
    }

    @PutMapping("/update/{pizza-id}")
    public ResponseEntity<PizzaDto> updatePizza(@RequestBody PizzaDto pizza,
                                                @PathVariable("pizza-id") Long id) {

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(pizzaService.updatePizza(id, pizza));
    }

    @DeleteMapping("/delete/{pizza-id}")
    public ResponseEntity<List<PizzaDto>> deleteDeleteById(@PathVariable("pizza-id") Long id) {
        pizzaService.deletePizzaById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(pizzaService.getAll());

    }
}

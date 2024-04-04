package ru.innopolis.pizzeria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.pizzeria.dto.OrderPizzaAllDto;
import ru.innopolis.pizzeria.dto.OrderPizzaDto;
import ru.innopolis.pizzeria.service.OrderPizzaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "Контроллер заказа", description = "Контроллер CRUD работы с заказом на сайте")
public class OrderPizzaController {

    private final OrderPizzaService orderPizzaService;

    @GetMapping("")
    @Operation(summary = "Получение списка всех заказов")
    public ResponseEntity<List<OrderPizzaAllDto>> getAllOrders() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(orderPizzaService.getAll());
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление заказа", description = "Заполнить данные заказа")
    public ResponseEntity<OrderPizzaDto> addNewOrder(@Valid @RequestBody OrderPizzaDto orderPizza) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderPizzaService.addOrderPizza(orderPizza));
    }

    @GetMapping("/findById/{order-id}")
    @Operation(summary = "Получение заказа по ID", description = "Задать ID для получения данных заказа")
        public ResponseEntity<OrderPizzaDto> getOrderById(@PathVariable("order-id") @Parameter(description = "Идентификатор заказа") Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(orderPizzaService.getOrderPizzaById(id));
    }

    @DeleteMapping("/delete/{order-id}")
    @Operation(summary = "Удаление заказа", description = "Задать ID заказа")
    public ResponseEntity<List<OrderPizzaAllDto>> deleteOrderById(@PathVariable("order-id") @Parameter(description = "Идентификатор заказа") Long id) {
        orderPizzaService.deleteOrderPizzaById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(orderPizzaService.getAll());
    }


}



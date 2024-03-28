package ru.innopolis.pizzeria.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.pizzeria.dto.OrderPizzaAllDto;
import ru.innopolis.pizzeria.dto.OrderPizzaDto;
import ru.innopolis.pizzeria.service.OrderPizzaService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderPizzaController {

    private final OrderPizzaService orderPizzaService;

    @GetMapping("")
    public ResponseEntity<List<OrderPizzaAllDto>> getAllOrders() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(orderPizzaService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<OrderPizzaDto> addNewOrder(@RequestBody OrderPizzaDto orderPizza) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderPizzaService.addOrderPizza(orderPizza));
    }

    @GetMapping("/findById/{order-id}")
    public ResponseEntity<OrderPizzaDto> getOrderById(@PathVariable("order-id") Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(orderPizzaService.getOrderPizzaById(id));
    }

    @DeleteMapping("/delete/{order-id}")
    public ResponseEntity<List<OrderPizzaAllDto>> deleteOrderById(@PathVariable("order-id") Long id) {
        orderPizzaService.deleteOrderPizzaById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(orderPizzaService.getAll());
    }


}



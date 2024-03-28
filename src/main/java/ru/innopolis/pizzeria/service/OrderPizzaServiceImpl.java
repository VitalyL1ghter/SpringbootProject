package ru.innopolis.pizzeria.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innopolis.pizzeria.dto.CustomerDto;
import ru.innopolis.pizzeria.dto.OrderPizzaAllDto;
import ru.innopolis.pizzeria.dto.OrderPizzaDto;
import ru.innopolis.pizzeria.model.OrderPizza;
import ru.innopolis.pizzeria.model.Pizza;
import ru.innopolis.pizzeria.repository.CustomerRepository;
import ru.innopolis.pizzeria.repository.OrderPizzaRepository;
import ru.innopolis.pizzeria.repository.PizzaRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.innopolis.pizzeria.dto.OrderPizzaDto.from;

@Service
@RequiredArgsConstructor
public class OrderPizzaServiceImpl implements OrderPizzaService {

    private final OrderPizzaRepository orderPizzaRepository;
    private final PizzaRepository pizzaRepository;
    private final CustomerRepository customerRepository;


    @Override
    public List<OrderPizzaAllDto> getAll() {
        return customerRepository.findAll().stream()
                .map(customer -> OrderPizzaAllDto.builder()
                        .customer(CustomerDto.from(customer))
                        .ordersPizza(orderPizzaRepository.findAllByCustomer(customer)
                                .stream()
                                .map(order -> from(order))
                                .collect(Collectors.toList()))
                        .build()).collect(Collectors.toList());
    }

    @Override
    public OrderPizzaDto getOrderPizzaById(Long id) {
        return from(orderPizzaRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    public OrderPizzaDto addOrderPizza(OrderPizzaDto orderPizza) {
        Pizza pizza = pizzaRepository.findById(orderPizza.getPizzaId()).orElseThrow(RuntimeException::new);
        return from(orderPizzaRepository.save(OrderPizza.builder()
                .orderPizzaId(orderPizza.getOrderPizzaId())
                .pizza(pizza)
                .customer(customerRepository.getReferenceById(orderPizza.getCustomerId()))
                .orderPrice(pizza.getCostPizza() * orderPizza.getAmount())
                .amount(orderPizza.getAmount())
                .dateOrder(LocalDateTime.now())
                .build()));

    }

    @Override
    public void deleteOrderPizzaById(Long id) {
        orderPizzaRepository.deleteById(id);

    }


}



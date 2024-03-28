package ru.innopolis.pizzeria.service;
import org.springframework.stereotype.Service;
import ru.innopolis.pizzeria.dto.OrderPizzaAllDto;
import ru.innopolis.pizzeria.dto.OrderPizzaDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface OrderPizzaService {

    List<OrderPizzaAllDto> getAll();

    OrderPizzaDto getOrderPizzaById (Long id);

    OrderPizzaDto addOrderPizza (OrderPizzaDto orderPizza);

    void deleteOrderPizzaById(Long id);

}

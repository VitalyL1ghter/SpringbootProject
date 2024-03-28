package ru.innopolis.pizzeria.service;
import org.springframework.stereotype.Service;
import ru.innopolis.pizzeria.dto.PizzaDto;
import java.util.List;

@Service
public interface PizzaService {

    List<PizzaDto> getAll();

    PizzaDto getPizzaById (Long id);

    PizzaDto updatePizza (Long id, PizzaDto pizza);

    PizzaDto addPizza(PizzaDto pizza);

    void deletePizzaById(Long id);

}

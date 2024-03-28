package ru.innopolis.pizzeria.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innopolis.pizzeria.dto.PizzaDto;
import ru.innopolis.pizzeria.model.Pizza;
import ru.innopolis.pizzeria.repository.PizzaRepository;
import java.util.List;
import static ru.innopolis.pizzeria.dto.PizzaDto.from;

@Service
@RequiredArgsConstructor
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;

    @Override
    public List<PizzaDto> getAll() {

        return from(pizzaRepository.findAll());
    }

    @Override
    public PizzaDto getPizzaById(Long id) {
        return from(pizzaRepository.findById(id).orElseThrow(RuntimeException::new));
    }


    @Override
    public PizzaDto updatePizza(Long id, PizzaDto pizza) {
        return from(pizzaRepository.save(Pizza.builder()
                .pizzaId(pizza.getPizzaId())
                .pizzaName(pizza.getPizzaName())
                .ingredients(pizza.getIngredients())
                .size(pizza.getSize())
                .costPizza(pizza.getCostPizza())
                .build()));
    }

    @Override
    public PizzaDto addPizza(PizzaDto pizza) {
        return from(pizzaRepository.save(Pizza.builder()
                .pizzaId(pizza.getPizzaId())
                .pizzaName(pizza.getPizzaName())
                .ingredients(pizza.getIngredients())
                .size(pizza.getSize())
                .costPizza(pizza.getCostPizza())
                .build()));
    }

    @Override
    public void deletePizzaById(Long id) {
        pizzaRepository.deleteById(id);

    }
}

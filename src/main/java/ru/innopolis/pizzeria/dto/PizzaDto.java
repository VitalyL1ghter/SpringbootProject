package ru.innopolis.pizzeria.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.pizzeria.model.Pizza;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PizzaDto {

    private Long pizzaId;
    @NotBlank(message = "Поле должно быть заполнено")
    private String pizzaName;
    @NotBlank(message = "Поле должно быть заполнено")
    private String ingredients;
    @NotBlank(message = "Поле должно быть заполнено")
    private String size;
    @Pattern(regexp = "\"^[0-9]\"")
    private Integer costPizza;

    public static PizzaDto from(Pizza pizza) {
        return PizzaDto.builder()
                .pizzaId(pizza.getPizzaId())
                .pizzaName(pizza.getPizzaName())
                .ingredients(pizza.getIngredients())
                .size(pizza.getSize())
                .costPizza(pizza.getCostPizza())
                .build();
    }

    public static List<PizzaDto> from(List<Pizza> pizza) {

        return pizza.stream().map(PizzaDto::from).toList();
    }
}

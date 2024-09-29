package ru.innopolis.pizzeria.dto;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность пицца")
public class PizzaDto {

    @Schema(description = "Идентификатор пиццы")
    private Long pizzaId;

    @Schema(description = "Наименование пиццы")
    @NotBlank(message = "Поле должно быть заполнено")
    private String pizzaName;

    @Schema(description = "Состав пиццы")
    @NotBlank(message = "Поле должно быть заполнено")
    private String ingredients;

    @Schema(description = "Размер пиццы в см.")
    @NotBlank(message = "Поле должно быть заполнено")
    private String size;

    @Schema(description = "Стоимость пиццы")
    @Pattern(regexp = "\"[0-9]\"")
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

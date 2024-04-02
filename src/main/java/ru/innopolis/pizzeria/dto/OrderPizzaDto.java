package ru.innopolis.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.pizzeria.model.OrderPizza;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность заказа")
public class OrderPizzaDto {

    @Schema(description = "Идентификатор заказа")
    private long orderPizzaId;

    @Schema(description = "Количество")
    @NotNull
    @Size(min = 1, message = "Количество должно быть не менее 1 ")
    private Integer amount;

    @Schema(description = "Сумма заказа")
    private Integer orderPrice;

    @Schema(description = "Идентификатор покупателя")
    @NotNull
    private Long customerId;

    @Schema(description = "Идентификатор пиццы")
    @NotNull
    private Long pizzaId;



    public static OrderPizzaDto from(OrderPizza orderPizza) {
        return OrderPizzaDto.builder()
                .orderPizzaId(orderPizza.getOrderPizzaId())
                .amount(orderPizza.getAmount())
                .orderPrice(orderPizza.getOrderPrice())
                .customerId(orderPizza.getCustomer().getCustomerId())
                .pizzaId(orderPizza.getPizza().getPizzaId())
                .build();
    }

    public static List<OrderPizzaDto> from(List<OrderPizza> orderPizza) {
        return orderPizza.stream().map(OrderPizzaDto::from).toList();
    }
}

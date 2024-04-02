package ru.innopolis.pizzeria.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.pizzeria.model.Customer;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность покупателя")
public class CustomerDto {

    @Schema(description = "Идентификатор покупателя")
    private Long customerId;

    @Schema(description = "Имя покупателя")
    @NotNull
    @Size(min = 3, message = "Имя должно быть не менее 3 букв")
    private String firstName;

    @Schema(description = "Фамилия покупателя")
    @NotNull
    @Size(min = 3, message = "Фамилия должна быть не менее 3 букв")
    private String lastName;

    @Schema(description = "Номер телефона покупателя")
    @NotBlank(message = "Номер телефона должен быть указан")
    @Pattern(regexp = "^\\d+$")
    private String phoneNumber;

    @Schema(description = "e-mail адрес покупателя")
    @Email
    @NotBlank(message = "Поле должно быть заполнено")
    private String addressMail;

    @Schema(description = "Город")
    @NotBlank(message = "Поле должно быть заполнено")
    private String city;

    @Schema(description = "Улица")
    @NotBlank(message = "Поле должно быть заполнено")
    private String street;

    @Schema(description = "Номер дома/номер квартиры")
    @NotBlank(message = "Номер дома и квартиры должны быть указаны в формате ххх/ххх")
    @Pattern(regexp = "^(\\d+)/(\\d+)$")
    private String numberHouseAndApartment;

    @Hidden
    private Boolean softDeletedFlag;

    public static CustomerDto from(Customer customer) {
        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .addressMail(customer.getAddressMail())
                .city(customer.getCity())
                .street(customer.getStreet())
                .numberHouseAndApartment(customer.getNumberHouseAndApartment())
                .softDeletedFlag(customer.getSoftDeletedFlag())
                .build();
    }

    public static List<CustomerDto> from(List<Customer> customer) {
        return customer.stream().map(CustomerDto::from).toList();
    }

}

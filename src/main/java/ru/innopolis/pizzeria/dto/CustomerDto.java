package ru.innopolis.pizzeria.dto;
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
public class CustomerDto {

    private Long customerId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String addressMail;

    private String city;

    private String street;

    private String numberHouseAndApartment;
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

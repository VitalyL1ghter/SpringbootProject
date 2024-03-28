package ru.innopolis.pizzeria.model;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import java.util.List;

@Entity
@Data
@Table (name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "first_name")
    @Description(value = "Имя")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address_mail")
    private String addressMail;

    @Column (name = "soft_deleted")
    private Boolean softDeletedFlag;

    @Column (name = "city")
    private String city;

    @Column (name = "street")
    private String street;

    @Column (name = "house_number_apartment_number")
    private String numberHouseAndApartment;


@OneToMany(mappedBy = "customer")
private List<OrderPizza> orderPizza;



}

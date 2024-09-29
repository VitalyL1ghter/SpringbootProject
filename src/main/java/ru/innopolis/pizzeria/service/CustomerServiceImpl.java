package ru.innopolis.pizzeria.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innopolis.pizzeria.dto.CustomerDto;
import ru.innopolis.pizzeria.model.Customer;
import ru.innopolis.pizzeria.repository.CustomerRepository;
import java.util.List;
import static ru.innopolis.pizzeria.dto.CustomerDto.from;
import ru.innopolis.pizzeria.exception.NotFoundException;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerDto> getAll() {
        return from(customerRepository.findAllButNotSoftDeleted());

    }
    @Override
    public CustomerDto getCustomerById(Long id) {
        return from(customerRepository.findById(id).orElseThrow(NotFoundException::new));
    }
    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customer) {
        return from(customerRepository.save(Customer.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .addressMail(customer.getAddressMail())
                .city(customer.getCity())
                .street(customer.getStreet())
                .numberHouseAndApartment(customer.getNumberHouseAndApartment())
                .softDeletedFlag(customer.getSoftDeletedFlag())
                .build()));
    }

    @Override
    public CustomerDto addCustomer(CustomerDto customer) {
        return from(customerRepository.save(Customer.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .addressMail(customer.getAddressMail())
                .city(customer.getCity())
                .street(customer.getStreet())
                .numberHouseAndApartment(customer.getNumberHouseAndApartment())
                .softDeletedFlag(customer.getSoftDeletedFlag())
                .build()));
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void softDeleteCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(NotFoundException::new);
        customer.setSoftDeletedFlag(true);
        customerRepository.save(customer);
    }

}





package ru.innopolis.pizzeria.service;
import org.springframework.stereotype.Service;
import ru.innopolis.pizzeria.dto.CustomerDto;
import java.util.List;

@Service

public interface CustomerService {


    List<CustomerDto> getAll();

    CustomerDto getCustomerById(Long id);

    CustomerDto updateCustomer(Long id, CustomerDto customer);

    CustomerDto addCustomer(CustomerDto customer);

   void deleteCustomerById(Long id);

   void softDeleteCustomerById(Long id);


}

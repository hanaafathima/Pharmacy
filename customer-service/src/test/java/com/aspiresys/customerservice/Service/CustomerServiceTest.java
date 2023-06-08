package com.aspiresys.customerservice.Service;

import com.aspiresys.customerservice.Entity.Customer;
import com.aspiresys.customerservice.Entity.Login;
import com.aspiresys.customerservice.Repository.CustomerRepository;
import com.aspiresys.customerservice.Repository.LoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private LoginRepository loginRepository;

    Customer customer;
    Login login;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerEmail("hana@gmail.com");
        customer.setCustomerId(1l);
        customer.setCustomerContact(9890346784l);
        customer.setCustomerName("Hana Fathima");
        customer.setCustomerAddress("Olive Courtyard");

        login = new Login();
        login.setId(1l);
        login.setEmail("hana@gmail.com");
        login.setRole("USER");
        login.setPassword("USERHana9856098222");
    }
    @Test
    void saveCustomer() {
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
        ResponseEntity<Customer> response = customerService.saveCustomer(customer);
        assertThat(response).isNotNull();

    }

    @Test
    void findCustomer() {
        Mockito.when(customerRepository.findById(1l)).thenReturn(Optional.of(customer));
        assertThat(customerService.findCustomer(1l).getBody());
    }

    @Test
    void listAllCustomers() {
        List<Customer> userList=new ArrayList<>();
        userList.add(customer);
        Mockito.when(customerRepository.findAll()).thenReturn(userList);

        ResponseEntity<List<Customer>> userDTOList = customerService.listAllCustomers();
        assertThat(userDTOList).isNotNull();
    }

    @Test
    void searchCustomer() {
        Mockito.when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));
        assertThat(customerService.searchCustomer(customer).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateCustomer() {
        Mockito.when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        ResponseEntity response=customerService.updateCustomer(1L,customer);
        assertThat(response).isNotNull();
    }


    @Test
    void deleteCustomer() {
        Mockito.when(customerRepository.findById(1l)).thenReturn(Optional.of(customer));
        Mockito.when(loginRepository.findById(1l)).thenReturn(Optional.of(login));
        assertThat(customerService.deleteCustomer(1l).getBody());
        assertThat(customerService.deleteCustomer(1l).getBody());
    }

    @Test
    void getCredentials() {
        Mockito.when(loginRepository.findByEmail(login.getEmail())).thenReturn(Optional.of(login));
        assertThat(customerService.getCredentials(login.getEmail()).getEmail()).isEqualTo("hana@gmail.com");
    }
}
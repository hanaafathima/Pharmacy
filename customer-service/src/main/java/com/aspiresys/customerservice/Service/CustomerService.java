package com.aspiresys.customerservice.Service;

import com.aspiresys.customerservice.Entity.Customer;
import com.aspiresys.customerservice.Entity.Login;
import com.aspiresys.customerservice.Repository.CustomerRepository;
import com.aspiresys.customerservice.Repository.LoginRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoginRepository loginRepository;



    public ResponseEntity<Customer> saveCustomer(Customer customer) {
        try {
            List<Customer> ExistUser= customerRepository.findAll().stream().filter(item->item.getCustomerEmail().equals(customer.getCustomerEmail())).collect(Collectors.toList());
            if(!ExistUser.isEmpty()){
                return new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
            }
            String password = "USER" + customer.getCustomerName() + customer.getCustomerContact();
            String role = "ROLE_USER";
            loginRepository.saveCustomer(customer.getCustomerEmail(),
                    password,
                    role);
            customerRepository.save(customer);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Customer> findCustomer(Long customerId) {
        log.info("Got customer details");

        Customer customer = customerRepository.findById(customerId).get();
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }


    public ResponseEntity<List<Customer>> listAllCustomers() {
        try {
            List<Customer> customerList = customerRepository.findAll();
            if (!customerList.isEmpty()) {
                log.info("List of customers fetched successfully");
                return new ResponseEntity(customerList, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Customer>> searchCustomer(Customer customer) {
        try {
            List customerDetails = customerRepository.findAll()
                    .stream()
                    .filter(showModel -> showModel.getCustomerName().equals(Objects.requireNonNullElse(customer.getCustomerName(), showModel.getCustomerName()))
                            && showModel.getCustomerContact().equals(Objects.requireNonNullElse(customer.getCustomerContact(), showModel.getCustomerContact()))
                            && showModel.getCustomerAddress().equals(Objects.requireNonNullElse(customer.getCustomerAddress(), showModel.getCustomerAddress()))
                            && showModel.getCustomerEmail().equals(Objects.requireNonNullElse(customer.getCustomerEmail(), showModel.getCustomerEmail()))
                    )
                    .limit(5).toList();

            return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Customer> updateCustomer(Long customerId, Customer customer) {
        try {
            if(!customerId.equals(null)) {
                Customer updateCustomer = customerRepository.findById(customerId).get();
                if (customer.getCustomerName() != null) {
                    updateCustomer.setCustomerName(customer.getCustomerName());
                }
                if (customer.getCustomerAddress() != null) {
                    updateCustomer.setCustomerAddress(customer.getCustomerAddress());
                }
                if (customer.getCustomerEmail() != null) {
                    updateCustomer.setCustomerEmail(customer.getCustomerEmail());
                }
                if (customer.getCustomerContact() != null) {
                    updateCustomer.setCustomerContact(customer.getCustomerContact());
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Customer> deleteCustomer(Long customerId) {
        try {
            Optional<Customer> deleteuser = customerRepository.findById(customerId);
            if (deleteuser.isPresent()) {
                Customer existingUser = deleteuser.get();
                String email = existingUser.getCustomerEmail();

                Optional<Login> loginDetails = loginRepository.findByEmail(email);
                Login existingLogin = loginDetails.get();
                Long loginId = existingLogin.getId();
                customerRepository.delete(existingUser);
                loginRepository.deleteById(loginId);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
        }

    public Login getCredentials(String email) {
        Optional<Login> user=loginRepository.findByEmail(email);
        return user.orElse(null);
    }
}


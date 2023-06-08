package com.aspiresys.customerservice.Repository;

import com.aspiresys.customerservice.Entity.Login;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    @Transactional
    @Modifying
    @Query(value = "Insert into login values(default,?1,?2,?3)",nativeQuery = true)
    void saveCustomer(String customerEmail, String password, String role);

    Optional<Login> findByEmail(String email);
}

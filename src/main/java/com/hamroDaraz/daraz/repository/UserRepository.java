package com.hamroDaraz.daraz.repository;


import com.hamroDaraz.daraz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>
{
   Optional<User> findByEmail(String username);  //select * from User where email=....
   boolean existsByEmail(String email);
   boolean existsByMobileNumber(String mobileNumber);

}

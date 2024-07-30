package com.example.authServer.Repository;

import com.example.authServer.DTO.UserDTO;
import com.example.authServer.Entity.Users;
import com.example.authServer.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Boolean existsByEmail(String email);
    Users findByEmail(String email);
}

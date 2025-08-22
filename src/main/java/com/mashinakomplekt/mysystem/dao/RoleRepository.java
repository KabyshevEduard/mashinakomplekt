package com.mashinakomplekt.mysystem.dao;


import com.mashinakomplekt.mysystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findBykind(String kind);
    Optional<Role> findById(Long id);
}

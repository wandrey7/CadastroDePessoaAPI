package com.wdev.sistema_de_cadastros.repository;

import com.wdev.sistema_de_cadastros.model.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, UUID> {
    List findByAge(Optional<Integer> age);
    List findByEmail(Optional<String> email);
    List<PersonModel> findByNameContainingIgnoreCase(Optional<String> name);
}


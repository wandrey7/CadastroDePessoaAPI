package com.wdev.sistema_de_cadastros.repository;

import com.wdev.sistema_de_cadastros.model.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionModel, UUID> {
}

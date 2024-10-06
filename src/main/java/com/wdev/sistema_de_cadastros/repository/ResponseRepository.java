package com.wdev.sistema_de_cadastros.repository;

import com.wdev.sistema_de_cadastros.model.ResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResponseRepository extends JpaRepository<ResponseModel, UUID> {
}

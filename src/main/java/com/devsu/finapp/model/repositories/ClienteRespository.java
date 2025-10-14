package com.devsu.finapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.finapp.model.entities.Cliente;

@Repository
public interface ClienteRespository extends JpaRepository<Cliente, Long> {

}

package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}

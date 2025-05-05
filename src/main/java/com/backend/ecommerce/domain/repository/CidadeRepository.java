package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.Cidade;
import com.backend.ecommerce.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}

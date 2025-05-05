package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.Cidade;
import com.backend.ecommerce.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    // Consulta que busca todos os endereços associados a um usuário pelo seu ID
    @Query("SELECT e FROM Endereco e WHERE e.usuario.id = :usuarioId")
    List<Endereco> buscarEnderecosPorUsuarioId(@Param("usuarioId") Long usuarioId);

}

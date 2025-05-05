package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Consulta personalizada para buscar um usuário pelo seu ID
    // Retorna o usuário completo (com todas as suas informações) com base no ID fornecido.
    @Query("SELECT u FROM Usuario u WHERE u.id = :usuarioId")
    Usuario buscarUsuarioCompleto(@Param("usuarioId") Long usuarioId);
}

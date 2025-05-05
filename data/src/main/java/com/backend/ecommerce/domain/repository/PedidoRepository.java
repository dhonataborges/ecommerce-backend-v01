package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.Pedido;
import com.backend.ecommerce.domain.model.enuns.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

   // Encontra os pedidos do usuário baseado no ID do cliente
   List<Pedido> findByUsuarioId(Long clienteId);

   // Consulta que retorna um pedido baseado no ID do usuário (usando Optional para tratar casos onde o pedido não existe)
   @Query("SELECT p FROM Pedido p WHERE p.usuario.id = :usuarioId")
   Optional<Pedido> buscarPedidosPorUsuario(@Param("usuarioId") Long usuarioId);

   // Soma a quantidade total de itens no pedido de um usuário, onde o status do pedido é 'ABERTO'
   @Query("SELECT SUM(pi.quantidade) FROM PedidoItem pi WHERE pi.pedido.usuario.id = :usuarioId AND pi.pedido.status = 'ABERTO'")
   Integer somarQuantidadeTotalDosItensPorUsuario(@Param("usuarioId") Long usuarioId);

   // Soma o valor total de todos os itens no pedido de um usuário, onde o status do pedido é 'ABERTO'
   @Query("SELECT SUM(pi.valorTotal) FROM PedidoItem pi WHERE pi.pedido.usuario.id = :usuarioId AND pi.pedido.status = 'ABERTO'")
   BigDecimal somarValorTotalDosItensPorUsuario(@Param("usuarioId") Long usuarioId);

   // Consulta que retorna um pedido completo, com dados do usuário e do endereço, baseado no ID do pedido
   @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.usuario u LEFT JOIN FETCH p.endereco e WHERE p.id = :pedidoId")
   Pedido buscarPedidoCompleto(@Param("pedidoId") Long pedidoId);

   // Consulta que retorna um pedido baseado no ID do usuário, no ID do catálogo e no status do pedido
   @Query("""
       SELECT p
       FROM Pedido p
       JOIN p.pedidoItens pi
       WHERE p.usuario.id = :usuarioId
       AND p.status = :status
       AND pi.catalogo.id = :catalogoId
       """)
   Optional<Pedido> findPedidoByUsuarioIdAndCatalogoId(@Param("usuarioId") Long usuarioId,@Param("catalogoId") Long catalogoId,@Param("status") StatusPedido status);

   // Consulta que retorna um pedido baseado no ID do usuário e no status do pedido
   @Query("SELECT p FROM Pedido p WHERE p.usuario.id = :usuarioId AND p.status = :status")
   Optional<Pedido> findPedidoByUsuarioIdAndStatus(@Param("usuarioId") Long usuarioId, @Param("status") StatusPedido status);

}

package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.PedidoItem;
import com.backend.ecommerce.domain.model.enuns.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

    // Consulta que retorna os itens de pedido, incluindo os detalhes do pedido e do usuário, dado o ID do pedido
    @Query("SELECT pi FROM PedidoItem pi JOIN FETCH pi.pedido p JOIN FETCH p.usuario WHERE p.id = :pedidoId")
    List<PedidoItem> buscarItensComUsuario(@Param("pedidoId") Long pedidoId);

    // Consulta que retorna o ID do cliente associado aos itens do pedido, dado o ID do usuário
    @Query("SELECT pi FROM PedidoItem pi WHERE pi.pedido.usuario.id = :usuarioId")
    Long buscarClienteId(Long usuarioId);

    // Consulta que retorna um item de pedido baseado no ID do usuário, ID do catálogo e o status do pedido
    @Query("SELECT pi FROM PedidoItem pi " +
            "WHERE pi.pedido.usuario.id = :usuarioId " +
            "AND pi.catalogo.id = :catalogoId " +
            "AND pi.pedido.status = :status")
    Optional<PedidoItem> buscarItemPorUsuarioECatalogoEStatus(@Param("usuarioId") Long usuarioId, @Param("catalogoId") Long catalogoId, @Param("status") StatusPedido status);

}

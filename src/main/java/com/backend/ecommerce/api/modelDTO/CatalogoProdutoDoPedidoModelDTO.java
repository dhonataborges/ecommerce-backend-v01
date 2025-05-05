
package com.backend.ecommerce.api.modelDTO;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CatalogoProdutoDoPedidoModelDTO {
    private Long id;
    private EstoqueDoPedidoModelDTO estoque;
    private BigDecimal valorItemComDesconto;
  //  private List<PedidoClienteModelDTO> pedidos;  // Agora contém os pedidos completos
}


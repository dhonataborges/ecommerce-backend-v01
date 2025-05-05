package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.*;
import com.backend.ecommerce.domain.model.*;
import com.backend.ecommerce.domain.model.enuns.Permissoes;
import com.backend.ecommerce.domain.model.enuns.StatusPedido;
import com.backend.ecommerce.domain.repository.EnderecoRepository;
import com.backend.ecommerce.domain.repository.EstoqueRepository;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class PedidoItemModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper; // Instância do ModelMapper para conversão entre objetos.

    @Autowired
    private ProdutoRepository produtoRepository; // Repositório para acessar produtos.

    @Autowired
    private EstoqueRepository estoqueRepository; // Repositório para acessar o estoque.

    @Autowired
    private EnderecoRepository enderecoRepository; // Repositório para acessar endereços.

    @Autowired
    private EnderecoModelAssemblerDTO enderecoModelAssemblerDTO; // Assembler para endereço.

    @Autowired
    private ProdutoDoPedidoModelAssemblerDTO produtoDoPedidoModelAssemblerDTO; // Assembler para produtos no pedido.

    @Autowired
    private EstoqueDoPedidoModelAssemblerDTO estoqueDoPedidoModelAssemblerDTO; // Assembler para estoque no pedido.

    // Método que converte um PedidoItem para seu modelo DTO correspondente.
    public PedidoItemModelDTO toModel(PedidoItem pedidoItem) {

        // Criação do DTO do usuário
        UsuarioModelDTO usuarioDTO = new UsuarioModelDTO();

        // Verifica se o usuário do pedido não é nulo
        if (pedidoItem.getPedido().getUsuario() != null) {
            // Define permissão como CLIENTE se for nulo
            if (pedidoItem.getPedido().getUsuario().getPermissoes() == null) {
                usuarioDTO.setCodPermicoes(Permissoes.CLIENTE.getCodigo());
            } else {
                usuarioDTO.setCodPermicoes(pedidoItem.getPedido().getUsuario().getPermissoes().getCodigo());
            }

            // Preenche os dados do usuário
            usuarioDTO.setId(pedidoItem.getPedido().getUsuario().getId());
            usuarioDTO.setNome(pedidoItem.getPedido().getUsuario().getNome());
            usuarioDTO.setSobrenome(pedidoItem.getPedido().getUsuario().getSobrenome());
            usuarioDTO.setEmail(pedidoItem.getPedido().getUsuario().getEmail());
        } else {
            // Lança exceção caso o usuário esteja null
            throw new IllegalArgumentException("ERRO: Usuário retornado do banco de dados está null.");
        }

        // Criação do nome completo do dono do endereço
        String nomeCompleto = null;
        if (pedidoItem.getPedido().getEndereco() != null &&
                pedidoItem.getPedido().getEndereco().getNomeDono() != null) {
            nomeCompleto = usuarioDTO.getNome() + " " + usuarioDTO.getSobrenome();
        }

        // DTOs para cidade e estado
        EstadoModelDTO estadoDTO = new EstadoModelDTO();
        CidadeModelDTO cidadeDTO = new CidadeModelDTO();

        if (pedidoItem.getPedido().getEndereco() != null) {
            estadoDTO.setId(pedidoItem.getPedido().getEndereco().getCidade().getEstado().getId());
            estadoDTO.setNome(pedidoItem.getPedido().getEndereco().getCidade().getEstado().getNome());
            cidadeDTO.setId(pedidoItem.getPedido().getEndereco().getCidade().getId());
            cidadeDTO.setNome(pedidoItem.getPedido().getEndereco().getCidade().getNome());
            cidadeDTO.setEstado(estadoDTO);
        }

        // DTO para o endereço
        EnderecoModelDTO enderecoDTO = new EnderecoModelDTO();
        if (pedidoItem.getPedido().getEndereco() != null) {
            enderecoDTO.setId(pedidoItem.getPedido().getEndereco().getId());
            enderecoDTO.setNomeDono(nomeCompleto);
            enderecoDTO.setCep(pedidoItem.getPedido().getEndereco().getCep());
            enderecoDTO.setBairro(pedidoItem.getPedido().getEndereco().getBairro());
            enderecoDTO.setRua(pedidoItem.getPedido().getEndereco().getRua());
            enderecoDTO.setNumero(pedidoItem.getPedido().getEndereco().getNumero());
            enderecoDTO.setComplemento(pedidoItem.getPedido().getEndereco().getComplemento());
            enderecoDTO.setCidade(cidadeDTO);
        }

        // DTO do pedido
        PedidoModelDTO pedidoDTO = new PedidoModelDTO();

        // Define o status do pedido: usa padrão se for null
        if (pedidoItem.getPedido().getStatus() == null) {
            pedidoDTO.setCodStatus(StatusPedido.ABERTO.getCodStatus());
            pedidoDTO.setDescriStatus(StatusPedido.ABERTO.getDescricao());
        } else {
            pedidoDTO.setCodStatus(pedidoItem.getPedido().getStatus().getCodStatus());
            pedidoDTO.setDescriStatus(pedidoItem.getPedido().getStatus().getDescricao());
        }

        // Preenche os dados do pedido
        pedidoDTO.setId(pedidoItem.getPedido().getId());
        pedidoDTO.setUsuario(usuarioDTO);
        pedidoDTO.setEndereco(enderecoDTO);
        pedidoDTO.setDataPedido(LocalDate.now());
        pedidoDTO.setValorTotal(pedidoItem.getPedido().getValorTotal());

        // DTOs relacionados ao produto
        FotoProdutoModelDTO fotoDTO = new FotoProdutoModelDTO();
        ProdutoModelDTO produtoDTO = new ProdutoModelDTO();
        EstoqueDoPedidoModelDTO estoqueDTO = new EstoqueDoPedidoModelDTO();

        // Verifica se catálogo existe
        if (pedidoItem.getCatalogo() != null) {
            Estoque estoque = pedidoItem.getCatalogo().getEstoque();

            if (estoque != null) {
                Produto produto = estoque.getProduto();

                if (produto != null) {
                    FotoProduto foto = produto.getFotoProduto();

                    if (foto != null) {
                        // Preenche os dados da foto do produto
                        fotoDTO.setId(foto.getId());
                        fotoDTO.setNomeArquivo(foto.getNomeArquivo());
                        fotoDTO.setDescricao(foto.getDescricao());
                        fotoDTO.setContentType(foto.getContentType());
                        fotoDTO.setTamanho(foto.getTamanho());
                    }

                    // Preenche os dados do produto
                    produtoDTO.setId(produto.getId());
                    produtoDTO.setCodProd(produto.getCodigo());
                    produtoDTO.setNomeProd(produto.getNome());
                    produtoDTO.setFoto(fotoDTO);
                }

                // Preenche os dados do estoque
                estoqueDTO.setId(estoque.getId());
                estoqueDTO.setValorUnitario(estoque.getValorUnitario());
                estoqueDTO.setQuantidade(estoque.getQuantidade());
                estoqueDTO.setProduto(produtoDTO);
            }
        }

        // DTO do catálogo do pedido
        CatalogoProdutoDoPedidoModelDTO catalogoProdutoDTO = new CatalogoProdutoDoPedidoModelDTO();
        if (pedidoItem.getCatalogo() != null) {
            catalogoProdutoDTO.setId(pedidoItem.getCatalogo().getId());
            catalogoProdutoDTO.setValorItemComDesconto(pedidoItem.getCatalogo().getValorComDesconto());
            catalogoProdutoDTO.setEstoque(estoqueDTO);
        }

        // Criação e preenchimento final do DTO do item do pedido
        PedidoItemModelDTO pedidoItemDTO = new PedidoItemModelDTO();
        pedidoItemDTO.setId(pedidoItem.getId());
        pedidoItemDTO.setPedido(pedidoDTO);
        pedidoItemDTO.setCatalogo(catalogoProdutoDTO);
        pedidoItemDTO.setQuantidade(pedidoItem.getQuantidade());
        pedidoItemDTO.setValorUnitario(pedidoItem.getValorUnitario());
        pedidoItemDTO.setValorTotal(pedidoItem.getValorTotal());

        return pedidoItemDTO;
    }

    // Converte uma lista de PedidoItem para uma lista de seus DTOs correspondentes.
    public List<PedidoItemModelDTO> toCollectionModel(List<PedidoItem> pedidoItens) {
        return pedidoItens.stream()
                .map(pedidoItem -> toModel(pedidoItem))
                .collect(Collectors.toList());
    }

}
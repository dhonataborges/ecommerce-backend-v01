package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.exception.ProdutoNaoEncontradoException;
import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.repository.FotoProdutoRepository;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.Optional;
/**
 * Serviço responsável pela gestão de produtos. As funcionalidades incluem a criação de produtos,
 * a atualização de produtos e fotos, a exclusão de produtos, e a busca de produtos pelo ID.
 */
@Service
public class ProdutoService {

    // Mensagens de erro
    private static final String MSG_PRODUTO_EM_USO = "Produto de código %d não pode ser removido, pois está em uso";

    // Repositórios necessários para interação com o banco de dados
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoStorageService fotoStorage;

    @Autowired
    private FotoProdutoRepository fotoProdutoRepository;

    /**
     * Cria uma foto genérica associada a um produto. A foto é salva no banco de dados e no armazenamento de fotos.
     *
     * @param produto O produto ao qual a foto será associada.
     * @return A FotoProduto recém-criada.
     * @throws IOException Se ocorrer um erro ao manipular o arquivo da foto.
     */
    @Transactional
    public FotoProduto criarFotoGenerica(Produto produto) throws IOException {

        Long produtoId = produto.getId(); // Obtém o ID do produto associado à foto

        produto = buscarOuFalhar(produtoId);  // Busca o produto no banco de dados

        // Caminho do arquivo da foto genérica
        Path caminho = Path.of("img/image-generica.jpeg");

        FotoProduto foto = new FotoProduto();

        // Captura as informações sobre o arquivo da foto
        File arquivo = caminho.toFile();

        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(arquivo.getName()); // Gera um nome único para o arquivo da foto

        FileInputStream inputStream = new FileInputStream(arquivo); // Cria o InputStream para ler os dados do arquivo
        String contentType = URLConnection.guessContentTypeFromStream(inputStream); // Obtém o tipo de conteúdo (MIME type)

        // Verificação se o caminho e contentType estão disponíveis
        if (caminho != null) {
            foto.setProduto(produto);
            foto.setDescricao("Sem nome");
            foto.setTamanho(arquivo.length());
            foto.setNomeArquivo(nomeNovoArquivo);

            // Se o contentType for nulo, define um valor padrão
            foto.setContentType(contentType != null ? contentType : "image/jpeg");
        } else {
            throw new FileNotFoundException("Caminho do arquivo não especificado!");
        }

        // Salva a nova foto no banco de dados
        foto = produtoRepository.save(foto);
        produtoRepository.flush();  // Garante que a foto seja persistida no banco de dados

        // Cria o objeto NovaFoto para armazenar a foto no sistema de armazenamento
        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeAquivo(foto.getNomeArquivo())
                .inputStream(inputStream)
                .build();

        // Armazena a foto genérica no armazenamento
        fotoStorage.armazenar(novaFoto);

        return foto;  // Retorna a foto recém-criada e salva
    }

    /**
     * Salva um novo produto no banco de dados. Se o produto não tiver uma foto associada, cria uma foto genérica.
     *
     * @param produto O produto a ser salvo.
     * @return O produto salvo com a foto associada.
     * @throws IOException Se ocorrer um erro ao manipular a foto do produto.
     */
    @Transactional
    public Produto salvar(@Valid Produto produto) throws IOException {

        // Verifica se o produto já existe no banco de dados pelo código
        Produto cod = produtoRepository.findByCodProd(produto.getCodigo());
        if (cod != null) {
            throw new NegocioException("Produto com o código " + produto.getCodigo() + " já está cadastrado!");
        }

        // Salva o produto e garante que o produto tenha um ID atribuído
        produto = produtoRepository.save(produto);
        produtoRepository.flush();

        FotoProduto foto = null;

        // Se o produto não tem uma foto associada, cria uma foto genérica
        if (produto.getFotoProduto() == null) {
            foto = criarFotoGenerica(produto);
        }

        // Associa a foto ao produto e salva o produto novamente
        produto.setFotoProduto(foto);
        return produtoRepository.save(produto);
    }

    /**
     * Atualiza as informações de um produto e sua foto associada. Se a foto já existir, ela será atualizada.
     *
     * @param produto O produto a ser atualizado.
     * @return O produto atualizado.
     * @throws IOException Se ocorrer um erro ao manipular a foto do produto.
     */
    @Transactional
    public Produto atualizar(@Valid Produto produto) throws IOException {

        Long fotoId = produto.getId();  // Obtém o ID do Produto (presumido ser o mesmo da FotoProduto)

        // Busca a FotoProduto associada ao Produto
        Optional<FotoProduto> fotoBanco = produtoRepository.findFotoById(fotoId);
        FotoProduto foto = fotoBanco.get();  // Lança exceção se a foto não for encontrada

        if (fotoBanco.isPresent()) {
            // Atualiza as informações da foto com as mais recentes
            foto.setProduto(produto);
            foto.setNomeArquivo(fotoBanco.get().getNomeArquivo());
            foto.setDescricao(fotoBanco.get().getDescricao());
            foto.setContentType(fotoBanco.get().getContentType());
            foto.setTamanho(fotoBanco.get().getTamanho());

            // Salva a FotoProduto atualizada no banco de dados
            fotoProdutoRepository.save(foto);

            // Associa a foto ao produto
            produto.setFotoProduto(foto);
        }

        // Salva o produto no banco de dados com a foto atualizada
        return produtoRepository.save(produto);
    }

    /**
     * Exclui um produto e sua foto associada do banco de dados e do armazenamento de fotos.
     *
     * @param produtoId O ID do produto a ser excluído.
     */
    @Transactional
    public void excluir(Long produtoId) {
        try {
            Long fotoId = produtoId;

            // Busca a foto associada ao produto
            Optional<FotoProduto> fotoBanco = produtoRepository.findFotoById(fotoId);
            FotoProduto foto = fotoBanco.get();

            if (fotoBanco.isPresent()) {
                // Exclui a foto do banco de dados
                fotoProdutoRepository.delete(foto);
                fotoProdutoRepository.flush();

                // Remove a foto do armazenamento (presumivelmente um sistema de arquivos ou S3)
                fotoStorage.remover(foto.getNomeArquivo());
            }

            // Exclui o produto do banco de dados
            produtoRepository.deleteById(produtoId);
            produtoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new ProdutoNaoEncontradoException(produtoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_PRODUTO_EM_USO, produtoId));

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar excluir o produto", e);
        }
    }

    /**
     * Busca um produto pelo ID e lança uma exceção se o produto não for encontrado.
     *
     * @param produtoId O ID do produto a ser buscado.
     * @return O produto encontrado.
     */
    public Produto buscarOuFalhar(Long produtoId) {
        return produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }
}

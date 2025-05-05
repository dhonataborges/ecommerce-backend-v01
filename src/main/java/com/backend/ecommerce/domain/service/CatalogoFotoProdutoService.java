package com.backend.ecommerce.domain.service;


import com.backend.ecommerce.domain.exception.ProdutoNaoEncontradoException;
import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;
/**
 * Serviço responsável por gerenciar a associação e o armazenamento de fotos de produtos no catálogo.
 * Realiza operações de substituição, exclusão lógica no banco de dados e manipulação do armazenamento físico.
 */
@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    /**
     * Salva ou substitui uma foto associada a um produto.
     * Se já existir uma foto vinculada ao produto, a foto anterior é removida do banco e do armazenamento.
     *
     * @param foto         Instância contendo os metadados da nova foto.
     * @param dadosArquivo Conteúdo da foto como fluxo de dados.
     * @return A instância da foto persistida.
     */
    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long produtoId = foto.getProduto().getId();

        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoDoProdutoById(produtoId);

        // Remove a foto anterior do banco de dados e armazena seu nome para remoção física
        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
            produtoRepository.flush();
        }

        // Atualiza o nome do novo arquivo e persiste a nova foto no banco de dados
        foto.setNomeArquivo(nomeNovoArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        // Define os dados da nova foto para armazenamento físico
        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeAquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        // Substitui o arquivo anterior, se existente, no sistema de arquivos
        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    /**
     * Busca uma foto de produto pelo seu identificador ou lança exceção se não encontrada.
     *
     * @param produtoId Identificador do produto.
     * @return Foto associada ao produto.
     * @throws ProdutoNaoEncontradoException se nenhuma foto estiver associada ao produto.
     */
    public FotoProduto buscarOuFalhar(Long produtoId) {
        return produtoRepository.findFotoById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }

    /**
     * Exclui a foto associada a um produto, removendo tanto o registro do banco de dados quanto o arquivo físico.
     *
     * @param produtoId Identificador do produto cuja foto será excluída.
     */
    @Transactional
    public void excluir(Long produtoId) {
        FotoProduto foto = buscarOuFalhar(produtoId);

        produtoRepository.delete(foto);
        produtoRepository.flush();

        fotoStorage.remover(foto.getNomeArquivo());
    }
}

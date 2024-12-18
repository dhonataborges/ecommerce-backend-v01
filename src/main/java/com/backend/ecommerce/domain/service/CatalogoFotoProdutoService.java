package com.backend.ecommerce.domain.service;


import com.backend.ecommerce.domain.exception.produtoException.ProdutoNaoEncontradoException;
import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {

        Long produtoId = foto.getProduto().getId(); // Obtém o ID do produto associado à foto

        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo()); // Gera um novo nome para o arquivo da foto
        String nomeArquivoExistente = null; // Variável para armazenar o nome do arquivo antigo, se houver

        // Verifica se já existe uma foto associada ao produto no banco de dados
        Optional<FotoProduto> fotoExistente = produtoRepository.findProdutoById(produtoId);

        // Se já existir uma foto para este produto, realiza a exclusão da foto anterior
        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo(); // Obtém o nome do arquivo da foto existente
            // Deleta a foto existente associada ao produto
            produtoRepository.delete(fotoExistente.get());
            produtoRepository.flush(); // Realiza o flush para garantir que a exclusão seja persistida no banco
        }

        // Atribui o nome do novo arquivo à foto
        foto.setNomeArquivo(nomeNovoArquivo);
        // Salva a nova foto no banco de dados
        foto = produtoRepository.save(foto);
        produtoRepository.flush(); // Realiza o flush para garantir que a foto seja persistida no banco

        // Cria um objeto NovaFoto para armazenar os dados do arquivo de foto a ser armazenado
        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeAquivo(foto.getNomeArquivo()) // Nome do arquivo da nova foto
                .inputStream(dadosArquivo) // InputStream contendo os dados do arquivo
                .build();

        // Substitui o arquivo existente (se houver) no armazenamento de fotos, caso contrário, armazena a nova foto
        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        // Retorna o objeto FotoProduto recém-salvo
        return foto;
    }

    @Transactional
    public FotoProduto buscarOuFalhar(Long produtoId) {
        // Tenta buscar a foto associada ao produto, se não encontrar, lança uma exceção
        return produtoRepository.findFotoById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }

    @Transactional
    public void excluir(Long produtoId) {
        // Busca a foto associada ao produto, ou lança uma exceção se não encontrar
        FotoProduto foto = buscarOuFalhar(produtoId);

        // Deleta a foto associada ao produto no banco de dados
        produtoRepository.delete(foto);
        produtoRepository.flush(); // Realiza o flush para garantir que a exclusão seja persistida no banco

        // Remove o arquivo de foto do armazenamento de arquivos
        fotoStorage.remover(foto.getNomeArquivo());
    }

}
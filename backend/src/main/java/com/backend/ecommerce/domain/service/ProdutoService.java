package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.FotoProdutoNaoEncontradaException;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.exception.produtoException.ProdutoNaoEncontradoException;
import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.repository.FotoProdutoRepository;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

@Service
public class ProdutoService {

    private static final String MSG_PRODUTO_EM_USO = "Produto de código %d não pode ser removido, pois está em uso";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoStorageService fotoStorage;

    @Autowired
    private FotoProdutoRepository fotoProdutoRepository;

    @Transactional
    public FotoProduto criarFotoGenerica(Produto produto) throws IOException {

        Long produtoId = produto.getId(); // Obtém o ID do produto associado à foto

        produto = buscarOuFalhar(produtoId);

        // Caminho do arquivo
        Path caminho = Path.of("img/image-generica.jpeg");

        FotoProduto foto = new FotoProduto();

        // Capturando informações sobre o arquivo
        File arquivo = caminho.toFile();

        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(arquivo.getName()); // Gera um novo nome para o arquivo da foto

        FileInputStream inputStream = new FileInputStream(arquivo);
        String contentType = URLConnection.guessContentTypeFromStream(inputStream);

        // Verificação se o caminho e contentType estão disponíveis
        if (caminho != null) {
            foto.setProduto(produto);
            foto.setDescricao("Sem nome");
            foto.setTamanho(arquivo.length());
            foto.setNomeArquivo(nomeNovoArquivo);

            // Se o contentType for nulo, define um valor padrão
            if (contentType != null) {
                foto.setContentType(contentType);
            } else {
                foto.setContentType("image/jpeg");
            }
        } else {
            throw new FileNotFoundException("Caminho do arquivo não especificado!");
        }

        // Salva a nova foto no banco de dados
        foto = produtoRepository.save(foto);
        produtoRepository.flush(); // Realiza o flush para garantir que a foto seja persistida no banco

        // Cria um objeto NovaFoto para armazenar os dados do arquivo de foto a ser armazenado
        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeAquivo(foto.getNomeArquivo()) // Nome do arquivo da nova foto
                .inputStream(inputStream) // InputStream contendo os dados do arquivo
                .build();

        // Armazenamento de fotos, armazena a foto generica
        fotoStorage.armazenar(novaFoto);

        // Retorna o objeto FotoProduto recém-salvo
        return foto;
    }


    @Transactional
    public Produto salvar(@Valid Produto produto) throws IOException {

        // Salva o produto primeiro para garantir que ele tenha um ID atribuído
        Produto cod = produtoRepository.findByCodProd(produto.getCodProd());

        // Verifica se o produto já existe (evitando condição de corrida)
        if (cod != null) {
            throw new NegocioException("Produto com o código " + produto.getCodProd() + " já está cadastrado!");
        }

            produto = produtoRepository.save(produto);
            produtoRepository.flush();  // Garante que o produto seja persistido no banco


        FotoProduto foto = null;


        // Verifica se o produto precisa de uma foto (se a foto não foi salva ainda)
        if (produto.getFotoProduto() == null) {

            // Cria e associa uma foto genérica ao produto
            foto = criarFotoGenerica(produto);
        }

        // Salva o produto novamente após associar a foto
        produto.setFotoProduto(foto);
        return produtoRepository.save(produto);

		/*if (buscaCodProd(produto) != null) {
			throw new NegocioException(String.format("Produto já está cadastrado!"));
			//DataIntegrityViolationException("Produto já está cadastrado!");
		}*/
    }

    @Transactional
    public Produto atualizar(@Valid Produto produto) throws IOException {  // Método que recebe um objeto Produto válido como parâmetro.

        Long fotoId = produto.getId();  // O código pega o ID do Produto. Aqui, a suposição é que o ID do Produto seja o ID da FotoProduto.
        // Se isso não for o caso, o ID da foto deve ser obtido de outra forma.

        // Busca a FotoProduto associada ao Produto pelo ID da foto.
        Optional<FotoProduto> fotoBanco = produtoRepository.findFotoById(fotoId);  // Obtém a FotoProduto a partir do repositório.

        // A partir de 'fotoBanco', obtemos o objeto FotoProduto, usando o método get().
        FotoProduto foto = fotoBanco.get();  // A linha pode lançar uma exceção caso o valor de fotoBanco seja vazio, pois não é verificado.

        // Verifica se o FotoProduto foi encontrado no banco de dados
        if (fotoBanco.isPresent()) {  // Se a foto existe no banco, a lógica para atualizar é executada.

            // Atualiza as informações da FotoProduto com os dados mais recentes
            foto.setProduto(produto);  // Associa a foto ao Produto
            foto.setNomeArquivo(fotoBanco.get().getNomeArquivo());  // Atualiza o nome do arquivo
            foto.setDescricao(fotoBanco.get().getDescricao());  // Atualiza a descrição da foto
            foto.setContentType(fotoBanco.get().getContentType());  // Atualiza o tipo de conteúdo da foto (por exemplo, "image/jpeg")
            foto.setTamanho(fotoBanco.get().getTamanho());  // Atualiza o tamanho da foto

            // Salva a FotoProduto atualizada no banco de dados
            fotoProdutoRepository.save(foto);  // O repositório FotoProduto é usado para salvar a foto no banco

            // Associa a FotoProduto atualizada ao Produto
            produto.setFotoProduto(foto);  // Aqui, a FotoProduto é associada ao Produto para que seja salva junto com o Produto
        }

        produto.setFotoProduto(foto);  // Garante que o Produto tenha a FotoProduto associada, caso a foto já tenha sido associada antes

        // Salva o Produto no banco de dados com a foto associada
        return produtoRepository.save(produto);  // O Produto, agora com a foto atualizada, é salvo no banco de dados
    }


    @Transactional
    public void excluir(Long produtoId) {
        try {
            // A variável 'fotoId' recebe o mesmo valor de 'produtoId', presumindo que o ID do produto seja o mesmo que o ID da FotoProduto.
            Long fotoId = produtoId;

            // Busca a FotoProduto associada ao produto pelo ID. O repositório de produtos tem um método para encontrar a foto pelo ID do produto.
            Optional<FotoProduto> fotoBanco = produtoRepository.findFotoById(fotoId);

            // 'fotoBanco' pode ser vazio. Aqui, obtemos o objeto FotoProduto de dentro de 'fotoBanco' (que é um Optional), usando o método 'get()'.
            // Esse método lança uma exceção se o valor não estiver presente.
            FotoProduto foto = fotoBanco.get();

            // Verifica se a foto foi encontrada no banco de dados.
            if (fotoBanco.isPresent()) {
                // Se a foto foi encontrada, ela é deletada usando o repositório 'fotoProdutoRepository'.
                fotoProdutoRepository.delete(foto);

                // 'flush()' força a sincronização da operação de exclusão com o banco de dados.
                fotoProdutoRepository.flush();

                // Remoção da foto associada ao produto do sistema de arquivos ou armazenamento (presumivelmente um sistema de arquivos ou S3).
                fotoStorage.remover(foto.getNomeArquivo());
            }

            // Após a remoção da foto, o produto também é excluído utilizando o repositório 'produtoRepository'.
            produtoRepository.deleteById(produtoId);

            // 'flush()' novamente para garantir que a exclusão do produto seja confirmada no banco de dados.
            produtoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            // Se não encontrar o produto no banco de dados (ou a foto), lança uma exceção personalizada.
            throw new ProdutoNaoEncontradoException(produtoId);

        } catch (DataIntegrityViolationException e) {
            // Se houver uma violação de integridade no banco de dados (por exemplo, se o produto estiver em uso por outra entidade), lança uma exceção.
            throw new EntidadeEmUsoException(String.format(MSG_PRODUTO_EM_USO, produtoId));

        } catch (Exception e) {
            // Caso haja algum erro inesperado (não tratado pelas exceções acima), lança uma exceção genérica.
            throw new RuntimeException("Erro ao tentar excluir o produto", e);
        }
    }

    public Produto buscarOuFalhar(Long produtoId) {
        return produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }

}



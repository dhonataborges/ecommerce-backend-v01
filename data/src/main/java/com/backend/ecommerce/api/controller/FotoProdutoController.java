package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.FotoProdutoModelAssemblerDTO;
import com.backend.ecommerce.api.modelDTO.FotoProdutoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.FotoProdutoInputDTO;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;
import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import com.backend.ecommerce.domain.service.CatalogoFotoProdutoService;
import com.backend.ecommerce.domain.service.FotoStorageService;
import com.backend.ecommerce.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Define que esta classe é um controlador REST (para tratar requisições HTTP).
@RequestMapping(value = "admin/produtos/{produtoId}/foto")  // Mapeamento da URL para o controlador de foto do produto.
public class FotoProdutoController {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;  // Serviço de negócios para operações relacionadas às fotos dos produtos.

	@Autowired
	private FotoStorageService fotoStorage;  // Serviço de armazenamento de fotos (recuperação e persistência).

	@Autowired
	private ProdutoService produtoService;  // Serviço para buscar informações sobre o produto.

	@Autowired
	private FotoProdutoModelAssemblerDTO fotoProdutoModelAssemblerDTO;  // Responsável por converter o modelo de domínio para o DTO (Data Transfer Object).

	@Autowired
	private ProdutoRepository produtoRepository;  // Repositório para interação com o banco de dados dos produtos.

	/**
	 * Retorna a foto de um produto específico.
	 * @param produtoId ID do produto.
	 * @return Foto do produto convertida para DTO.
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModelDTO buscar(@PathVariable Long produtoId) {
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(produtoId);  // Busca a foto do produto pelo ID.
		return fotoProdutoModelAssemblerDTO.toModel(fotoProduto);  // Converte a foto do produto para o formato DTO e retorna.
	}

	/**
	 * Retorna a foto de um produto em formato de arquivo, de acordo com o tipo de mídia aceito pelo cliente.
	 * @param produtoId ID do produto.
	 * @param acceptHeader Tipo de mídia que o cliente aceita (usado para verificar a compatibilidade do tipo de mídia).
	 * @return Foto do produto em formato de arquivo.
	 */
	@GetMapping
	public ResponseEntity<InputStreamResource> servir(@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader)
			throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(produtoId);  // Busca a foto do produto pelo ID.

			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());  // Tipo de mídia da foto do produto.
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);  // Tipos de mídia aceitos pelo cliente.

			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);  // Verifica se o tipo de mídia da foto é compatível com o aceito pelo cliente.

			// Recupera a foto do armazenamento.
			InputStream inputStream = fotoStorage.recuperar(fotoProduto.getNomeArquivo());

			return ResponseEntity.ok()  // Retorna a foto como resposta.
					.contentType(mediaTypeFoto)  // Define o tipo de mídia.
					.body(new InputStreamResource(inputStream));  // Retorna a foto como recurso de entrada.
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();  // Caso não encontre a foto, retorna 404 (não encontrado).
		}
	}

	/**
	 * Verifica se o tipo de mídia da foto é compatível com os tipos aceitos pelo cliente.
	 * @param mediaTypeFoto Tipo de mídia da foto.
	 * @param mediaTypesAceitas Tipos de mídia aceitos pelo cliente.
	 * @throws HttpMediaTypeNotAcceptableException Se o tipo de mídia não for compatível.
	 */
	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto,
												   List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {

		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));  // Verifica se algum tipo aceito é compatível com o tipo da foto.

		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);  // Se não for compatível, lança exceção.
		}
	}

	/**
	 * Atualiza a foto de um produto.
	 * @param produtoId ID do produto.
	 * @param fotoProdutoInput DTO contendo a foto a ser associada ao produto.
	 * @return Foto do produto atualizada e convertida para DTO.
	 */
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModelDTO atualizarFoto(@PathVariable Long produtoId, @Valid FotoProdutoInputDTO fotoProdutoInput) throws IOException {
		Produto produto = produtoService.buscarOuFalhar(produtoId);  // Busca o produto pelo ID.

		MultipartFile arquivo = fotoProdutoInput.getArquivo();  // Obtém o arquivo da foto.

		FotoProduto foto = new FotoProduto();  // Cria um novo objeto FotoProduto.
		foto.setProduto(produto);  // Associa o produto.
		foto.setDescricao(fotoProdutoInput.getDescricao());  // Define a descrição da foto.
		foto.setContentType(arquivo.getContentType());  // Define o tipo de conteúdo da foto.
		foto.setTamanho(arquivo.getSize());  // Define o tamanho da foto.
		foto.setNomeArquivo(arquivo.getOriginalFilename());  // Define o nome do arquivo da foto.

		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());  // Salva a foto.

		return fotoProdutoModelAssemblerDTO.toModel(fotoSalva);  // Converte e retorna a foto salva como DTO.
	}

	/**
	 * Exclui a foto de um produto.
	 * @param produtoId ID do produto.
	 */
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)  // Retorna o status HTTP 204 (Sem Conteúdo) após a exclusão.
	public void excluir(@PathVariable Long produtoId) {
		catalogoFotoProdutoService.excluir(produtoId);  // Exclui a foto do produto.
	}

}
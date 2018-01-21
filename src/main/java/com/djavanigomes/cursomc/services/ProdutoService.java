package com.djavanigomes.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.djavanigomes.cursomc.domain.Categoria;
import com.djavanigomes.cursomc.domain.Produto;
import com.djavanigomes.cursomc.repositories.CategoriaRepository;
import com.djavanigomes.cursomc.repositories.ProdutoRepository;
import com.djavanigomes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Produto obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id +
					", Tipo: " + Produto.class.getName());
		}
		return obj;
	}
	
	public Page<Produto> search(
			String nome, List<Integer> ids, 
			Integer page, Integer linesPerPage, 
			String orderBy, String direction){
		PageRequest pageRequest	= new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAll(ids);
		return repo.findDisctinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}

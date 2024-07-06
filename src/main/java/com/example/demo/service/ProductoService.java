package com.example.demo.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.ProductoEntity;


public interface ProductoService {
	List<ProductoEntity>buscarTodosProductos();
	ProductoEntity buscarProductoPorId(Integer id);
	ProductoEntity registrarProducto(ProductoEntity productoEntity, Model model, MultipartFile foto);
	ProductoEntity editarProducto(ProductoEntity productoEntity, MultipartFile foto, Model model);
    void eliminarProducto(Integer productoId);
    
}
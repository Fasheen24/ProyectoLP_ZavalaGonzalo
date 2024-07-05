package com.example.demo.service;

import java.util.List;


import com.example.demo.entity.ProductoEntity;


public interface ProductoService {
	List<ProductoEntity>buscarTodosProductos();
	ProductoEntity buscarProductoPorId(Integer id);
	ProductoEntity registrarProducto(ProductoEntity productoEntity);
	ProductoEntity editarProducto(ProductoEntity productoEntity);
    void eliminarProducto(Integer productoId);
    
}
package com.example.demo.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.DetallePedidoEntity;
import com.example.demo.entity.ProductoEntity;
import com.example.demo.repository.DetallePedidoRepository;
import com.example.demo.repository.MedicamentoRepository;
import com.example.demo.service.ProductoService;
import com.example.demo.utils.Utilitarios;

import jakarta.transaction.Transactional;


@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private MedicamentoRepository medicamentoRepository;
	@Autowired
    private DetallePedidoRepository detallePedidoRepository;
	
	@Override
	public List<ProductoEntity> buscarTodosProductos() {
		
		return medicamentoRepository.findAll();
		
	}

	@Override
	public ProductoEntity buscarProductoPorId(Integer id) {
		return medicamentoRepository.findById((int) id.longValue()).get();
	}

	@Override
	public ProductoEntity registrarProducto(ProductoEntity productoEntity,Model model, MultipartFile foto) {
		// TODO Auto-generated method stub
		String nombreFoto = Utilitarios.guardarImagen(foto);
		productoEntity.setUrlImagen(nombreFoto);
		return medicamentoRepository.save(productoEntity);
	}

	@Override
	public ProductoEntity editarProducto(ProductoEntity productoEntity, MultipartFile foto, Model model) {
		ProductoEntity productoBuscadoEntity = buscarProductoPorId(productoEntity.getProductoId());
		if(productoBuscadoEntity != null) {
			productoBuscadoEntity.setNombre(productoEntity.getNombre());
			productoBuscadoEntity.setPrecio(productoEntity.getPrecio());
			productoBuscadoEntity.setStock(productoEntity.getStock());
			
			 if (foto != null && !foto.isEmpty()) {
		            String nombreImagen = Utilitarios.guardarImagen(foto);
		            productoBuscadoEntity.setUrlImagen(nombreImagen);
		        }

			
			return medicamentoRepository.save(productoBuscadoEntity);
		}
		return null;
	}

	@Override
	@Transactional
	public void eliminarProducto(Integer productoId) {
		ProductoEntity producto = medicamentoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        List<DetallePedidoEntity> detalles = detallePedidoRepository.findByProductoEntity(producto);

        detallePedidoRepository.deleteAll(detalles);
        medicamentoRepository.delete(producto);
		
	}
	
	


	
}
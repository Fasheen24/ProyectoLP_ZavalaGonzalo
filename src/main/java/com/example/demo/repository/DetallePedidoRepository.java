package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DetallePedidoEntity;
import com.example.demo.entity.ProductoEntity;

public interface DetallePedidoRepository extends JpaRepository<DetallePedidoEntity, Long>{
	List<DetallePedidoEntity> findByProductoEntity(ProductoEntity productoEntity);
}

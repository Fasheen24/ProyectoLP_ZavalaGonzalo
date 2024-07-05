package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DetallePedidoEntity;
import com.example.demo.entity.PedidoEntity;
import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.model.Pedido;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.PedidoService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService{


	 	@Autowired
	    private PedidoRepository pedidoRepository;

	    @Autowired
	    private UsuarioRepository usuarioRepository;
	    @Override
	    @Transactional
	    public void guardarFactura(HttpSession session) {
	        String correString = (String) session.getAttribute("usuario");
	        UsuarioEntity usuarioEntity = usuarioRepository.findByCorreo(correString);

	        PedidoEntity pedidoEntity = new PedidoEntity();
	        pedidoEntity.setFechaCompra(LocalDate.now());
	        pedidoEntity.setUsuarioEntity(usuarioEntity);

	        List<DetallePedidoEntity> detallePedidoEntityList = new ArrayList<>();
	        List<Pedido> productoSession = (List<Pedido>) session.getAttribute("carrito");

	        if (productoSession != null) {
	            for (Pedido pedido : productoSession) {
	                DetallePedidoEntity detallePedidoEntity = new DetallePedidoEntity();
	                ProductoEntity productoEntity = new ProductoEntity();
	                productoEntity.setProductoId(pedido.getProductoId());

	                detallePedidoEntity.setProductoEntity(productoEntity);
	                detallePedidoEntity.setCantidad(pedido.getCantidad());
	                detallePedidoEntity.setPedidoEntity(pedidoEntity);
	                detallePedidoEntityList.add(detallePedidoEntity);
	            }
	        }

	        pedidoEntity.setDetalleMedicamento(detallePedidoEntityList);
	        pedidoRepository.save(pedidoEntity);

	        session.removeAttribute("carrito");
	    }
	   
	}



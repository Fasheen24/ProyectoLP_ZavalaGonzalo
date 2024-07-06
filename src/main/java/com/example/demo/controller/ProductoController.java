package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.DetallePedidoEntity;
import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.model.Pedido;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.impl.PdfService;

@Controller
public class ProductoController {
    
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/menu_producto")
    public String showMenuProducto(Model model, HttpSession session) {
        if(session.getAttribute("usuario") == null) {
            return "redirect:/";
        }
        model.addAttribute("producto", new ProductoEntity());
        model.addAttribute("categorias", categoriaRepository.findAll());
        String correo = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
        model.addAttribute("foto", usuarioEntity.getUrlImagen());

        List<ProductoEntity> productos = productoService.buscarTodosProductos();
        model.addAttribute("productos", productos);

        List<Pedido> productoSession = session.getAttribute("carrito") == null ? new ArrayList<>() : 
            (List<Pedido>) session.getAttribute("carrito");

        model.addAttribute("cant_carrito", productoSession.size());

        List<DetallePedidoEntity> detallePedidoEntitiyList = new ArrayList<DetallePedidoEntity>();
        Double total = 0.0;

        for (Pedido pedido : productoSession) {
            DetallePedidoEntity detallePedidoEntity = new DetallePedidoEntity();
            ProductoEntity productoEntity = productoService.buscarProductoPorId(pedido.getProductoId());
            detallePedidoEntity.setProductoEntity(productoEntity);
            detallePedidoEntity.setCantidad(pedido.getCantidad());
            detallePedidoEntitiyList.add(detallePedidoEntity);
            total += pedido.getCantidad() * productoEntity.getPrecio();
        }
        model.addAttribute("carrito", detallePedidoEntitiyList);
        model.addAttribute("total", total);
        
        return "menuproducto";
    }

    @PostMapping("/agregar_producto")
    public String agregarProducto(HttpSession session, @RequestParam("prodId") String prod, @RequestParam("cant") String cant) {
        List<Pedido> productos = null;
        if (session.getAttribute("carrito") == null) {
            productos = new ArrayList<>();
        } else {
            productos = (List<Pedido>) session.getAttribute("carrito");
        }

        Integer cantidad = Integer.parseInt(cant);
        Integer prodId = Integer.parseInt(prod);
        Pedido pedido = new Pedido(cantidad, prodId);
        productos.add(pedido);
        session.setAttribute("carrito", productos);
        return "redirect:/menu_producto";
    }

    @GetMapping("/generar_pdf")
    public ResponseEntity<InputStreamResource> generarPdf(HttpSession session) throws IOException {
        List<Pedido> productoSession = null;
        if (session.getAttribute("carrito") == null) {
            productoSession = new ArrayList<Pedido>();
        } else {
            productoSession = (List<Pedido>) session.getAttribute("carrito");
        }
        List<DetallePedidoEntity> detallePedidoEntityList = new ArrayList<DetallePedidoEntity>();
        Double total = 0.0;

        for (Pedido pedido : productoSession) {
            DetallePedidoEntity detallePedidoEntity = new DetallePedidoEntity();
            ProductoEntity productoEntity = productoService.buscarProductoPorId(pedido.getProductoId());
            detallePedidoEntity.setProductoEntity(productoEntity);
            detallePedidoEntity.setCantidad(pedido.getCantidad());
            detallePedidoEntityList.add(detallePedidoEntity);
            total += pedido.getCantidad() * productoEntity.getPrecio();
        }

        Map<String, Object> datosPdf = new HashMap<String, Object>();
        datosPdf.put("factura", detallePedidoEntityList);
        datosPdf.put("precio_total", total);

        ByteArrayInputStream pdfBytes = pdfService.generarPdfDeHtml("template_pdf", datosPdf);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=medicamento.pdf");

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfBytes));
    }

    @GetMapping("/registrar_producto")
	public String showAgregarProducto(Model model) {
		model.addAttribute("producto",new ProductoEntity());
		model.addAttribute("categorias", categoriaRepository.findAll());
		return "registrarproducto";
	}
	@PostMapping("/registrar_producto")
	public String registrarProducto(ProductoEntity productoEntity,Model model, @RequestParam("foto") MultipartFile foto) {
		productoService.registrarProducto(productoEntity,model,foto);
		return "redirect:/menu_producto";
	}
	
	@GetMapping("/editar_producto/{id}")
	public String showEditarProducto(@PathVariable("id") Integer medicinaId, Model model ) {
		ProductoEntity productobuscarEntity = productoService.buscarProductoPorId(medicinaId);
		model.addAttribute("categorias", categoriaRepository.findAll());
		model.addAttribute("producto", productobuscarEntity);
		return "editar_producto";
	}
	@PostMapping("/editar_producto")
	public String editarproducto( Model model, ProductoEntity productoEntity,@RequestParam("foto") MultipartFile foto ) {
		productoService.editarProducto(productoEntity, foto, model);
		return "redirect:/menu_producto";
	}
	@GetMapping("/buscarproducto/{id}")
	public String buscarPorId(@PathVariable("id") Integer id, Model model) {
		ProductoEntity productoEcontraEntity = productoService.buscarProductoPorId(id);
		model.addAttribute("producto", productoEcontraEntity);
		return "buscarproducto";
	}
	@GetMapping("/eliminar_producto/{id}")
	public String eliminarProducto(@PathVariable("id") Integer id, Model model) {
        productoService.eliminarProducto(id);
        return "redirect:/menu_producto";
    }
}

package com.iyan.donapp.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.iyan.donapp.model.Producto;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.ProductoDto;
import com.iyan.donapp.repositories.ProductoRepository;

@Service
public class ProductoService {

	private ProductoRepository productoRepository;

	public ProductoService(ProductoRepository productoRepository) {
		super();
		this.productoRepository = productoRepository;
	}

	public Producto saveProducto(ProductoDto dto, User user) {
		byte[] bytes = null;
		try {
			bytes = dto.getFoto().getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Producto producto = new Producto(bytes, dto.getTitulo(), dto.getSubtitulo(), dto.getUrgencia(), dto.getTipo(), dto.getFormaEntrega(), dto.getDescripcionEntrega(), dto.getEstado());
		producto.setUsuario(user);
		return productoRepository.save(producto);
	}

	public List<Producto> getAllProductos() {
		List<Producto> productos = productoRepository.findAll();
		for (Producto p: productos) {
			p.setFotoEncoded(Base64.getEncoder().encodeToString(p.getFoto()));
		}
		return productos;
	}

	public Object findAllByUserId(Long id) {
		List<Producto> productos = productoRepository.findAllByUserId(id);
		for (Producto p: productos) {
			p.setFotoEncoded(Base64.getEncoder().encodeToString(p.getFoto()));
		}
		return productos;
	}

}

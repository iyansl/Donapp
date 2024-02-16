package com.iyan.donapp.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	public Producto saveProducto(ProductoDto dto, User user1) {
		byte[] bytes = null;
		try {
			if (dto.getFoto() != null)
				bytes = dto.getFoto().getBytes();
			else
				bytes = obtenerDatosImagenPorDefecto("/static/img/producto.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Producto producto = new Producto(bytes, dto.getTitulo(), dto.getSubtitulo(), dto.getUrgencia(), dto.getTipo(),
				dto.getFormaEntrega(), dto.getDescripcionEntrega(), dto.getEstado());
		producto.setUsuario(user1);
		return productoRepository.save(producto);
	}
	
	public Producto saveProducto(ProductoDto dto, User user1, String ruta) {
		byte[] bytes = null;
		try {
			if (dto.getFoto() != null)
				bytes = dto.getFoto().getBytes();
			else
				bytes = obtenerDatosImagenPorDefecto(ruta);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Producto producto = new Producto(bytes, dto.getTitulo(), dto.getSubtitulo(), dto.getUrgencia(), dto.getTipo(),
				dto.getFormaEntrega(), dto.getDescripcionEntrega(), dto.getEstado());
		producto.setUsuario(user1);
		return productoRepository.save(producto);
	}

	public List<Producto> getAllProductos() {
		List<Producto> productos = productoRepository.findAll();
		for (Producto p : productos) {
			p.updateFotoEncoded();
		}
		return productos;
	}

	public List<Producto> findAllByUserId(Long id) {
		List<Producto> productos = productoRepository.findAllByUserId(id);
		for (Producto p : productos) {
			p.updateFotoEncoded();
		}
		return productos;
	}

	public List<Producto> getAllProductosExceptActiveUser(Long id) {
		List<Producto> productos = productoRepository.findAllExceptActiveUser(id);
		for (Producto p : productos) {
			p.updateFotoEncoded();
		}
		return productos;
	}

	public byte[] obtenerDatosImagenPorDefecto(String ruta) {
		try {
			InputStream inputStream = getClass().getResourceAsStream(ruta);
			if (inputStream != null) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, length);
				}
				return outputStream.toByteArray();
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	public Producto getProductoById(Long id) {
		Optional<Producto> p = productoRepository.findById(id);
		p.get().updateFotoEncoded();
		return p.get();
	}

	public Producto updateProducto(ProductoDto dto, Long id, User obtained) {
		Optional<Producto> productoOptional = productoRepository.findById(id);
		if (productoOptional.isPresent()) {
			Producto producto = productoOptional.get();

			if (producto.getUsuario().getId() == obtained.getId()) {
				if (dto.getTitulo() != null && !dto.getTitulo().isEmpty()) {
					producto.setTitulo(dto.getTitulo());
				}
				if (dto.getSubtitulo() != null && !dto.getSubtitulo().isEmpty()) {
					producto.setSubtitulo(dto.getSubtitulo());
				}
				if (dto.getEstado() != null && !dto.getEstado().isEmpty()) {
					producto.setEstado(dto.getEstado());
				}
				if (dto.getDescripcionEntrega() != null && !dto.getDescripcionEntrega().isEmpty()) {
					producto.setDescripcionEntrega(dto.getDescripcionEntrega());
				}
				if (dto.getFormaEntrega() != null && !dto.getFormaEntrega().isEmpty()) {
					producto.setFormaEntrega(dto.getFormaEntrega());
				}
				if (dto.getTipo() != null && !dto.getTipo().isEmpty()) {
					producto.setTipo(dto.getTipo());
				}
				if (dto.getUrgencia() != null && !dto.getUrgencia().isEmpty()) {
					producto.setUrgencia(dto.getUrgencia());
				}
				productoRepository.save(producto);
				return producto;
			} else {
				return null;
			}
		}
		return null;
	}

	public Producto cambiarFoto(MultipartFile foto, Long id, User obtained) {
		Optional<Producto> p = productoRepository.findById(id);
		if (p.get().getUsuario().getId() == obtained.getId()) {
			byte[] fotoBytes;
			try {
				fotoBytes = foto.getBytes();
				p.get().setFoto(fotoBytes);
				p.get().updateFotoEncoded();
				productoRepository.save(p.get());
				return p.get();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<Producto> findAllObtainedByUserId(Long id) {
		List<Producto> productos = productoRepository.findAllProductsObtainedByUser(id);
		for (Producto p : productos) {
			p.updateFotoEncoded();
		}
		return productos;
	}

	public List<Producto> findAllDonatedByUserId(Long id) {
		List<Producto> productos = productoRepository.findAllProductsDonatedByUser(id);
		for (Producto p : productos) {
			p.updateFotoEncoded();
		}
		return productos;
	}

	public List<Producto> buscarProductosConFiltros(String busqueda, String urgencia, String tipo, String recogida,Long usuarioId) {
		if (busqueda == null) {
			busqueda = "";
		}
		if ("Todos".equals(urgencia)) {
			urgencia = "";
		}
		if ("Todos".equals(tipo)) {
			tipo = "";
		}
		if ("Todos".equals(recogida)) {
			recogida = "";
		}
		List<Producto> productosFiltrados = productoRepository.buscarProductosConFiltros(busqueda, urgencia, tipo,
				recogida, usuarioId);

		for (Producto p : productosFiltrados) {
			p.updateFotoEncoded();
		}

		return productosFiltrados;
	}

	public void deleteProductoById(Long id) {
		productoRepository.deleteById(id);
	}

}

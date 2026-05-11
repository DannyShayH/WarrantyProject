package app.services.entityServices;

import app.daos.ProductDAO;
import app.dto.ProductDTO;
import app.entity.Product;
import app.services.dtoConverter.ProductDTOConverter;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductService {

    private final EntityManagerFactory emf;
    private final ProductDAO productDAO;
    private final ProductDTOConverter converter;

    public ProductService(EntityManagerFactory emf) {
        productDAO = new ProductDAO(emf);
        this.converter = new ProductDTOConverter(emf);
        this.emf = emf;
    }

    public ProductDTO create(ProductDTO productDTO) {
        Product product = converter.fromDTO(productDTO);
        Product created = productDAO.create(product);
        return converter.toDTO(created);
    }

    public Set<ProductDTO> get() {
        return productDAO.get()
            .stream()
            .map(converter::toDTO)
            .collect(Collectors.toSet());
    }

    public Set<ProductDTO> getAllByUserId(Long userId) {
        return productDAO.getAllByUserId(userId)
                .stream()
                .map(converter::toDTO)
                .collect(Collectors.toSet());
    }

    public ProductDTO getByID(Long id) {
        Product product = productDAO.getByID(id);
        return converter.toDTO(product);
    }

    public ProductDTO update(ProductDTO productDTO) {
        Product product = converter.fromDTO(productDTO);
        Product updated = productDAO.update(product);
        return converter.toDTO(updated);
    }

    public Long delete(long id) {
        Product product = productDAO.getByID(id);
        return productDAO.delete(product);
    }
}

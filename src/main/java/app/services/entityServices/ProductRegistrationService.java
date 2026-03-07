package app.services.entityServices;

import app.daos.ProductRegistrationDAO;
import app.dto.ProductRegistrationDTO;
import app.entity.ProductRegistration;
import app.services.dtoConverter.ProductRegistrationDTOConverter;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductRegistrationService {

    private final ProductRegistrationDAO productRegistrationDAO;
    private final ProductRegistrationDTOConverter converter;
    private final EntityManagerFactory emf;

    public ProductRegistrationService(EntityManagerFactory emf){
        this.productRegistrationDAO = new ProductRegistrationDAO(emf);
        this.converter = new ProductRegistrationDTOConverter(emf);
        this.emf = emf;
    }

    public ProductRegistrationDTO create(ProductRegistrationDTO productRegistrationDTO) {
        ProductRegistration productRegistration = converter.fromDTO(productRegistrationDTO);
        ProductRegistration created = productRegistrationDAO.create(productRegistration);
        return converter.toDTO(created);
    }

    public Set<ProductRegistrationDTO> get() {
        return productRegistrationDAO.get()
                .stream()
                .map(converter::toDTO)
                .collect(Collectors.toSet());
    }

    public ProductRegistrationDTO getByID(Long id) {
       ProductRegistration productRegistration = productRegistrationDAO.getByID(id);
       return converter.toDTO(productRegistration);
    }

    public ProductRegistrationDTO update(ProductRegistrationDTO productRegistrationDTO) {
    ProductRegistration productRegistration = converter.fromDTO(productRegistrationDTO);
    ProductRegistration updated = productRegistrationDAO.update(productRegistration);
    return converter.toDTO(updated);
    }

    public Long delete(long id) {
        ProductRegistration productRegistration = productRegistrationDAO.getByID(id);
        return productRegistrationDAO.delete(productRegistration);

    }
}

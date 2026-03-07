package app.services.entityServices;

import app.daos.WarrantyDAO;
import app.dto.WarrantyDTO;
import app.entity.Warranty;
import app.services.dtoConverter.WarrantyDTOConverter;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class WarrantyService {
    private final EntityManagerFactory emf;
    private final WarrantyDTOConverter converter;
    private final WarrantyDAO warrantyDAO;

    public WarrantyService(EntityManagerFactory emf) {
        this.emf = emf;
        this.warrantyDAO = new WarrantyDAO(emf);
        this.converter = new WarrantyDTOConverter(emf);
    }

    public WarrantyDTO create(WarrantyDTO warrantyDTO) {
        Warranty warranty = converter.fromDTO(warrantyDTO);
        Warranty created = warrantyDAO.create(warranty);
        return converter.toDTO(created);
    }

    public Set<WarrantyDTO> get() {
        return warrantyDAO.get()
                .stream()
                .map(converter::toDTO)
                .collect(Collectors.toSet());
    }

    public WarrantyDTO getByID(Long id) {
        Warranty warranty = warrantyDAO.getByID(id);
        return converter.toDTO(warranty);
    }

    public WarrantyDTO update(WarrantyDTO warrantyDTO) {
        Warranty warranty = converter.fromDTO(warrantyDTO);
        Warranty updated = warrantyDAO.update(warranty);
        return converter.toDTO(updated);
    }

    public Long delete(long id) {
        Warranty warranty = warrantyDAO.getByID(id);
        return warrantyDAO.delete(warranty);
    }
}

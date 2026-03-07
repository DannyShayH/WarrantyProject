package app.services.entityServices;

import app.daos.ReceiptDAO;
import app.dto.ReceiptDTO;
import app.entity.Receipt;
import app.services.dtoConverter.ReceiptDTOConverter;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class ReceiptService {
    private final EntityManagerFactory emf;
    private final ReceiptDAO receiptDAO;
    private final ReceiptDTOConverter converter;

    public ReceiptService(EntityManagerFactory emf) {
        this.emf = emf;
        this.converter = new ReceiptDTOConverter(emf);
        this.receiptDAO = new ReceiptDAO(emf);
    }

    public ReceiptDTO create(ReceiptDTO receiptDTO) {
        Receipt receipt = converter.fromDTO(receiptDTO);
        Receipt created = receiptDAO.create(receipt);
        return converter.toDTO(created);
    }

    public Set<ReceiptDTO> get() {
        return receiptDAO.get().stream()
                .map(converter::toDTO)
                .collect(Collectors.toSet());
    }

    public ReceiptDTO getByID(Long id) {
        Receipt receipt = receiptDAO.getByID(id);
        return converter.toDTO(receipt);
    }

    public ReceiptDTO update(ReceiptDTO receiptDTO) {
        Receipt receipt = converter.fromDTO(receiptDTO);
        Receipt updated = receiptDAO.update(receipt);
        return converter.toDTO(updated);
    }

    public Long delete(long id) {
        Receipt receipt = receiptDAO.getByID(id);
        return receiptDAO.delete(receipt);
    }
}

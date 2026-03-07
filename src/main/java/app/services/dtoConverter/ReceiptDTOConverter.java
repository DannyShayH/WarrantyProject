package app.services.dtoConverter;

import app.daos.ProductRegistrationDAO;
import app.dto.ReceiptDTO;
import app.entity.ProductRegistration;
import app.entity.Receipt;
import jakarta.persistence.EntityManagerFactory;

public class ReceiptDTOConverter {
    private final EntityManagerFactory emf;
    private final ProductRegistrationDAO productRegistrationDAO;

    public ReceiptDTOConverter(EntityManagerFactory emf, ProductRegistrationDAO productRegistrationDAO){
        this.emf = emf;
        this.productRegistrationDAO = productRegistrationDAO;
    }

    public Receipt fromDTO(ReceiptDTO receiptDTO){
        Receipt receipt = new Receipt();
        if(receiptDTO.getId() != 0){
            receipt.setId(receiptDTO.getId());
        }
        if(receiptDTO.getProductRegistrationId() != 0){
             ProductRegistration productRegistration = productRegistrationDAO.getByID(receiptDTO.getProductRegistrationId());
            if(productRegistration != null){
                receipt.setRegistration(productRegistration);
            }
        }
        receipt.setPurchasedAt(receiptDTO.getPurchasedAt());
        receipt.setPrice(receiptDTO.getPrice());
        receipt.setDescription(receiptDTO.getDescription());

        return receipt;
    }

    public ReceiptDTO toDTO(Receipt receipt){
        ReceiptDTO receiptDTO = new ReceiptDTO();
        receiptDTO.setId(receipt.getId());
        if(receipt.getRegistration() != null){
        receiptDTO.setProductRegistrationId(receipt.getRegistration().getId());
        }
        receiptDTO.setDescription(receipt.getDescription());
        receiptDTO.setPurchasedAt(receipt.getPurchasedAt());
        receiptDTO.setPrice(receipt.getPrice());
        return receiptDTO;
    }
}

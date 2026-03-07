package app.services.dtoConverter;

import app.daos.ReceiptDAO;
import app.daos.UserDAO;
import app.dto.ProductRegistrationDTO;
import app.entity.*;
import jakarta.persistence.EntityManagerFactory;

public class ProductRegistrationDTOConverter {
    private final EntityManagerFactory emf;
    private final UserDAO userDAO;
    private final ReceiptDAO receiptDAO;

    public ProductRegistrationDTOConverter(EntityManagerFactory emf){
        this.emf = emf;
        this.userDAO = new UserDAO(emf);
        this.receiptDAO = new ReceiptDAO(emf);
    }

    public ProductRegistration fromDTO(ProductRegistrationDTO productRegistrationDTO){
        ProductRegistration productRegistration = new ProductRegistration();
        if(productRegistrationDTO.getId() != 0){
            productRegistration.setId(productRegistrationDTO.getId());
        }
        if(productRegistrationDTO.getUserId() != 0){
            User user = userDAO.getByID(productRegistrationDTO.getUserId());
            if(user != null){
                productRegistration.setOwner(user);
            }
        }
        if(productRegistrationDTO.getReceiptId() != 0){
            Receipt receipt = receiptDAO.getByID(productRegistrationDTO.getReceiptId());
            if(receipt != null){
                productRegistration.setReceipt(receipt);
            }
        }
        productRegistration.setRegisteredAt(productRegistrationDTO.getRegisteredAt());
        productRegistration.setPurchasedAt(productRegistrationDTO.getPurchasedAt());

        return productRegistration;
    }

    public ProductRegistrationDTO toDTO(ProductRegistration productRegistration){
        ProductRegistrationDTO productRegistrationDTO = new ProductRegistrationDTO();
        if(productRegistration.getProduct() != null){
        productRegistrationDTO.setProductId(productRegistration.getProduct().getId());
        }
        if(productRegistration.getOwner() !=null){
        productRegistrationDTO.setUserId(productRegistration.getOwner().getId());
        }
        if(productRegistration.getReceipt() !=null){
        productRegistrationDTO.setReceiptId(productRegistration.getReceipt().getId());
        }
        productRegistrationDTO.setRegisteredAt(productRegistration.getRegisteredAt());
        productRegistrationDTO.setPurchasedAt(productRegistration.getPurchasedAt());
        return productRegistrationDTO;
    }
}

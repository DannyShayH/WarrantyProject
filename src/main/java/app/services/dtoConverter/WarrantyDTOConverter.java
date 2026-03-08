package app.services.dtoConverter;

import app.daos.ProductDAO;
import app.dto.WarrantyDTO;
import app.entity.Product;
import app.entity.Warranty;
import jakarta.persistence.EntityManagerFactory;

public class WarrantyDTOConverter {
    private final EntityManagerFactory emf;
    private final ProductDAO productDAO;

    public WarrantyDTOConverter(EntityManagerFactory emf){
        this.emf = emf;
        this.productDAO = new ProductDAO(emf);
    }

    public Warranty fromDTO(WarrantyDTO warrantyDTO){
        Warranty warranty = new Warranty();
        if(warrantyDTO.getId() != 0){
            warranty.setId(warrantyDTO.getId());
        }
        if(warrantyDTO.getProductId() != 0){
            Product product = productDAO.getByID(warrantyDTO.getProductId());
            if(product != null){
                warranty.setProduct(product);
            }
        }
        warranty.setWarrantyMonths(warrantyDTO.getWarrantyMonths());
        warranty.setStartDate(warrantyDTO.getStartDate());
        warranty.calculateEndDate();

        return warranty;
    }

    public WarrantyDTO toDTO(Warranty warranty){
        WarrantyDTO warrantyDTO = new WarrantyDTO();
        warrantyDTO.setId(warranty.getId());
        if(warranty.getProduct() != null){
        warrantyDTO.setProductId(warranty.getProduct().getId());
        }
        warrantyDTO.setStartDate(warranty.getStartDate());
        warrantyDTO.setEndDate(warranty.getEndDate());
        warrantyDTO.setWarrantyMonths(warranty.getWarrantyMonths());
        warrantyDTO.setNotified90Days(warranty.isNotified90Days());
        warrantyDTO.setNotified60Days(warranty.isNotified60Days());
        warrantyDTO.setNotified30Days(warranty.isNotified30Days());
        warrantyDTO.setNotifiedExpired(warranty.isNotifiedExpired());
        return warrantyDTO;
    }
}

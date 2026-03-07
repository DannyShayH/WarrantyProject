package app.services.dtoConverter;

import app.daos.UserDAO;
import app.daos.WarrantyDAO;
import app.dto.ProductDTO;
import app.entity.Product;
import app.entity.User;
import app.entity.Warranty;
import jakarta.persistence.EntityManagerFactory;

public class ProductDTOConverter {
    private final EntityManagerFactory emf;
    private final UserDAO userDAO;
    private final WarrantyDAO warrantyDAO;

    public ProductDTOConverter(EntityManagerFactory emf){
        this.emf = emf;
        this.userDAO = new UserDAO(emf);
        this.warrantyDAO = new WarrantyDAO(emf);
    }

    public Product fromDTO(ProductDTO productDTO){
        Product product = new Product();
        if(productDTO.getId() != 0){
            product.setId(productDTO.getId());
        }
        product.setProductName(productDTO.getProductName());

        if(productDTO.getUserId() != 0){
            User user = userDAO.getByID(productDTO.getUserId());
            if(user != null){
                product.setOwner(user);
            }
        }
        if(productDTO.getWarrantyId() != 0){
            Warranty warranty = warrantyDAO.getByID(productDTO.getWarrantyId());
            if(warranty != null){
                product.setWarranty(warranty);
            }
        }
        return product;
    }

    public ProductDTO toDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProductName());
        if(product.getOwner() != null){
            productDTO.setUserId(product.getOwner().getId());
        }
        if(product.getWarranty() != null){
            productDTO.setWarrantyId(product.getWarranty().getId());
        }
        return productDTO;
    }
}

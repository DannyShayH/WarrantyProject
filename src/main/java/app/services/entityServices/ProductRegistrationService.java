package app.services.entityServices;

import app.daos.ProductRegistrationDAO;
import app.entity.ProductRegistration;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class ProductRegistrationService implements CrudService<ProductRegistration> {

    private final ProductRegistrationDAO productRegistrationDAO;
    private final EntityManagerFactory emf;

    public ProductRegistrationService(EntityManagerFactory emf, ProductRegistrationDAO productRegistrationDAO){
        this.productRegistrationDAO = productRegistrationDAO;
        this.emf = emf;
    }

    @Override
    public ProductRegistration create(ProductRegistration productRegistration) {return productRegistrationDAO.create(productRegistration);}

    @Override
    public Set<ProductRegistration> get() {return productRegistrationDAO.get();}

    @Override
    public ProductRegistration getByID(Long id) {return productRegistrationDAO.getByID(id);}

    @Override
    public ProductRegistration update(ProductRegistration productRegistration) {return productRegistrationDAO.update(productRegistration);}

    @Override
    public Long delete(ProductRegistration productRegistration) {return productRegistrationDAO.delete(productRegistration);}
}

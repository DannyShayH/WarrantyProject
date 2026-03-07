package app.services.entityServices;

import app.daos.ReceiptDAO;
import app.entity.Receipt;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class ReceiptService implements CrudService<Receipt> {
    private final EntityManagerFactory emf;
    private final ReceiptDAO receiptDAO;

    public ReceiptService(EntityManagerFactory emf, ReceiptDAO receiptDAO) {
        this.emf = emf;
        this.receiptDAO = receiptDAO;
    }

    @Override
    public Receipt create(Receipt receipt) {return receiptDAO.create(receipt);}

    @Override
    public Set<Receipt> get() {return receiptDAO.get();}

    @Override
    public Receipt getByID(Long id) {return receiptDAO.getByID(id);}

    @Override
    public Receipt update(Receipt receipt) {return receiptDAO.update(receipt);}

    @Override
    public Long delete(Receipt receipt) {return receiptDAO.delete(receipt);}
}

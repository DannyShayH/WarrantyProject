package app.services.persistenceServices;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

public class EntityManagerFactoryService {
    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    public static EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }
}

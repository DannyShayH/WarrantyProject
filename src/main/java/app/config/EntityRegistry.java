package app.config;


import app.entity.*;
import org.hibernate.cfg.Configuration;

final class EntityRegistry {

    private EntityRegistry() {}

    static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Receipt.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Warranty.class);
        configuration.addAnnotatedClass(ProductRegistration.class);
    }
}
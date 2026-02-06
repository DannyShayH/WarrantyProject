package app.config;


import app.entity.Product;
import app.entity.Receipt;
import app.entity.User;
import app.entity.Warranty;
import org.hibernate.cfg.Configuration;

final class EntityRegistry {

    private EntityRegistry() {}

    static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Receipt.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Warranty.class);
    }
}
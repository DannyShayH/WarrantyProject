package app.persistence;

import app.entity.Product;
import app.entity.Receipt;
import app.entity.User;
import app.entity.Warranty;

import java.util.Set;

public interface IRetrieveDAO {

    Set<Product> getAllProductsForUsers(long userId);

    Set<User> getAllUsersForProduct(String productName);

    Set<Product> getProductsWithUserEmailDomain(String domain);

    Set<Warranty> getAllWarrantiesForUsers(long userId);

    Set<Receipt> getAllReceiptForUsers(long userId);

    Set<Receipt> getReceiptWithSpecificPrice(double price);

    Set<Receipt> getReceiptWithPriceRange(double price);
}

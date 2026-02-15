package app.persistence;

import java.util.Set;

public interface IDAO<T> {

    T create(T t);

    Set<T> get();

    T getByID(Long id);

    T update(T t);

    Long delete(T t);


}

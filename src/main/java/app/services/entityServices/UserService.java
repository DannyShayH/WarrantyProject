package app.services.entityServices;

import app.daos.UserDAO;
import app.dto.UserDTO;
import app.entity.User;
import app.services.dtoConverter.UserDTOConverter;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class UserService {
    private final EntityManagerFactory emf;
    private final UserDAO userDAO;
    private final UserDTOConverter converter;

    public UserService(EntityManagerFactory emf) {
        this.emf = emf;
        this.userDAO = new UserDAO(emf);
        this.converter = new UserDTOConverter(emf);
    }

    public UserDTO create(UserDTO userDTO) {
        User user = converter.fromDTO(userDTO);
        User created = userDAO.create(user);
        return converter.toDTO(created);
    }

    public Set<UserDTO> get() {
        return userDAO.get()
                .stream()
                .map(converter::toDTO)
                .collect(Collectors.toSet());
    }

    public UserDTO getByID(Long id) {
        User user = userDAO.getByID(id);
        return converter.toDTO(user);
    }

    public UserDTO update(UserDTO userDTO) {
        User user = converter.fromDTO(userDTO);
        User updated = userDAO.update(user);
        return converter.toDTO(updated);
    }

    public Long delete(long id) {
        User user = userDAO.getByID(id);
        return userDAO.delete(user);
    }
}

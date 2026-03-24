package app.services.entityServices;

import app.daos.UserDAO;
import app.dto.UserDTO;
import app.entity.User;
import app.services.dtoConverter.UserDTOConverter;
import app.services.validationServices.PasswordService;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class UserService {
    private final UserDAO userDAO;
    private final UserDTOConverter converter;
    private final PasswordService passwordService;

    public UserService(EntityManagerFactory emf) {
        this.userDAO = new UserDAO(emf);
        this.converter = new UserDTOConverter(emf);
        this.passwordService = new PasswordService();
    }

    public UserDTO create(UserDTO userDTO) {
        User user = converter.fromDTO(userDTO);
        user.setPassword(passwordService.hash(userDTO.getPassword()));
        if(user.getCreatedAt() == null){user.setCreatedAt(LocalDateTime.now());}
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
        User user = userDAO.getByIDWithRegistrations(id);
        return converter.toDTOWithRegistrations(user);
    }

    public UserDTO update(UserDTO userDTO) {
        User existing = userDAO.getByID(userDTO.getId());
        existing.setEmail(userDTO.getEmail());
        User updated = userDAO.update(existing);
        return converter.toDTO(updated);
    }

    public Long delete(long id) {
        User user = userDAO.getByID(id);
        return userDAO.delete(user);
    }

}

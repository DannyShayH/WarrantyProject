package app.services.dtoConverter;

import app.dto.ProductRegistrationDTO;
import app.dto.UserDTO;
import app.entity.User;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserDTOConverter {
    private final ProductRegistrationDTOConverter registrationDTOConverter;


    public UserDTOConverter(EntityManagerFactory emf){
        this.registrationDTOConverter = new ProductRegistrationDTOConverter(emf);
    }

    public User fromDTO(UserDTO userDTO){
        User user = new User();
        if(userDTO.getId() != 0){
            user.setId(userDTO.getId());
        }
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setCreatedAt(userDTO.getCreatedAt());

        return user;
    }

    public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;
    }

    public UserDTO toDTOWithRegistrations(User user){
        UserDTO userDTO = toDTO(user);
        List<ProductRegistrationDTO> registrationDTOS = user.getRegistrationlist()
                .stream()
                .map(registrationDTOConverter::toDTO)
                .toList();
        userDTO.setRegistrationList(registrationDTOS);
        return userDTO;
    }
}

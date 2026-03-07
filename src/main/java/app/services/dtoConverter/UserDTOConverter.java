package app.services.dtoConverter;

import app.daos.UserDAO;
import app.dto.ProductRegistrationDTO;
import app.dto.UserDTO;
import app.entity.ProductRegistration;
import app.entity.User;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserDTOConverter {
    private final EntityManagerFactory emf;
    private final ProductRegistrationDTOConverter registrationDTOConverter;


    public UserDTOConverter(EntityManagerFactory emf){
        this.emf = emf;
        this.registrationDTOConverter = new ProductRegistrationDTOConverter(emf);
    }

    public User fromDTO(UserDTO userDTO){
        User user = new User();
        if(userDTO.getId() != 0){
            user.setId(userDTO.getId());
        }
        if(userDTO.getRegistrationList() != null){
            List<ProductRegistration> registrations = userDTO.getRegistrationList()
                    .stream()
                    .map(registrationDTOConverter::fromDTO)
                    .toList();
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
        userDTO.setPassword(user.getPassword());
        userDTO.setCreatedAt(user.getCreatedAt());
        if(user.getRegistrationlist() != null){
            List<ProductRegistrationDTO> registrationDTOS = user.getRegistrationlist()
                    .stream()
                    .map(registrationDTOConverter::toDTO)
                    .toList();
            userDTO.setRegistrationList(registrationDTOS);

        }
        return userDTO;
    }
}

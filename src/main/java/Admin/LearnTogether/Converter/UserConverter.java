package Admin.LearnTogether.Converter;

import Admin.LearnTogether.DTO.UserDTO;
import Admin.LearnTogether.Entity.RoleEntity;
import Admin.LearnTogether.Entity.UserEnity;
import Admin.LearnTogether.Repo.RoleRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/*
  Created by Luvbert
*/
@Component
public class UserConverter implements IConverter<UserDTO, UserEnity> {

    private RoleRepo roleRepository;

    public UserConverter(RoleRepo roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDTO toDTO(UserEnity userEnity){
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(userEnity.getUsername());
        userDTO.setEmail(userEnity.getEmail());
        userDTO.setFullname(userEnity.getFullname());
        userDTO.setUserQuote(userEnity.getUserQuote());
        userDTO.setPhoneNumber(userEnity.getPhoneNumber());
        userDTO.setAvatar(userEnity.getAvatar());
        userDTO.setId(userEnity.getId() + "");
        userDTO.setRoleName(userEnity.getRole().getRoleName());

        return userDTO;
    }

    public UserDTO toDTOSecure(UserEnity userEnity){
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(userEnity.getUsername());
        userDTO.setFullname(userEnity.getFullname());
        userDTO.setUserQuote(userEnity.getUserQuote());
        userDTO.setEmail(userDTO.getEmail());
        userDTO.setAvatar(userEnity.getAvatar());
        userDTO.setId(userEnity.getId() + "");
        userDTO.setRoleName(userEnity.getRole().getRoleName());

        return userDTO;
    }

    @Override
    public UserEnity toEntity(UserDTO userDTO){
        UserEnity userEnity = new UserEnity();

        userEnity.setEmail(userDTO.getEmail());
        userEnity.setUsername(userDTO.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userEnity.setPassword(encoder.encode(userDTO.getPassword()));
        userEnity.setAvatar("/assets/Uploads/Avatar/unset-icon.png");
        userEnity.setFullname("Chưa có");
        userEnity.setPhoneNumber("Chưa có");
        userEnity.setUserQuote("Học tập cùng LearnTogether");
        userEnity.setStatus(1);
        RoleEntity role = roleRepository.findById(5l).get();
        userEnity.setRole(role);

        return userEnity;
    }
}

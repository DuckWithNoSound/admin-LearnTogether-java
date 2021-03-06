package Admin.LearnTogether.Service;

import Admin.LearnTogether.DTO.UserDetail;
import Admin.LearnTogether.Entity.UserEnity;
import Admin.LearnTogether.Repo.UserRepo;
import Admin.LearnTogether.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
  Created by Luvbert
*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEnity userEnity = userRepository.findOneByUsernameAndStatus(username, SystemConstant.ACTIVE_STATUS);

        if(userEnity == null){
            throw new UsernameNotFoundException("User with username: \'" + username + "\' not found!");
        }

        if(userEnity.getStatus() == SystemConstant.INACTIVE_STATUS){
            System.out.println(username + " has been banned !");
        }

        return putUserEntityToUserDetail(userEnity);
    }

    private UserDetails putUserEntityToUserDetail(UserEnity userEnity){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEnity.getRole().getRoleName()));
        // Put user's information to spring security store
        UserDetail user = new UserDetail(userEnity.getUsername(),userEnity.getPassword(), true, true, true, true, authorities);
        user.setFullname(userEnity.getFullname());
        user.setAvatar(userEnity.getAvatar());
        user.setUserQuote(userEnity.getUserQuote());
        user.setPhoneNumber(userEnity.getPhoneNumber());
        user.setEmail(userEnity.getEmail());
        user.setId(userEnity.getId());
        user.setRole(userEnity.getRole());
        return user;
    }
}

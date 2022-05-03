package Admin.LearnTogether.DTO;

import Admin.LearnTogether.Entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.util.Collection;

/*
  Created by Luvbert
*/
@Setter
@Getter
public class UserDetail extends User {
    private Long id;
    private String fullname;
    private String avatar;
    private String email;
    private String phoneNumber;
    private String userQuote;
    private Integer status;
    private RoleEntity role;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

}

package Admin.LearnTogether.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
  Created by Luvbert
*/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEnity extends BaseEntity {

    @Column
    private String username;

    @Column
    private String password;

    @Column(columnDefinition = "TINYINT")
    private Integer status;

    @Column
    private String avatar;

    @Column
    private String fullname;

    @Column
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "user_quote")
    private String userQuote;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "user")
    private List<PostEntity> posts = new ArrayList<>();

}

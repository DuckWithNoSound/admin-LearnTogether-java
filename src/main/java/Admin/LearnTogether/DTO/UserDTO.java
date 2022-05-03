package Admin.LearnTogether.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/*
  Created by Luvbert
*/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;

    private String username;

    private String password;

    private String fullname;

    private String avatar;

    private String email;

    private String phoneNumber;

    private String userQuote;

    private Integer status;

    private Long roleId;

    private String roleName;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private Integer numberOfUserPost;

    private Integer totalScore;

    private String newPassword;

}

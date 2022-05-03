package Admin.LearnTogether.DTO;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/*
  Created by Luvbert
*/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 255)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 255)
    private String password;

}

package Admin.LearnTogether.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/*
  Created by Luvbert
*/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractDTO {
    private Long id;

    private Date createdDate;
    private Date modifiedDate;

}

package Admin.LearnTogether.DTO;

/*
  Created by Luvbert
*/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO{
    private Long id;
    private String tagName;
    private String tagSlug;

}

package Admin.LearnTogether.DTO;

import Admin.LearnTogether.Entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
  Created by Luvbert
*/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostDTO extends AbstractDTO{
    private String content;
    private Integer score;
    private Long postId;
    private PostEntity post;
    private String authorName;
    private String authorRole;
    private String authorAvatar;

    private Long[] ids;

}

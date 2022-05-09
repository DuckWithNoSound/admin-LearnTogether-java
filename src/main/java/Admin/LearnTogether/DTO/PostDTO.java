package Admin.LearnTogether.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/*
  Created by Luvbert
*/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO extends AbstractDTO{
    private String title;
    private String content;
    private String image;
    private Integer viewNumber;
    private Integer score;
    private String[] listTagSlug;
    private String authorName;
    private UserDTO author;
    private List<CommentPostDTO> comments;

    private Long[] ids;

    public List<String> getListTagSlug() {
        if(listTagSlug == null) return null;
        List<String> list = Arrays.asList(listTagSlug);
        return list;
    }

    public void setListTagSlug(List<String> listTagSlug){
        this.listTagSlug = listTagSlug.toArray(new String[0]);
    }

}

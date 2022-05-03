package Admin.LearnTogether.Entity;

/*
  Created by Luvbert
*/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment_post")
public class CommentPostEntity extends BaseEntity {

    @Column(name = "comment_content")
    private String content;

    @Column(name = "comment_score")
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEnity user;

}

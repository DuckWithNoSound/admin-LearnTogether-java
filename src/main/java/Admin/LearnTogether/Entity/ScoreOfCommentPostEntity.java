package Admin.LearnTogether.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/*
  Created by Luvbert
*/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "score_of_comment_post")
public class ScoreOfCommentPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score_type", columnDefinition = "TINYINT")
    private Byte scoreType;

    @ManyToOne
    @JoinColumn(name = "comment_post_id")
    private CommentPostEntity commentPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEnity user;

}

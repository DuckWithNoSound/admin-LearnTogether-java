package Admin.LearnTogether.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

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
@Table(name = "post")
public class PostEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "image")
    private String image;

    @Column(name = "view_number")
    private Integer viewNumber;

    @Column(name = "score")
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEnity user;

    @OneToMany(mappedBy = "post")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @OrderBy("score desc")
    List<CommentPostEntity> commentsPost = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "post_tag_mtm", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagEntity> tags = new ArrayList<>();

}

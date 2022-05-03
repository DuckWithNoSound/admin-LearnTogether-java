package Admin.LearnTogether.Repo;

import Admin.LearnTogether.Entity.CommentPostEntity;
import Admin.LearnTogether.Entity.ScoreOfCommentPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
  Created by Luvbert
*/
@Repository
public interface ScoreOfCommentPostRepo extends JpaRepository<ScoreOfCommentPostEntity, Long> {
    ScoreOfCommentPostEntity findByCommentPostIdAndUserId(Long commentPostId, Long UserId);
    void deleteAllByCommentPost(CommentPostEntity commentPostEntity);
}

package Admin.LearnTogether.Repo;

import Admin.LearnTogether.Entity.PostEntity;
import Admin.LearnTogether.Entity.ScorePostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*
  Created by Luvbert
*/
public interface ScorePostRepo extends JpaRepository<ScorePostEntity, Long> {
    Integer countByPostIdAndUserId(Long postId, Long userId);
    Integer countAllByPost(PostEntity postEntity);
    ScorePostEntity findByPostIdAndUserId(Long postId, Long userId);
    void deleteAllByPost(PostEntity postEntity);
}

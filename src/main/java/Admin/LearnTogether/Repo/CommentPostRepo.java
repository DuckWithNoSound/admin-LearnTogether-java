package Admin.LearnTogether.Repo;

import Admin.LearnTogether.Entity.CommentPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
  Created by Luvbert
*/
@Repository
public interface CommentPostRepo extends JpaRepository<CommentPostEntity, Long> {
}

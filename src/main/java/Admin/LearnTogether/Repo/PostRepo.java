package Admin.LearnTogether.Repo;

import Admin.LearnTogether.Entity.PostEntity;
import Admin.LearnTogether.Entity.UserEnity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
  Created by Luvbert
*/
public interface PostRepo extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findAllByUser(UserEnity user);
    Integer countAllByUser(UserEnity user);
}

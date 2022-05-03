package Admin.LearnTogether.Repo;

import Admin.LearnTogether.Entity.UserEnity;
import org.springframework.data.jpa.repository.JpaRepository;

/*
  Created by Luvbert
*/
public interface UserRepo extends JpaRepository<UserEnity, Long> {
    UserEnity findOneByUsernameAndStatus(String username, int status);
    UserEnity findByEmail(String email);
    UserEnity findByUsername(String username);
    UserEnity findUserById(Long id);
}

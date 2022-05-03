package Admin.LearnTogether.Repo;

import Admin.LearnTogether.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
  Created by Luvbert
*/
@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    public RoleEntity findByRoleName(String roleName);
    public Optional<RoleEntity> findById(Long id);
}

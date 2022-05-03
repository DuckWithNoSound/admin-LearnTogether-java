package Admin.LearnTogether.Repo;

import Admin.LearnTogether.Entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*
  Created by Luvbert
*/
public interface TagRepo extends JpaRepository<TagEntity, String> {
    TagEntity findByName(String tagName);
    TagEntity findBySlug(String tagSlug);
}

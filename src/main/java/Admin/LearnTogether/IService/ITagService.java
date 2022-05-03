package Admin.LearnTogether.IService;

import Admin.LearnTogether.DTO.TagDTO;
import Admin.LearnTogether.Entity.TagEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/*
  Created by Luvbert
*/
@Service
public interface ITagService {
    List<TagDTO> findAll() throws Exception;
    TagEntity findByName(String tagName);
    TagEntity findBySlug(String tagSlug);
}

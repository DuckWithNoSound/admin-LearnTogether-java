package Admin.LearnTogether.Service;

import Admin.LearnTogether.Converter.TagConverter;
import Admin.LearnTogether.DTO.TagDTO;
import Admin.LearnTogether.Entity.TagEntity;
import Admin.LearnTogether.IService.ITagService;
import Admin.LearnTogether.Repo.TagRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
  Created by Luvbert
*/
@Service
public class TagService implements ITagService {

    private TagRepo tagRepository;
    private TagConverter tagConverter;

    public TagService(TagRepo tagRepository, TagConverter tagConverter){
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
    }

    @Override
    public List<TagDTO> findAll() throws Exception {
        List<TagEntity> entities = tagRepository.findAll();
        List<TagDTO> dtos = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            dtos.add(tagConverter.toDTO(entities.get(i)));
        }

        return dtos;
    }

    @Override
    public TagEntity findByName(String tagName) {
        return tagRepository.findByName(tagName);
    }

    @Override
    public TagEntity findBySlug(String tagSlug) {
        return tagRepository.findBySlug(tagSlug);
    }

}

package Admin.LearnTogether.Converter;

import Admin.LearnTogether.DTO.CommentPostDTO;
import Admin.LearnTogether.DTO.PostDTO;
import Admin.LearnTogether.Entity.CommentPostEntity;
import Admin.LearnTogether.Entity.PostEntity;
import Admin.LearnTogether.Entity.TagEntity;
import Admin.LearnTogether.IService.ITagService;
import Admin.LearnTogether.Repo.UserRepo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/*
  Created by Luvbert
*/
@Component
public class PostConverter implements IConverter<PostDTO, PostEntity> {

    private ITagService tagService;
    private UserRepo userRepository;
    private UserConverter userConverter;
    private CommentPostConverter commentPostConverter;

    public PostConverter(ITagService tagService, UserRepo userRepository, UserConverter userConverter, CommentPostConverter commentPostConverter){
        this.tagService = tagService;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.commentPostConverter = commentPostConverter;
    }

    @Override
    public PostDTO toDTO(PostEntity postEntity){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setContent(postEntity.getContent());
        postDTO.setAuthorName(postEntity.getUser().getUsername());
        postDTO.setCreatedDate(postEntity.getCreatedDate());
        postDTO.setModifiedDate(postEntity.getModifiedDate());
        postDTO.setViewNumber(postEntity.getViewNumber());
        postDTO.setScore(postEntity.getScore());
        // get all tag slugs
        List<TagEntity> tags = postEntity.getTags();
        List<String> listTagSlug = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            listTagSlug.add(tags.get(i).getSlug());
        }
        postDTO.setListTagSlug(listTagSlug);
        //
        postDTO.setAuthor(userConverter.toDTOSecure(postEntity.getUser()));
        //
        List<CommentPostEntity> commentPostEntities = postEntity.getCommentsPost();
        List<CommentPostDTO> commentPostDTOS = new ArrayList<>();
        for (int i = 0; i < commentPostEntities.size(); i++) {
            commentPostDTOS.add(commentPostConverter.toDTO(commentPostEntities.get(i)));
        }
        postDTO.setComments(commentPostDTOS);
        return postDTO;
    }

    @Override
    public PostEntity toEntity(PostDTO postDTO){
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        if(postDTO.getViewNumber() == null) postDTO.setViewNumber(0);
        postEntity.setViewNumber(postDTO.getViewNumber());
        if(postDTO.getScore() == null) postDTO.setScore(0);
        postEntity.setScore(postDTO.getScore());
        postEntity.setCreatedDate(new Date());
        //tags
        List<TagEntity> tags = new ArrayList<>();
        for(String tagSlug : postDTO.getListTagSlug()){
            tags.add(tagService.findBySlug(tagSlug));
        }
        postEntity.setTags(tags);
        //
        postEntity.setUser(userRepository.findByUsername(postDTO.getAuthorName()));
        return postEntity;
    }
}

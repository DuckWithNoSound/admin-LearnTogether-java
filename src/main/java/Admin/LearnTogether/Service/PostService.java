package Admin.LearnTogether.Service;

import Admin.LearnTogether.Converter.PostConverter;
import Admin.LearnTogether.DTO.PostDTO;
import Admin.LearnTogether.DTO.UserDetail;
import Admin.LearnTogether.Entity.CommentPostEntity;
import Admin.LearnTogether.Entity.PostEntity;
import Admin.LearnTogether.Entity.ScorePostEntity;
import Admin.LearnTogether.Entity.TagEntity;
import Admin.LearnTogether.IService.IPostService;
import Admin.LearnTogether.IService.ITagService;
import Admin.LearnTogether.Repo.PostRepo;
import Admin.LearnTogether.Repo.ScoreOfCommentPostRepo;
import Admin.LearnTogether.Repo.ScorePostRepo;
import Admin.LearnTogether.Repo.UserRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/*
  Created by Luvbert
*/
@Service
@Transactional
public class PostService implements IPostService {

    private PostRepo postRepository;
    private ITagService tagService;
    private UserRepo userRepository;
    private PostConverter postConverter;
    private ScorePostRepo scorePostRepository;
    private ScoreOfCommentPostRepo scoreOfCommentPostRepository;

    public PostService(PostRepo postRepository, ITagService tagService, UserRepo userRepository,
                       PostConverter postConverter, ScorePostRepo scorePostRepository,
                       ScoreOfCommentPostRepo scoreOfCommentPostRepository){
        this.postRepository = postRepository;
        this.tagService = tagService;
        this.userRepository = userRepository;
        this.postConverter = postConverter;
        this.scorePostRepository = scorePostRepository;
        this.scoreOfCommentPostRepository = scoreOfCommentPostRepository;
    }

    @Override
    public PostDTO createNewPost(PostDTO postDTO) throws IllegalArgumentException, Exception {
        String username = ((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        if(username == null){
            throw new Exception("User not login !");
        } else {
            postDTO.setAuthorName(username);
        }
        if(postDTO.getTitle() == null || postDTO.getTitle().isEmpty()){
            throw new Exception("Title is empty !");
        }
        if(postDTO.getContent() == null || postDTO.getContent().isEmpty()){
            throw new Exception("Content is empty !");
        }
        if(postDTO.getListTagSlug() == null || postDTO.getListTagSlug().isEmpty()){
            throw new Exception("Tag is empty !");
        }

        // save and get some hide information back to DTO
        postDTO.setId(postRepository.save(postConverter.toEntity(postDTO)).getId());
        return postDTO;
    }

    @Override
    public PostDTO findPostById(Long postID) throws IllegalArgumentException, Exception {
        Optional<PostEntity> postEntity = postRepository.findById(postID);
        if(!postEntity.isPresent()){
            throw new Exception("Post not found !");
        };
        return postConverter.toDTO(postEntity.get());
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO) throws IllegalArgumentException, Exception{
        String username = ((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        if(username == null){
            throw new Exception("User not login !");
        }
        Optional<PostEntity> postEntity = postRepository.findById(postDTO.getId());
        if(!postEntity.isPresent()){
            throw new Exception("Source post not exist !");
        }
        PostEntity entity = postEntity.get();
        if(postDTO.getTitle() != null) entity.setTitle(postDTO.getTitle());
        if(postDTO.getContent() != null) entity.setContent(postDTO.getContent());
        entity.setModifiedDate(new Date());
        // convert tag slug to tag entity
        if(postDTO.getListTagSlug() != null){
            List<TagEntity> tags = new ArrayList<>();
            for(String tagSlug : postDTO.getListTagSlug()){
                tags.add(tagService.findBySlug(tagSlug));
            }
            entity.setTags(tags);
        }

        entity.setUser(userRepository.findByUsername(username));
        postRepository.save(entity);
        return postConverter.toDTO(postRepository.findById(entity.getId()).get());
    }

    @Override
    public boolean deletePost (Long[] arrPostId) throws Exception {
        UserDetail user = ((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(user == null){
            throw new Exception("User not login !");
        }
        if(arrPostId == null) {
            throw new Exception("Nothing to delete !");
        }
        for (int i = 0; i < arrPostId.length; i++) {
            Optional<PostEntity> temp = postRepository.findById(arrPostId[i]);
            if(!temp.isPresent()){
                throw new Exception("Post not found !");
            }
            PostEntity entity = temp.get();
            if(entity.getUser().getUsername().equals(user.getUsername()) || user.getRole().getId() >= 3){
                scorePostRepository.deleteAllByPost(entity);
                List<CommentPostEntity> commentPostEntities = entity.getCommentsPost();
                for(int j = 0; j < commentPostEntities.size(); j++){
                    scoreOfCommentPostRepository.deleteAllByCommentPost(commentPostEntities.get(j));
                }
                postRepository.delete(entity);
            } else throw new Exception("You do not have permission to delete post(post id = " + entity.getId() + ").");
        }
        return true;
    }

    @Override
    public List<PostDTO> findAll(Pageable pageRequest) throws Exception {

        List<PostEntity> entities = postRepository.findAll(pageRequest).getContent();
        List<PostDTO> dtos = new ArrayList<>();
        for(PostEntity postEntity: entities){
            dtos.add(postConverter.toDTO(postEntity));
        }
        return dtos;
    }

    @Override
    public Long countAllPost() throws Exception {
        return postRepository.count();
    }

    @Override
    public Byte getCurrentScoreVote(Long postId) throws Exception {
        UserDetail user = ((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        ScorePostEntity scorePostEntity = scorePostRepository.findByPostIdAndUserId(postId, user.getId());
        if(scorePostEntity == null){
            return 0;
        } else {
            return scorePostEntity.getScoreType();
        }
    }

    @Override
    public Integer upOrDownScore(Long postId, Byte scoreType) throws Exception {
        Optional<PostEntity> postEntity = postRepository.findById(postId);
        if(!postEntity.isPresent()){
            throw new Exception("Post not found !");
        }
        PostEntity entity = postEntity.get();
        UserDetail user = ((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        ScorePostEntity scorePostEntity = scorePostRepository.findByPostIdAndUserId(postId, user.getId());
        if(scorePostEntity != null){
            if(scorePostEntity.getScoreType() == 1 && scoreType == -1){
                scorePostEntity.setScoreType((byte) -1);
                scorePostRepository.save(scorePostEntity);
                return (entity.getScore()-2);
            } else if (scorePostEntity.getScoreType() == -1 && scoreType == 1){
                scorePostEntity.setScoreType((byte) 1);
                scorePostRepository.save(scorePostEntity);
                return (entity.getScore()+2);
            } else {
                return entity.getScore();
            }
        } else {
            scorePostEntity = new ScorePostEntity();
            scorePostEntity.setScoreType(scoreType);
            scorePostEntity.setPost(entity);
            scorePostEntity.setUser(userRepository.findByUsername(user.getUsername()));
            scorePostRepository.save(scorePostEntity);
            if(scoreType == 1){
                return (entity.getScore()+1);
            } else {
                return (entity.getScore()-1);
            }
        }
    }

    @Override
    public Integer upView(Long postId) throws Exception {
        Optional<PostEntity> postEntity = postRepository.findById(postId);
        if(!postEntity.isPresent()){
            throw new Exception("Post not found !");
        }
        PostEntity entity = postEntity.get();
        Integer viewNumber = entity.getViewNumber() + 1;
        entity.setViewNumber(viewNumber);
        return postRepository.save(entity).getScore();
    }
}

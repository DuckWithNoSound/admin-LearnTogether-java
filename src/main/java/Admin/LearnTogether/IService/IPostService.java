package Admin.LearnTogether.IService;

import Admin.LearnTogether.DTO.PostDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/*
  Created by Luvbert
*/
@Service
public interface IPostService {
    PostDTO createNewPost(PostDTO postDTO, String username) throws Exception;
    PostDTO findPostById(Long postID);
    PostDTO updatePost(PostDTO postDTO) throws Exception;
    boolean deletePost(Long[] arrPostId) throws Exception;
    List<PostDTO> findAll(Pageable pageRequest) throws Exception;
    Long countAllPost() throws Exception;
    Integer upOrDownScore(Long postId, Byte scoreType) throws Exception;
    Integer upView(Long postId) throws Exception;
    Byte getCurrentScoreVote(Long postId) throws Exception;
}

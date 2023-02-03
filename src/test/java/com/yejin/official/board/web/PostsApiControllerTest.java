package com.yejin.official.board.web;

import com.yejin.official.board.domain.posts.Posts;
import com.yejin.official.board.domain.posts.PostsRepository;
import com.yejin.official.board.web.dto.PostsSaveRequestDto;
import com.yejin.official.board.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

//    @AfterEach
//    public void deleteEverything() throws Exception{
//        postsRepository.deleteAll();
//    }

    @Test
    @DisplayName("insert")
    public void posts_enroll() throws Exception{
        String title = "title";
        String content ="content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title).content(content).build();
        String url = "http://localhost:"+port+"/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //assert that response is OK and exist
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        //assert that it is saved correctly
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);


    }

    @Test
    @DisplayName("update posts")
    public void posts_update() throws Exception{
        String updatedTitle ="updated";
        String updatedContent = "updated_content";
        Long id = postsRepository.findAll().get(0).getId();
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(updatedTitle).content(updatedContent) .build();
        String url ="http://localhost:"+port+"/api/v1/posts/"+id;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updatedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updatedContent);

    }

}

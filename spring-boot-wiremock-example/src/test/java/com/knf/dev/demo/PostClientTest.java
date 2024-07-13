package com.knf.dev.demo;

import com.knf.dev.demo.client.PostClient;
import com.knf.dev.demo.model.Post;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableWireMock({
        @ConfigureWireMock(name = "post-client", property = "post.base.url")
})
public class PostClientTest {

    @Autowired
    private PostClient postClient;

    @Test
    void shouldGetPost_whenFetchingWithValidId() {
        Post post = postClient.findById(1);
        assertThat(post.getId()).isEqualTo(1);
        assertThat(post.getTitle()).isEqualTo("Spring Boot Wire Mock Example");
        assertThat(post.getBody()).isEqualTo("Testing HTTP clients in " +
                "Spring Boot app with wiremock");
        assertThat(post.getUserId()).isEqualTo(1);
    }

}

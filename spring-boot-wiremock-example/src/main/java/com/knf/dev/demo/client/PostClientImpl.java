package com.knf.dev.demo.client;

import com.knf.dev.demo.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@AllArgsConstructor
public class PostClientImpl implements PostClient {

    private final RestClient restClient;

    @Override
    public Post findById(int id) {
        Post post = restClient.get()
                .uri("/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            // TODO
                        }
                )
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            //TODO
                        })
                .body(Post.class);

        return post;
    }
}

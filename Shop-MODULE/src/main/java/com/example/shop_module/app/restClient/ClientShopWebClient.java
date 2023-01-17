package com.example.shop_module.app.restClient;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ClientShopWebClient {

    private  final WebClient.Builder webBuilder;

    public ClientShopWebClient(WebClient.Builder webBuilder) {
        this.webBuilder = webBuilder;
    }

    public Mono<ResponseEntity<UserDTO>> performRequest(String url) {
        var client = webBuilder.baseUrl(url).build();

        try{
            return client.get()
                    .accept(MediaType.APPLICATION_JSON)
                    .header("MSS", "message")
                    .exchange()
                    .flatMap(res ->
                            res.bodyToMono(UserDTO.class).map(rs -> {
                                log.info(res.toString());
                        return new ResponseEntity<UserDTO>(rs, res.statusCode());
                    }));
        }catch (Exception e) {
            throw new NoConnectedToGRpsServer();
        }
    }

}

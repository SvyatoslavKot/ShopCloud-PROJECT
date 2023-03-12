package com.example.shop_module.app.restClient;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.restClient.abstraction.WebClientAbstractRequester;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class WebClientRequester extends WebClientAbstractRequester {

    private final String REQUEST_HOST;
    private final String REQUEST_PORT;
    private final String API_VERSION;

    public WebClientRequester(WebClient.Builder webBuilder, String request_host, String request_port, String api_version) {
        super(webBuilder);
        REQUEST_HOST = request_host;
        REQUEST_PORT = request_port;
        API_VERSION = api_version;
    }

    public Mono<ResponseEntity> performRequest(String url, Class<?> responseType) {
        var client = webBuilder.baseUrl(REQUEST_HOST + REQUEST_PORT + API_VERSION + url).build();

        try{
            return client.get()
                    .accept(MediaType.APPLICATION_JSON)
                    //.header("MSS", "message")
                    .exchange()
                    .flatMap(res ->
                            res.bodyToMono(responseType).map(rs -> {
                                log.info(res.toString());
                                return new ResponseEntity(rs, res.statusCode());
                            }));
        }catch (Exception e) {
            throw new NoConnectedToRestService();
        }
    }

}

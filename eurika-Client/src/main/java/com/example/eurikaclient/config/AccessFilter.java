package com.example.eurikaclient.config;

import com.example.eurikaclient.jwtBeans.UserResponse;
import com.example.eurikaclient.provider.CurrentUserProvider;
import com.example.eurikaclient.provider.JwtSettingsProvider;
import com.example.eurikaclient.provider.JwtTokenProvider;
import com.example.eurikaclient.provider.JwtTokenProviderService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Order(1)
@Component
@Slf4j
//@AllArgsConstructor
public class AccessFilter implements Filter
{

    private final JwtSettingsProvider jwtSettingsProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate;
    private final CurrentUserProvider currentUserProvider;
    private final JwtTokenProviderService jwtTokenProviderService;
    private String secretKey = "secretKey";

    public AccessFilter(JwtSettingsProvider jwtSettingsProvider, CurrentUserProvider currentUserProvider,
                        RestTemplate restTemplate, JwtTokenProvider jwtTokenProvider, JwtTokenProviderService jwtTokenProviderService) {
        this.jwtSettingsProvider = jwtSettingsProvider;
        this.currentUserProvider = currentUserProvider;
        this.restTemplate = restTemplate;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenProviderService = jwtTokenProviderService;

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (token != null && !token.isEmpty()) {
            if (jwtTokenProvider.validateToken(token)) {
                    try {
               //уменьшить количество обращений,добавить проверку на null (при logout добавить ранзакционность)
                        currentUserProvider.set(httpServletRequest, jwtTokenProviderService.getCurrentUserFromToken(token));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                        currentUserProvider.clearRequestAttribute(httpServletRequest);
                        filterChain.doFilter(httpServletRequest, response);
                    }
                }
            }
        filterChain.doFilter(httpServletRequest, response);
    }

    private UserResponse fetchRemouteUser(HttpServletRequest httpServletRequest) {
        System.out.println("filter ");
        /*
        URL url = new URL("http://localhost:8082/api/v1/auth/current");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(2000);
        connection.setReadTimeout(1000);
        connection.setRequestProperty("Cookie", httpServletRequest.getHeader("Cookie"));
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

         */
        UserResponse userResponse = null;

        try{
            String url = "http://localhost:8082/api/v1/auth/current";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject personJsonObject = new JSONObject();
            ResponseEntity<UserResponse> responseEntity  = restTemplate.getForEntity(url, UserResponse.class);
            userResponse = responseEntity.getBody();
        }catch (Exception e){
            log.error(e.getMessage());
             userResponse = new UserResponse();
        }

        System.out.println("filter " + userResponse);
        return userResponse;
        //gson.fromJson(content.toString(), UserResponse.class);
        //System.out.println("filter null");
        //return new UserResponse();
    }
}

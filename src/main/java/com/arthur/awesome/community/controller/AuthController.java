package com.arthur.awesome.community.controller;

import com.arthur.awesome.community.dto.AccessTokenDTO;
import com.arthur.awesome.community.dto.GitHubUserDTO;
import com.arthur.awesome.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String ClientSecret;

    @Value("${github.client.redirect.url}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        final AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(ClientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        final String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);

        final GitHubUserDTO userInfo = githubProvider.getUserInfo(accessToken);
        System.out.println(userInfo.getName());

        return "index";
    }
}

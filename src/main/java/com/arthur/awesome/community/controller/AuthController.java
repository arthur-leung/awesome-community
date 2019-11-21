package com.arthur.awesome.community.controller;

import com.arthur.awesome.community.dto.AccessTokenDTO;
import com.arthur.awesome.community.dto.GitHubUserDTO;
import com.arthur.awesome.community.mapper.UserMapper;
import com.arthur.awesome.community.model.User;
import com.arthur.awesome.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

    @Autowired
    UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest req) {
        final AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(ClientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        final String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        final GitHubUserDTO githubUser = githubProvider.getUserInfo(accessToken);
        if (githubUser != null) {
            final User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            // 登录成功 写cookie session
            req.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        } else {
            return "redirect:/ ";
        }
    }
}

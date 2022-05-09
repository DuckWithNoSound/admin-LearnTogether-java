package Admin.LearnTogether.API;

import Admin.LearnTogether.DTO.JwtResponse;
import Admin.LearnTogether.DTO.LoginDTO;
import Admin.LearnTogether.DTO.UserDTO;
import Admin.LearnTogether.DTO.UserDetail;
import Admin.LearnTogether.IService.IUserService;
import Admin.LearnTogether.Service.JwtAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/*
  Created by Luvbert
*/
@RestController
public class AuthAPI {

    private AuthenticationManager authenticationManager;

    private JwtAuthService jwtAuthService;

    private IUserService userService;

    public AuthAPI(AuthenticationManager authenticationManager, JwtAuthService jwtAuthService, IUserService userService){
        this.authenticationManager = authenticationManager;
        this.jwtAuthService = jwtAuthService;
        this.userService = userService;
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity registerAPI(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.NOT_ACCEPTABLE);
        }
        Map<String, String> registered = userService.registerNewUserAccount(userDTO);
        //Check service validate
        if(!registered.isEmpty()){
            registered.put("oldUsername", userDTO.getUsername());
            registered.put("oldEmail", userDTO.getEmail());
            return ResponseEntity.ok(registered);
        }
        return ResponseEntity.ok(userService.findUserByUsername(userDTO.getUsername()));
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> loginAPI(@RequestBody @Valid LoginDTO loginDTO, HttpServletResponse response){
        Map<String, String> message = new HashMap<>();
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            long hasAccessRole = authentication.getAuthorities().stream().filter(temp -> temp.getAuthority().equalsIgnoreCase("root") || temp.getAuthority().equalsIgnoreCase("admin") || temp.getAuthority().equalsIgnoreCase("mod")).count();

            if(hasAccessRole <= 0){
                message.put("Error", "You do not have permission to access!");
                return ResponseEntity.ok(message);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetail user = (UserDetail) authentication.getPrincipal();

            String accessToken = jwtAuthService.generateToken(user);
            String refreshToken = "";


            String accessTokenW = jwtAuthService.generateTokenWithoutPrefix(user);
            Cookie cookie = new Cookie("AuthenticationCookie", accessTokenW);
            cookie.setMaxAge(2*60);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);


            response.setHeader("Authorization", accessToken);

            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken, user.getUsername(), user.getFullname(), user.getAvatar(), user.getUserQuote(), user.getRole().getRoleName()));

        } catch (BadCredentialsException badCredentialsException){
            message.put("Error", "Login failed! Username or password is incorrect.");
            return ResponseEntity.ok(message);
        }
    }

    @GetMapping("/api/auth/logout")
    public ResponseEntity<Void> logoutApi(HttpServletResponse response){

        SecurityContextHolder.clearContext();

        Cookie authCookie = new Cookie("AuthenticationCookie", "");
        authCookie.setMaxAge(0);
        response.addCookie(authCookie);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/auth/random")
    public ResponseEntity randomStuff(){
        return new ResponseEntity("You are here, this page only access when authenticated.", HttpStatus.OK);
    }
}

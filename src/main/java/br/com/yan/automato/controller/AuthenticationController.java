package br.com.yan.automato.controller;

import br.com.yan.automato.dto.AuthenticationDTO;
import br.com.yan.automato.dto.CreateUserDTO;
import br.com.yan.automato.dto.LoginResponseDTO;
import br.com.yan.automato.enums.UserRole;
import br.com.yan.automato.infra.security.TokenService;
import br.com.yan.automato.model.User;
import br.com.yan.automato.repository.UserRepository;
import br.com.yan.automato.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity create(@RequestBody CreateUserDTO data){
        if(this.userRepository.findByEmail(data.email()) != null){
            return ResponseEntity.badRequest().build();
        }
        String encryptedEncode = new BCryptPasswordEncoder().encode(data.senha());
        User user = new User(data.nome(), data.email(), encryptedEncode, data.instituicao(), UserRole.USER);
        userService.create(user);
        return ResponseEntity.ok().build();
    }
}

package  com.fafunda.authms.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fafunda.authms.domain.Authority;
import com.fafunda.authms.domain.User;
import com.fafunda.authms.repository.AuthorityRepository;
import com.fafunda.authms.repository.UserRepository;
import com.fafunda.authms.security.jwt.auth.controller.AuthRequest;
import com.fafunda.authms.security.jwt.auth.controller.AuthResponse;

import io.jsonwebtoken.lang.Objects;

@RestController
@CrossOrigin
public class SignupFormController {

	@Autowired
	private  AuthorityRepository authorityRepository;
	@Autowired
    private  UserRepository userRepository;
	@Autowired
    private  PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/v1/signup", method = RequestMethod.POST)
	public ResponseEntity<?> submitForm(@Valid @RequestBody SignUpRequest reqBody) {
		Authority role = authorityRepository.findOneByRole("USER");
		if(StringUtils.hasText(reqBody.getPassword()))
		{
		 	User user=userRepository.findOneByUsername(reqBody.getUsername());
		 	if(user==null)
			userRepository.save(User.builder()
	                .username(reqBody.getUsername())
	                .password(passwordEncoder.encode(reqBody.getPassword()))
	                .authority(role)
	                .oidcProvider(reqBody.getOidcProvider())
	                .build());
		}
		return ResponseEntity.ok("{\"message\":\"User has been registered sucessfully\"}");
	}
}

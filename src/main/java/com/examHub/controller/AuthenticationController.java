package com.examHub.controller;

import com.examHub.ExamHub.dto.examRequestDto.PasswordChangeRequest;
import com.examHub.ExamHub.dto.examRequestDto.JwtRequest;
import com.examHub.ExamHub.model.JwtResponse;
import com.examHub.helpers.JwtUtil;
import com.examHub.entity.User;
import com.examHub.model.Message;
import com.examHub.service.CustomUserDetailsService;
import com.examHub.service.PasswordService;
import com.examHub.service.UserService;
import com.examHub.utils.constants.MessageConstant;
import com.examHub.utils.constants.RequestMappingConstants;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(RequestMappingConstants.LOGINAPIVERSION)
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PasswordService passwordService;

    @RequestMapping(value = RequestMappingConstants.GETTOKEN, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        User user = userService.findByUsername(jwtRequest.getUsername());

        if (StringUtils.isEmpty(jwtRequest.getUsername()) && StringUtils.isEmpty(jwtRequest.getPassword())) {
            return new ResponseEntity<Message>(new Message(HttpStatus.UNAUTHORIZED, MessageConstant.EMAILANDPASSWORDNOTPASSED, MessageConstant.BAD_CREDENTIALS), HttpStatus.UNAUTHORIZED);
        } else if (user == null) {
            return new ResponseEntity<Message>(new Message(HttpStatus.UNAUTHORIZED, MessageConstant.INCORRECT_EMAIL, MessageConstant.BAD_CREDENTIALS), HttpStatus.UNAUTHORIZED);
        } else if (!bCryptPasswordEncoder.matches(jwtRequest.getPassword(), user.getPassword())) {
            return new ResponseEntity<Message>(new Message(HttpStatus.UNAUTHORIZED, MessageConstant.INCORRECT_PASSWORD, MessageConstant.BAD_CREDENTIALS), HttpStatus.UNAUTHORIZED);
        } else {
            if (!user.isStatus()) {
                return new ResponseEntity<Message>(new Message(HttpStatus.UNAUTHORIZED, MessageConstant.ACCOUNT_DEACTIVATED, MessageConstant.BAD_CREDENTIALS), HttpStatus.UNAUTHORIZED);
            }
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("Bad credentials");
        }

        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        String token = this.jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(userDetails, token));
    }

    @RequestMapping(value = RequestMappingConstants.FORGETPASSWORD, method = RequestMethod.GET)
    public ResponseEntity<Message> forgotPassword(@RequestParam String email) throws Exception {
        return passwordService.resetForgetPasswordAndMailToCorrespondingUser(email);
    }

    @RequestMapping(value = RequestMappingConstants.USERCHANGEPASSWORD, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Message> changeUserPassword(@RequestBody PasswordChangeRequest passwordChangeRequest) throws Exception {
        return passwordService.changeUserPassword(passwordChangeRequest);
    }
}

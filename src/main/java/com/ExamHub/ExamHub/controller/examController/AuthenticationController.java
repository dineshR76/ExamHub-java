package com.ExamHub.ExamHub.controller.examController;

import com.ExamHub.ExamHub.dto.examRequestDto.PasswordChangeRequest;
import com.ExamHub.ExamHub.entity.examEntity.User;
import com.ExamHub.ExamHub.dto.examRequestDto.JwtRequest;
import com.ExamHub.ExamHub.model.JwtResponse;
import com.ExamHub.ExamHub.model.Message;
import com.ExamHub.ExamHub.service.examService.CustomUserDetailsService;
import com.ExamHub.ExamHub.service.examService.PasswordService;
import com.ExamHub.ExamHub.service.examService.UserService;
import com.ExamHub.ExamHub.utils.constants.MessageConstant;
import com.ExamHub.ExamHub.utils.constants.RequestMappingConstants;
import com.ExamHub.ExamHub.utils.helpers.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
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
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Authentication Controller",
        description = "REST APIs - generate token, forgot password, change password"
)
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

    @Operation(
            summary = "Generate token REST API",
            description = "Generate token API is used to generate the JWT token by validating the username and password by fetching it from the database"
    )
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

    @Operation(
            summary = "Forgot password REST API",
            description = "forgot password API generates the random 7 char alphanumeric password and sends the e-mail to the user"
    )
    @RequestMapping(value = RequestMappingConstants.FORGETPASSWORD, method = RequestMethod.GET)
    public ResponseEntity<Message> forgotPassword(@RequestParam String email) throws Exception {
        return passwordService.resetForgetPasswordAndMailToCorrespondingUser(email);
    }

    @Operation(
            summary = "Change password REST API",
            description = "Change password API changes the current password of the user"
    )
    @RequestMapping(value = RequestMappingConstants.USERCHANGEPASSWORD, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Message> changeUserPassword(@RequestBody PasswordChangeRequest passwordChangeRequest) throws Exception {
        return passwordService.changeUserPassword(passwordChangeRequest);
    }
}

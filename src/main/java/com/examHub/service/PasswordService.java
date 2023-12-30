package com.examHub.service;

import com.examHub.ExamHub.dto.examRequestDto.MailRequest;
import com.examHub.ExamHub.dto.examRequestDto.PasswordChangeRequest;

import com.examHub.helpers.MailUtils;
import com.examHub.helpers.PasswordUtils;
import com.examHub.helpers.SecurityUtils;
import com.examHub.entity.User;
import com.examHub.model.Message;
import com.examHub.utils.constants.CommonConstants;
import com.examHub.utils.constants.MessageConstant;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
@AllArgsConstructor
@ToString
public class PasswordService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private MailUtils mailUtils;


    public ResponseEntity<Message> resetForgetPasswordAndMailToCorrespondingUser(String email) throws Exception {
        if (StringUtils.isEmpty(email)) {
            return new ResponseEntity<Message>(
                    new Message(HttpStatus.NOT_ACCEPTABLE, MessageConstant.EMAIL_NOT_PASSED, CommonConstants.EXCEPTION),
                    HttpStatus.NOT_ACCEPTABLE);
        }
        User user = customUserDetailsService.findByUsername(email);
        if (user == null) {
            return new ResponseEntity<Message>(new Message(HttpStatus.NOT_FOUND, MessageConstant.INCORRECT_EMAIL, CommonConstants.EXCEPTION), HttpStatus.NOT_FOUND);
        } else if (!user.isStatus()) {
            return new ResponseEntity<Message>(new Message(HttpStatus.UNAUTHORIZED,
                    MessageConstant.ACCOUNT_DEACTIVATED, MessageConstant.BAD_CREDENTIALS), HttpStatus.UNAUTHORIZED);
        }
        String newPassword = PasswordUtils.generateSecurePassword();
        try {
            Context context = new Context();
            context.setVariable("password", newPassword);
            try {

//                TODO: use this below commented code for html mail sending we need to implement it later
//                String[] nameParts = user.getUsername().split("[.@]");
//                String firstName = nameParts[0].substring(0, 1).toUpperCase() + nameParts[0].substring(1);
//                context.setVariable("name", firstName);
//                mailUtils.sendEmail(new MailRequest(email,"Reset password","index", context));

                mailUtils.sendEmail(new MailRequest(email, "Reset password", "new password is: " + newPassword));
                updateUserPassword(user, newPassword);
            } catch (MessagingException e) {
                System.out.println("error in sendForgotPasswordEmail" + e);
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return new ResponseEntity<Message>(new Message(HttpStatus.BAD_GATEWAY, MessageConstant.WENT_WRONG, CommonConstants.EXCEPTION), HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<Message>(new Message(HttpStatus.OK, MessageConstant.RESET_PASSWORD, CommonConstants.SUCCESS), HttpStatus.OK);

    }

    public ResponseEntity<Message> changeUserPassword(PasswordChangeRequest passwordChangeRequest) throws Exception {
        if (passwordChangeRequest == null || passwordChangeRequest.getOldPassword().isEmpty() || passwordChangeRequest.getPassword().isEmpty()) {
            return new ResponseEntity<Message>(new Message(HttpStatus.NOT_ACCEPTABLE, MessageConstant.PASSWORD_NOT_PASSED, CommonConstants.EXCEPTION), HttpStatus.NOT_ACCEPTABLE);
        }
        if (!passwordChangeRequest.getPassword().equalsIgnoreCase(passwordChangeRequest.getConfirmPassword())) {
            return new ResponseEntity<Message>(new Message(HttpStatus.NOT_ACCEPTABLE, MessageConstant.PASSWORD_MISMATCH, CommonConstants.EXCEPTION), HttpStatus.NOT_ACCEPTABLE);
        }
        User user = this.getCurrentLoginUser();
        if (bCryptPasswordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword())) {
            updateUserPassword(user, passwordChangeRequest.getPassword());
            return new ResponseEntity<Message>(new Message(HttpStatus.OK, MessageConstant.CHANGE_PASSWORD, CommonConstants.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<Message>(new Message(HttpStatus.NOT_ACCEPTABLE, MessageConstant.OLD_PASSWORD_MISMATCH, CommonConstants.EXCEPTION), HttpStatus.NOT_ACCEPTABLE);
    }

    private void updateUserPassword(User user, String password) throws Exception {
        user.setPassword(bCryptPasswordEncoder.encode(password));
        customUserDetailsService.saveOrUpdate(user);
    }

    public User getCurrentLoginUser() throws Exception {
        Optional<User> userOpt = SecurityUtils.getCurrentLoginUser()
                .flatMap(customUserDetailsService::findOneByEmail);

        if (userOpt.isEmpty())
            throw new Exception(MessageConstant.USER_NOT_FOUND);
        return userOpt.get();
    }
}

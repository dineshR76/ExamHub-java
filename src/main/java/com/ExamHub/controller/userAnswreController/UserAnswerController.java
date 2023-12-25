package com.ExamHub.controller.userAnswreController;

import com.ExamHub.dto.userAnswerRequestDto.UserAnswerRequestDto;
import com.ExamHub.entity.examEntity.UserAnswer;
import com.ExamHub.model.Message;
import com.ExamHub.service.userAnswerService.UserAnswerService;
import com.ExamHub.utils.constants.CommonConstants;
import com.ExamHub.utils.constants.MessageConstant;
import com.ExamHub.utils.constants.RequestMappingConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping(RequestMappingConstants.USER_ANSWER_APIVERSION)
public class UserAnswerController {

    @Autowired
    private UserAnswerService userAnswerService;


    //  @ApiOperation(value = "submit user answer")
    @RequestMapping(value = RequestMappingConstants.SAVE_USER_ANSWER , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Message> saveUserAnswer(@RequestBody UserAnswerRequestDto userAnswerRequestDto) throws Exception {

        try {
          UserAnswer saveUserAnswer = userAnswerService.saveUserAnswer(userAnswerRequestDto);
            if (saveUserAnswer != null) {
                return new ResponseEntity<Message>(new Message(HttpStatus.OK, MessageConstant.USER_ANSWER_SAVED, CommonConstants.SUCCESS), HttpStatus.OK);
            } else {
                return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, CommonConstants.EXCEPTION, CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
        }
    }


}

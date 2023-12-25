package com.ExamHum.ExamHum.controller.examController;

import com.ExamHum.ExamHum.dto.examRequestDto.ExamRequestDto;
import com.ExamHum.ExamHum.entity.examEntity.Exam;
import com.ExamHum.ExamHum.service.examService.ExamService;
import com.ExamHum.ExamHum.model.Message;
import com.ExamHum.ExamHum.utils.constants.CommonConstants;
import com.ExamHum.ExamHum.utils.constants.MessageConstant;
import com.ExamHum.ExamHum.utils.constants.RequestMappingConstants;
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
@RequestMapping(RequestMappingConstants.EXAMAPIVERSION)
public class ExamController {

    @Autowired
    private ExamService examService;


  //  @ApiOperation(value = "Creating a New  exam")
    @RequestMapping(value = RequestMappingConstants.SAVE_EXAM , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Message> saveExam(@RequestBody ExamRequestDto examRequestDto) throws Exception {

        try {
            Exam savedExam = examService.createExam(examRequestDto);
            if (savedExam != null) {
                return new ResponseEntity<Message>(new Message(HttpStatus.OK, MessageConstant.EXAM_SAVED, CommonConstants.SUCCESS), HttpStatus.OK);
            } else {
                return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, CommonConstants.EXCEPTION, CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
        }
    }

}

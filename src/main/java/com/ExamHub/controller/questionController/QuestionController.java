package com.ExamHub.controller.questionController;

import com.ExamHub.dto.questionRequestDto.QuestionRequestDto;
import com.ExamHub.entity.examEntity.Question;
import com.ExamHub.model.Message;
import com.ExamHub.model.ResultList;
import com.ExamHub.service.questionService.QuestionService;
import com.ExamHub.utils.constants.CommonConstants;
import com.ExamHub.utils.constants.MessageConstant;
import com.ExamHub.utils.constants.RequestMappingConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;


@RestController
@AllArgsConstructor
@RequestMapping(RequestMappingConstants.QUESTIONAPIVERSION)
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    //  @ApiOperation(value = "Creating a New  question")
    @RequestMapping(value = RequestMappingConstants.SAVE_QUESTION , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Message> saveQuestion(@RequestBody QuestionRequestDto questionRequestDto) throws Exception {

        try {
            Question savedQuestion = questionService.createQuestion(questionRequestDto);
            if (savedQuestion != null) {
                return new ResponseEntity<Message>(new Message(HttpStatus.OK, MessageConstant.QUESTION_SAVED, CommonConstants.SUCCESS), HttpStatus.OK);
            } else {
                return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, CommonConstants.EXCEPTION, CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
        }
    }


    // @ApiOperation(value = "update Question")
    @RequestMapping(value = RequestMappingConstants.UPDATE_QUESTION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<Message> updateQuestion(@RequestBody QuestionRequestDto questionRequestDto) throws Exception {

        try {

            Question savedQuestion = questionService.updateQuestion(questionRequestDto);
            if (savedQuestion != null) {
                return new ResponseEntity<Message>(new Message(HttpStatus.OK, MessageConstant.QUESTION_UPDATE, CommonConstants.SUCCESS), HttpStatus.OK);
            } else {
                return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, CommonConstants.EXCEPTION, CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
        }
    }

    //  @ApiOperation(value = "get all question")
    @RequestMapping(value = RequestMappingConstants.GET_ALL_QUESTION, params = {"page", "size"}, method = RequestMethod.GET)
    public ResponseEntity<?> getAllQuestion(@RequestParam("page") int page, @RequestParam("size") int size) throws InstanceNotFoundException {

        if (size == 0) {
            throw new IncorrectResultSizeDataAccessException("Expected PageSize not Found.", 10, size);
        }
        Page<Question> result = questionService.getAllQuestion(page, size);
        if (CollectionUtils.isEmpty(result.getContent())) {
            return new ResponseEntity<>(new ResultList<>(result.getContent(), new Message(HttpStatus.OK, MessageConstant.NOT_FOUND, CommonConstants.SUCCESS)), HttpStatus.OK);
        }
        return ResponseEntity.ok(new ResultList<>(result.getContent(), result.getPageable(), result.getTotalPages(), result.getTotalElements(), new Message(HttpStatus.OK, CommonConstants.SUCCESS, CommonConstants.SUCCESS)));
    }

    // @ApiOperation(value = "get question by id")
    @RequestMapping(value = RequestMappingConstants.GET_QUESTION_BY_ID, params = {"questionId"}, method = RequestMethod.GET)
    public ResponseEntity<?> getQuestionById(@RequestParam("questionId") int questionId)  {
        try{
            Question question = questionService.getQuestionById(questionId);
            return ResponseEntity.ok(question);
        }
        catch (Exception e){
            return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
        }
    }

    // @ApiOperation(value = "delete question by id")
    @RequestMapping(value = RequestMappingConstants.DELETE_QUESTION, params = {"questionId"}, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> deleteQuestion(@RequestParam("questionId") int questionId)  {

        try{
            questionService.deleteQuestion(questionId);
            return new ResponseEntity<Message>(new Message(HttpStatus.OK, MessageConstant.QUESTION_DELETED, CommonConstants.SUCCESS), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
        }
    }

}


package com.ExamHub.ExamHub.controller.examController;

import com.ExamHub.ExamHub.model.Message;
import com.ExamHub.ExamHub.dto.examRequestDto.ExamRequestDto;
import com.ExamHub.ExamHub.entity.examEntity.Exam;
import com.ExamHub.ExamHub.model.ResultList;
import com.ExamHub.ExamHub.service.examService.ExamService;
import com.ExamHub.ExamHub.utils.constants.CommonConstants;
import com.ExamHub.ExamHub.utils.constants.MessageConstant;
import com.ExamHub.ExamHub.utils.constants.RequestMappingConstants;
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


   // @ApiOperation(value = "update Exam")
    @RequestMapping(value = RequestMappingConstants.UPDATEEXAM, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Message> updateExam(@RequestBody ExamRequestDto examRequestDto) throws Exception {

        try {

            Exam savedExam = examService.updateExam(examRequestDto);
            if (savedExam != null) {
                return new ResponseEntity<Message>(new Message(HttpStatus.OK, MessageConstant.EXAM_UPDATE, CommonConstants.SUCCESS), HttpStatus.OK);
            } else {
                return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, CommonConstants.EXCEPTION, CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
        }
    }

  //  @ApiOperation(value = "get all Exam")
    @RequestMapping(value = RequestMappingConstants.GETALLEXAM, params = {"page", "size"}, method = RequestMethod.GET)
    public ResponseEntity<?> getAllExam(@RequestParam("page") int page, @RequestParam("size") int size) throws InstanceNotFoundException {

        if (size == 0) {
            throw new IncorrectResultSizeDataAccessException("Expected PageSize not Found.", 10, size);
        }
        Page<Exam> result = examService.getAllExam(page, size);
        if (CollectionUtils.isEmpty(result.getContent())) {
            return new ResponseEntity<>(new ResultList<Exam>(result.getContent(), new Message(HttpStatus.OK, MessageConstant.NOT_FOUND, CommonConstants.SUCCESS)), HttpStatus.OK);
        }
        return ResponseEntity.ok(new ResultList<Exam>(result.getContent(), result.getPageable(), result.getTotalPages(), result.getTotalElements(), new Message(HttpStatus.OK, CommonConstants.SUCCESS, CommonConstants.SUCCESS)));
    }

   // @ApiOperation(value = "get Exam by id")
    @RequestMapping(value = RequestMappingConstants.GETEXAMBYID, params = {"examId"}, method = RequestMethod.GET)
    public ResponseEntity<?> getExamById(@RequestParam("examId") int examId)  {
        try{
            Exam exam = examService.getExamById(examId);
            return ResponseEntity.ok(exam);
        }
        catch (Exception e){
            return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
        }
    }

    // @ApiOperation(value = "delete Exam by id")
    @RequestMapping(value = RequestMappingConstants.DELETEEXAM, params = {"examId"}, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> deleteExam(@RequestParam("examId") int examId)  {

        try{
            examService.deleteExam(examId);
            return new ResponseEntity<Message>(new Message(HttpStatus.OK, MessageConstant.EXAM_DELETED, CommonConstants.SUCCESS), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Message>(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), CommonConstants.EXCEPTION), HttpStatus.BAD_REQUEST);
        }
    }

}

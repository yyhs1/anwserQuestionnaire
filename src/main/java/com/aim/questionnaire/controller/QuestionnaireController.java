package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import com.aim.questionnaire.dao.entity.QuestionnaireEntity;
import com.aim.questionnaire.service.QuestionnaireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class QuestionnaireController {

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private QuestionnaireService questionnaireService;

    @RequestMapping(value = "/addQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addQuestionnaire(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int ret = questionnaireService.addQuestionnaire(questionnaireEntity);
            if (ret > 0) {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
            } else {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
            }
        } catch (Exception e) {
            logger.error("add questionnaire error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }


    @RequestMapping(value = "/queryQuestionnaireByProjectId", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryQuestionnaireByProjectId(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            List<Map<String, Object>> list = questionnaireService.queryQuestionnaireByProjectId(questionnaireEntity.getProjectId());
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            httpResponseEntity.setData(list);
        } catch (Exception e) {
            logger.error("query questionnaire error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }


}

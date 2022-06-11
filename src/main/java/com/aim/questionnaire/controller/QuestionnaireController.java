package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import com.aim.questionnaire.dao.entity.QuestionnaireEntity;
import com.aim.questionnaire.service.QuestionnaireService;
import com.sun.corba.se.spi.ior.ObjectKey;
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

    /**
     * 删除问卷信息
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/deleteQuestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteQuestionnaireInfo(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            questionnaireService.deleteQuestionnaireInfo(questionnaireEntity);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
        } catch (Exception e) {
            logger.error("delete questionnaire error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 修改问卷信息
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/modifyQuestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyQuestionnaireInfo(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            questionnaireService.modifyQuestionnaireInfo(questionnaireEntity);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
        } catch (Exception e) {
            logger.error("modify questionnaire error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 根据id查找问卷
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/queryQuestionnaireById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryQuestionnaireById(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            Map<String, Object> map = questionnaireService.queryQuestionnaireById(questionnaireEntity.getId());
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            httpResponseEntity.setData(map);
        } catch (Exception e) {
            logger.error("query questionnaire error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 添加问卷信息
     * @param questionnaireEntity
     * @return
     */
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

    /**
     * 根据项目id查询所有关联的问卷
     * @param questionnaireEntity
     * @return
     */
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

package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.QuestionnaireEntityMapper;
import com.aim.questionnaire.dao.entity.QuestionnaireEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;


    public int addQuestionnaire(QuestionnaireEntity questionnaireEntity) {
        questionnaireEntity.setId(UUIDUtil.getOneUUID());
        return questionnaireEntityMapper.insertSelective(questionnaireEntity);
    }

    public List<Map<String, Object>> queryQuestionnaireByProjectId(String projectId) {
        return questionnaireEntityMapper.selectQuestionnaireByProjectId(projectId);
    }



}

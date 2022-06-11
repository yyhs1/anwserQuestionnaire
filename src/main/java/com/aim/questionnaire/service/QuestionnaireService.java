package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.QuestionnaireEntityMapper;
import com.aim.questionnaire.dao.entity.QuestionnaireEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;

    /**
     * 修改问卷信息
     * @param questionnaireEntity
     * @return
     */
    public int modifyQuestionnaireInfo(QuestionnaireEntity questionnaireEntity) {

        // 设置修改时间
        Date date = DateUtil.getCurrentTime();
        questionnaireEntity.setLastUpdateDate(date);
        return questionnaireEntityMapper.updateByPrimaryKeySelective(questionnaireEntity);
    }

    /**
     * 添加问卷信息
     * @param questionnaireEntity
     * @return
     */
    public int addQuestionnaire(QuestionnaireEntity questionnaireEntity) {

        // 设置id
        questionnaireEntity.setId(UUIDUtil.getOneUUID());

        // 设置创建时间和修改时间
        Date date = DateUtil.getCurrentTime();
        questionnaireEntity.setCreationDate(date);
        questionnaireEntity.setLastUpdateDate(date);

        return questionnaireEntityMapper.insertSelective(questionnaireEntity);
    }

    /**
     * 根据项目id查询所有关联的问卷
     * @param projectId
     * @return
     */
    public List<Map<String, Object>> queryQuestionnaireByProjectId(String projectId) {
        return questionnaireEntityMapper.selectQuestionnaireByProjectId(projectId);
    }

    /**
     * 根据问卷id查询问卷信息
     * @param questionnaireId
     * @return
     */
    public Map<String, Object> queryQuestionnaireById(String questionnaireId) {
        return questionnaireEntityMapper.selectQuestionnaireById(questionnaireId);
    }


}

package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.ProjectEntityMapper;
import com.aim.questionnaire.dao.QuestionnaireEntityMapper;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wln on 2018\8\6 0006.
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectEntityMapper projectEntityMapper;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;

    /**
     * 查询项目项目是否含有正在进行中的问卷
     * @param projectId
     * @return
     */
    public boolean isProjectHasOpenQuestionnaire(String projectId) {
        List<Map<String, Object>> list = questionnaireEntityMapper.selectQuestionnaireByProjectId(projectId);
        Date currentTime = new Date();
        for(Map<String, Object> map  : list) {
            Date endTime = (Date) map.get("endTime");
            Date startTime = (Date) map.get("startTime");
            if(startTime.compareTo(currentTime) <= 0 && endTime.compareTo(currentTime) >= 0) {
                return true;
            }
        }
        return false;
    }

    public int queryProjectCountByName(String name) {
        return projectEntityMapper.queryProjectCountByName(name);
    }


    /**
     * 添加项目
     *
     * @param projectEntity
     * @return
     */
    public int addProjectInfo(ProjectEntity projectEntity) {

        projectEntity.setId(UUIDUtil.getOneUUID());

        String userId = userEntityMapper.selectIdByName(projectEntity.getCreatedBy());
        projectEntity.setUserId(userId);

        //创建时间
        Date date = DateUtil.getCurrentTime();
        projectEntity.setCreationDate(date);
        projectEntity.setLastUpdateDate(date);

        return projectEntityMapper.insert(projectEntity);
    }

    /**
     * 修改项目
     *
     * @param projectEntity
     * @return
     */
    public int modifyProjectInfo(ProjectEntity projectEntity) {

        Map<String, Object> map = projectEntityMapper.queryProjectById(projectEntity);

        projectEntity.setId(map.get("id").toString());
        projectEntity.setUserId(map.get("userId").toString());

        //创建时间
        Date date = DateUtil.getCurrentTime();
        projectEntity.setLastUpdateDate(date);

        return projectEntityMapper.updateProjectById(projectEntity);
    }

    /**
     * 删除项目
     *
     * @param projectEntity
     * @return
     */
    public int deleteProjectById(ProjectEntity projectEntity) {
        questionnaireEntityMapper.deleteByProjectId(projectEntity.getId());
        return projectEntityMapper.deleteProjectById(projectEntity);
    }

    public Map<String, Object> queryProjectById(ProjectEntity projectEntity) {
        return projectEntityMapper.queryProjectById(projectEntity);
    }

    /**
     * 查询项目列表
     *
     * @param projectEntity
     * @return
     */
    public List<Map<String, Object>> queryAllProject(ProjectEntity projectEntity) {
        List<Map<String, Object>> projectEntityList = projectEntityMapper.queryAllProject(projectEntity);
        return projectEntityList;

    }

    /**
     * 查询全部项目的名字接口
     *
     * @return
     */
    public List<Map<String, Object>> queryAllProjectName() {
        return null;
    }
}

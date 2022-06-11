package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import com.aim.questionnaire.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wln on 2018\8\6 0006.
 */
@RestController
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    /**
     * 查询项目是否有正在开启的问卷
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/isProjectHasOpenQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity isProjectHasOpenQuestionnaire(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        Map<String, Object> map = new HashMap<>();
        try {
            boolean ret = projectService.isProjectHasOpenQuestionnaire(projectEntity.getId());
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            map.put("ret", ret);
            httpResponseEntity.setData(map);
        } catch (Exception e) {
            logger.error("query project error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 查询同名项目数量
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/queryProjectCountByName", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryProjectCountByName(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = projectService.queryProjectCountByName(projectEntity.getProjectName());
            if (ret > 0) {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
                map.put("ret", ret);
                httpResponseEntity.setData(map);
            } else {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
            }
        } catch (Exception e) {
            logger.error("query project error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }


    /**
     * 查询全部项目的信息
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/queryAllProject", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryAllProject(@RequestBody(required = false) ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            List<Map<String, Object>> projectList = projectService.queryAllProject(projectEntity);
            httpResponseEntity.setData(projectList);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
        } catch (Exception e) {
            logger.error("queryAllProject error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 根据id删除项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/deleteProjectById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteProjectById(@RequestBody ProjectEntity projectEntity) {
        logger.info(projectEntity.getId());
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int result = projectService.deleteProjectById(projectEntity);
            if (result > 0) {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            } else {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
            }
        } catch (Exception e) {
            logger.error("deleteProjectById error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 根据id查找项目信息
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/queryProjectById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryProjectById(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            Map<String, Object> project = projectService.queryProjectById(projectEntity);
            httpResponseEntity.setData(project);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
        } catch (Exception e) {
            logger.error("queryProjectById error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 添加项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/addProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int result = projectService.addProjectInfo(projectEntity);
            if (result > 0) {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            } else {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
            }
        } catch (Exception e) {
            logger.error("addProjectInfo error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 根据项目id修改项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/modifyProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int result = projectService.modifyProjectInfo(projectEntity);
            if (result > 0) {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            } else {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
            }
        } catch (Exception e) {
            logger.error("modifyProjectInfo error", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }


    /**
     * 查询全部项目的名字接口
     *
     * @return
     */
    @RequestMapping(value = "/queryAllProjectName", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryAllProjectName() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }
}

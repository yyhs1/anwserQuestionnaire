function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

$(function () {
    isLoginFun();
    // header();
    $("#ctl01_lblUserName").text(getCookie('userName'));
    var oTable = new relatedQuestionnaireTableInit();
    oTable.Init();
});

window.operateEvents = {
    //编辑
    'click #btn_count': function (e, value, row, index) {
        id = row.id;
        $.cookie('questionId', id);
    }
};

function addFunctionAlty(value, row, index) {
    // console.log(row);
    var btnText = '';

    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"editQuestionnaire(" + "'" + row.id + "')\" class=\"btn btn-default-g ajax-link\">编辑</button>&nbsp;&nbsp;";

    if (row.questionStop === "0") {
        btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"changeQuestionnaireStatus(" + "'" + row.id + "'" + ")\" class=\"btn btn-danger-g ajax-link\">关闭</button>&nbsp;&nbsp;";
    } else if (row.questionStop === "5"){
        btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"changeQuestionnaireStatus(" + "'" + row.id + "'" + ")\" class=\"btn btn-success-g ajax-link\">开启</button>&nbsp;&nbsp;"
    }
    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"questionnaireDetail(" + "'" + row.id + "')\" class=\"btn btn-default-g ajax-link\">详情</button>&nbsp;&nbsp;";

    btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"deleteQuestionnaire(" + "'" + row.id + "'" + ")\" class=\"btn btn-danger-g ajax-link\">删除</button>&nbsp;&nbsp;";

    return btnText;
}

function relatedQuestionnaireTableInit() {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#questionnaireTable').bootstrapTable({
            url: httpRequestUrl + '/queryQuestionnaireByProjectId',         //请求后台的URL（*）
            method: 'POST',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortOrder: "desc",                   //排序方式
            sortable: true,
            sortName: "creation_date",
            queryParamsType: '',
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            queryParams: queryParams,//请求服务器时所传的参数
            sidePagination: 'server',//指定服务器端分页
            pageSize: 10,//单页记录数
            pageList: [10, 20, 30, 40],//分页步进值
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            silent: true,
            showRefresh: false,                  //是否显示刷新按钮
            showToggle: false,
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列

            columns: [{
                checkbox: true,
                visible: false
            }, {
                field: 'id',
                title: '序号',
                align: 'center',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
                {
                    field: 'questionName',
                    title: '试卷名称',
                    align: 'center',
                    width: '230px'
                },
                {
                    field: 'releaseTime',
                    title: '发布时间',
                    align: 'center'
                },
                {
                    field: 'status',
                    title: '问卷状态',
                    align: 'center'
                },
                {
                    field: 'operation',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: addFunctionAlty//表格中增加按钮
                }],
            responseHandler: function (res) {
                // console.log(res);
                if (res.code == "666") {
                    var userInfo = res.data;
                    // console.log(userInfo);
                    // var userInfo=JSON.parse('[{"password":"1","startTime":"2022-05-12T10:09:28","id":"1","endTime":"2022-05-12T10:09:30","username":"aa","status":"1"},{"password":"123","startTime":"2022-05-12T12:10:37","id":"290e08f3ea154e33ad56a18171642db1","endTime":"2022-06-11T12:10:37","username":"aaa","status":"1"},{"password":"1","startTime":"2018-10-24T09:49:00","id":"8ceeee2995f3459ba1955f85245dc7a5","endTime":"2025-11-24T09:49:00","username":"admin","status":"1"},{"password":"aa","startTime":"2022-05-16T12:01:54","id":"a6f15c3be07f42e5965bec199f7ebbe6","endTime":"2022-06-15T12:01:54","username":"aaaaa","status":"1"}]');
                    var NewData = [];
                    if (userInfo.length) {
                        for (var i = 0; i < userInfo.length; i++) {
                            var dataNewObj = {
                                'id': '',
                                "questionName": '',
                                'startTime': ''
                            };
                            dataNewObj.id = userInfo[i].id;
                            dataNewObj.questionName = userInfo[i].questionName;
                            dataNewObj.releaseTime = userInfo[i].releaseTime;
                            dataNewObj.questionStop = userInfo[i].questionStop;
                            dataNewObj.status = getStatus(
                                userInfo[i].questionStop, userInfo[i].startTime,
                                userInfo[i].endTime, userInfo[i].releaseTime
                            );
                            NewData.push(dataNewObj);
                        }
                        //console.log(NewData)
                    }
                    var data = {
                        total: res.data.total,
                        rows: NewData
                    };

                    return data;
                }

            }

        });
    };

    // 得到查询的参数
    function queryParams(params) {
        // console.log(params);
        var userName = $("#keyWord").val();
        //console.log(userName);
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            // pageNum: params.pageNumber,
            // pageSize: params.pageSize,
            projectId: getQueryVariable('projectId'),
            // orderBy: params.sortName,
            // sort: params.sortOrder
        };
        return JSON.stringify(temp);
    }

    return oTableInit;
}

function formatDateToTimeStamp(str) {
    var date = str.split("T")[0];
    var time = str.split("T")[1].split(".")[0];

    //get timestamp
    return new Date(date + " " + time).getTime();
}

// 获取用于显示在表格中的问卷状态信息
function getStatus(status, startTime, endTime, releaseTime) {
    if(status === "5"){
        return "<p style='color: #e65c47'><strong>● 关闭中</strong></p>";
    }


    var currentTimeStamp = new Date().getTime();
    var startTimeStamp = formatDateToTimeStamp(startTime);
    var endTimeStamp = formatDateToTimeStamp(endTime);

    if(currentTimeStamp <= startTimeStamp){
        return "<p style='color: #e1e1e1'><strong>● 进行中（未开始）</strong></p>";
    } else if(currentTimeStamp >= endTimeStamp){
        return "<p style='color: #e1e1e1'><strong>● 进行中（已过期）</strong></p>";
    }

    if(releaseTime != null){
        return "<p style='color: #00AA00'><strong>● 进行中（已发布）</strong></p>";
    } else {
        return "<p style='color: #00AA00'><strong>● 进行中（未发布）</strong></p>";
    }

}


// 用于判断问卷是否可以被删除与修改
function isQuestionnaireOpenOrReleased(questionId) {

    let flag = false;

    commonAjaxPost(false, "/queryQuestionnaireById", {'id': questionId}, function (result) {
        if(result.code === "666") {
            var question = result.data;
            console.log(question)
            if (question.questionStop === "0") {
                alert("问卷已启动，请停止后再试！");
                flag = true;
            }
            if (question.releaseTime != null) {
                alert("问卷已发布，无法更改！");
                flag = true;
            }
            // if (question.startTime > new Date().getTime()) {
            //     alert("问卷未开始，无法更改！");
            //     return;
            // }
            // if (question.endTime < new Date().getTime()) {
            //     alert("问卷已过期，无法更改！");
            //     return;
            // }
        } else {
            alert(result.msg);
            flag = true;
        }

    });

    return flag;
}

// 编辑问卷信息
function editQuestionnaire(questionnaireId) {

    if(isQuestionnaireOpenOrReleased(questionnaireId)) {
        return;
    }

    // console.log(questionnaireId);

    commonAjaxPost(false, "/queryQuestionnaireById", {id: questionnaireId}, function (result) {
        if (result.code === "666") {
            var data = result.data;
            // console.log(data);
            setCookie('questionName', data.questionName);
            setCookie('questionContent', data.questionContent);
            setCookie('questionId', data.id);
            setCookie('endTime', data.endTime);
            setCookie('creationDate', data.creationDate);
            setCookie('dataId', data.dataId);
            setCookie('ifEditQuestType', "true");
            window.parent.open('editQuestionnaire.html?questionnaireId=' + questionnaireId);
        } else {
            layer.msg(result.msg);
        }
    });

}


// 删除问卷
function deleteQuestionnaire(questionnaireId) {
    if(isQuestionnaireOpenOrReleased(questionnaireId)) {
        return;
    }

    layer.confirm('您确认要删除此问卷吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var url = '/deleteQuestionnaireInfo';
        commonAjaxPost(true, url, {'id': questionnaireId}, function (result) {
            if (result.code === "666") {
                layer.msg(result.message, {icon: 1});
                $("#questionnaireTable").bootstrapTable('refresh');
            } else if (result.code === "333") {
                layer.msg(result.message, {icon: 2});
                setTimeout(function () {
                    window.location.href = 'login.html';
                }, 1000);
            } else {
                layer.msg(result.message, {icon: 2});
            }
        });
    }, function () {
    });

}


// 改变问卷的开启、关闭状态
function changeQuestionnaireStatus (questionnaireId) {

    var questionStop;

    commonAjaxPost(false, "/queryQuestionnaireById", {id: questionnaireId}, function (result) {
        if (result.code === "666") {
            var data = result.data;
            questionStop = data.questionStop;
        } else {
            layer.msg(result.msg);
        }
    });

    if (questionStop === "5") {
        questionStop = "0";
    } else {
        questionStop = "5";
    }
    commonAjaxPost(true, '/modifyQuestionnaireInfo', {'id':questionnaireId, 'questionStop':questionStop}, function (data) {
        if (data.code === "666") {
            $("#questionnaireTable").bootstrapTable('refresh');
        } else {
            alert(data.msg);
        }
    });

}

// 进入到发送、编辑、预览问卷界面
function questionnaireDetail (questionnaireId) {
    setCookie('questionnaireId', questionnaireId);
    window.parent.open('sendQuestionnaire.html');
}

<%@ page language="java" import="java.util.*,java.io.*,java.text.*" contentType="text/html;charset=utf8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<% pageContext.include("header.html");%>

    <!--中部主要内容容器-->
    <div id="content-container" class="row content-container-css">

        <!--中部左侧功能选择栏容器-->
        <div id="content-func-select" class="col-md-3 content-func-select-css">
            <div id="content-func-select-area" class="content-func-select-area-css">
                <h4>课程作业</h4>
                <hr />
                <p class="content-func-select-title-css" id="task-list-btn">> <a href="taskList.jsp">作业列表</a></p>

                <p class="content-func-select-cat">学生</p>

                <p class="content-func-select-title-css" id="tash-stu-btn">> <a href="stuCommentList.jsp">我提交的作业</a></p>

                <p class="content-func-select-cat">教师</p>

                <p class="content-func-select-title-css" id="task-add-btn">> <a href="addTask.jsp">添加作业</a></p>


            </div>
        </div>

        <!--中部右侧主要内容总容器-->
        <div id="content-main-area" class="col-md-9 content-main-area-css">
            <div id="content-area" class="content-area-css">
                <h4>作业列表</h4>
                <hr />
                <table class="table table-striped table-hover" id="task-list-table">
                    <tr>
                        <th>#</th>
                        <th>课程名称</th>
                        <th>作业主题</th>
                        <th>创建用户</th>
                        <th>创建时间</th>
                        <th>当前状态</th>
                        <th>执行操作</th>
                    </tr>
                    <tr><td>-1</td><td>Loading...正在加载作业...</td></tr>
                </table>
            </div>
        </div>
    </div>


<% pageContext.include("footer.html");%>

<script>

    function dofunc() {
        $("#func-task").css("background-color","#d8d8d8");
        $("#task-list-btn").css("background-color","#d8d8d8");
        showTaskList();
    }

    function showTaskList() {
        $.ajax({
            type:"GET",
            url:"showTaskList?t=" + Math.random(),
            dataType:"text",
            success:function(data) {
                $("table tr").eq(1).remove();
                $("#task-list-table").append(data);
            }
        });
    }

    function deleteTask(_taskId) {
        if(userType != 1) {
            alert("没有权限");
        } else {
            if(confirm("是否删除作业#" + _taskId + "（只能删除自己创建的作业）")) {
                $.ajax({
                    type:"post",
                    url:"deleteTask?t="+ Math.random(),
                    dataType:"json",
                    data:"taskid=" + _taskId,
                    success:function (json) {
                        if(json.errCode == 0) {
                            alert("作业删除成功");
                            window.location = "taskList.jsp";
                        } else {
                            alert(json.errCode + " 作业删除失败：" + json.errMsg);
                        }
                    }
                });
            }
        }
    }

    function setTaskStatus(_taskId) {
        if(userType != 1) {
            alert("没有权限");
        } else {
            if(confirm("是否更改作业#" + _taskId + "的状态（只能更改自己创建的作业）")) {
                $.ajax({
                    type:"post",
                    url:"setTaskStatus?t="+ Math.random(),
                    dataType:"json",
                    data:"taskid=" + _taskId,
                    success:function (json) {
                        if(json.errCode == 0) {
                            alert("作业状态更改成功");
                            window.location = "taskList.jsp";
                        } else {
                            alert(json.errCode + " 作业状态更改失败：" + json.errMsg);
                        }
                    }
                });
            }
        }
    }



</script>

</html>
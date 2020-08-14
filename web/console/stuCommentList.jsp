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

                <p class="content-func-select-title-css" id="task-stu-btn">> <a href="stuCommentList.jsp">我提交的作业</a></p>

                <p class="content-func-select-cat">教师</p>

                <p class="content-func-select-title-css" id="task-add-btn">> <a href="addTask.jsp">添加作业</a></p>


            </div>
        </div>

        <!--中部右侧主要内容总容器-->
        <div id="content-main-area" class="col-md-9 content-main-area-css">
            <div id="content-area" class="content-area-css">
                <h4>我提交的作业</h4>
                <hr />

                <table class="table table-striped table-hover" id="comment-list-table">
                    <tr>
                        <th>#</th>
                        <th>作业主题</th>
                        <th>作答内容</th>
                        <th>提交时间</th>
                        <th>教师评语</th>
                        <th>分数</th>
                        <th>执行操作</th>
                    </tr>
                    <tr><td>-1</td><td>Loading...正在加载作答...</td></tr>
                </table>
            </div>
        </div>
    </div>

<% pageContext.include("footer.html");%>

<script>

    function dofunc() {
        $("#func-task").css("background-color","#d8d8d8");
        $("#task-stu-btn").css("background-color","#d8d8d8");
        if(userType != 2) {
            alert("没有权限");
            window.location = "taskList.jsp";
        } else {
            showStuCommentList();
        }
    }

    function showStuCommentList() {
        $.ajax({
            type:"GET",
            url:"showStuCommentList?t=" + Math.random(),
            dataType:"text",
            success:function(data) {
                $("table tr").eq(1).remove();
                $("#comment-list-table").append(data);
            }
        });
    }


</script>

</html>
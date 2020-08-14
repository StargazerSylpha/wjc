<%@ page language="java" import="java.util.*,java.io.*,java.text.*" contentType="text/html;charset=utf8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<% pageContext.include("header.html");%>
<%
    String taskId = request.getParameter("taskId");
%>

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
                <h4>作业详情</h4>
                <hr />

                <p style="font-weight: bold" id="task-subject">#</p>
                <p class="notice-desc" id="task-desc"></p>
                <div id="task-content">
                </div>

                <hr />

                <ul class="nav nav-tabs">
                    <li id="nav-stu-btn" class="active"><a href="javascript:void(0)" onclick="showStuWriteArea()">学生作答</a></li>
                    <li id="nav-teacher-btn"><a href="javascript:void(0)" onclick="showTeacherCheckArea()">教师批改</a></li>
                </ul>

                <div id="task-stu-write-area">
                    <p class="notice-desc">仅学生可以作答</p>
                    <p><textarea class="form-control" name="commentcontent" id="commentcontent" minlength="4" placeholder="请输入作答..." required="required"></textarea></p>
                    <button type="button" class="btn btn-success" id="submitbtn" disabled>提 交</button>
                    <p style="color: red;display: none;" id="error-msg"></p>
                </div>

                <div id="task-teacher-check-area" style="display: none;">
                    <p class="notice-desc">只能批改自己课程的作业，不能批改其他不属于自己的课程的作业</p>
                    <table class="table table-striped table-hover" id="comment-list-table">
                        <tr>
                            <th>#</th>
                            <th>作答学生及学号</th>
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
    </div>

<% pageContext.include("footer.html");%>

<script>

    function dofunc() {
        $("#func-task").css("background-color","#d8d8d8");
        $("#task-list-btn").css("background-color","#d8d8d8");
        if(userType == 2) {
            $("#submitbtn").attr("disabled",false);
        } else {
            $("#submitbtn").attr("disabled",true).text("没有权限").removeClass("btn-success").addClass("btn-danger");
        }
        showTaskDetail();
    }

    function showTaskDetail() {
        $.ajax({
            type: "GET",
            url: "../console/showTaskDetail?t=" + Math.random() + "&taskId=" + <%=taskId%>,
            dataType: "json",
            success: function (json) {
                if (json.errCode == 0) {
                    var ts = "";
                    if(json.taskStatus == "1") {
                        ts = "可提交";
                    }
                    if(json.taskStatus == "0") {
                        $("#submitbtn").attr("disabled",true).text("已截止").removeClass("btn-success").addClass("btn-danger"); //方法链接
                        ts = "已截止";
                    }
                    $("#task-subject").append(json.taskId + " " + json.taskSubject);
                    $("#task-desc").append("【" + ts +"】课程：" + json.taskCourseName + "，" + json.taskUserName + "创建于" + json.taskCreate);
                    $("#task-content").append(json.taskContent);
                } else {
                    $("#task-content").append("作业查询失败");
                }
            }
        });
    }

    $(document).ready(function () {

        $("#submitbtn").click(function() {
            $("#error-msg").hide();
            $("#error-msg").empty();
            $("#submitbtn").attr("disabled",true);
            var commentContent = $("#commentcontent").val();

            $.ajax({
                type:"post",
                url:"editComment?t="+ Math.random(),
                dataType:"json",
                data:"commentcontent=" + commentContent + "&commenttask=" +<%=taskId%>,
                success:function (json) {
                    if(json.errCode == 0) {
                        alert("作答提交成功");
                        window.location = "taskList.jsp";
                    } else {
                        $("#error-msg").append(json.errCode + " 作答提交失败：" + json.errMsg);
                        $("#error-msg").show();
                        $("#submitbtn").attr("disabled",false);
                    }
                }
            });
        });

    });

    function showStuWriteArea() {
        $("#task-teacher-check-area").hide();
        $("#task-stu-write-area").show();
        $("#nav-stu-btn").addClass("active");
        $("#nav-teacher-btn").removeClass("active");
    }

    function showTeacherCheckArea() {

        if(userType !== 1) {
            alert("没有权限");
        } else {
            $("#task-teacher-check-area").show();
            $("#task-stu-write-area").hide();
            $("#nav-stu-btn").removeClass("active");
            $("#nav-teacher-btn").addClass("active");
            showTaskCommentList();
        }

    }

    function showTaskCommentList() {
        $.ajax({
            type:"POST",
            url:"showTaskCommentList?t=" + Math.random(),
            dataType:"text",
            data:"&taskid=" + <%=taskId%>,
            success:function(data) {
                $("table tr").eq(1).remove();
                $("#comment-list-table").append(data);
            }
        });
    }



</script>

</html>
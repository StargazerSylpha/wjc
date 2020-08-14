<%@ page language="java" import="java.util.*,java.io.*,java.text.*" contentType="text/html;charset=utf8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<% pageContext.include("header.html");%>
<%
    String commentId = request.getParameter("commentId");
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
                <h4>作答详情</h4>
                <hr />

                <p style="font-weight: bold" id="comment-subject">#</p>
                <p class="notice-desc" id="comment-desc"></p>
                <p><label>分数</label></p>
                <p><div id="comment-score"></div></p>
                <p><label>教师评语</label></p>
                <p><div id="comment-cmt"></div></p>
                <hr />
                <p><label>作答内容</label></p>
                <p><div id="comment-content"></div></p>

                <hr />

                <div id="comment-teacher-write-area">
                    <p class="notice-desc">仅教师可以批改</p>
                    <p><textarea class="form-control" name="commentcmt" id="commentcmt" minlength="4" placeholder="请输入评语..." required="required"></textarea></p>
                    <p><input type="text" class="form-control" minlength="1" placeholder="请输入分数..." required="required" name="commentscore" id="commentscore"></p>
                    <button type="button" class="btn btn-success" id="submitbtn" disabled>提 交</button>
                    <p style="color: red;display: none;" id="error-msg"></p>
                </div>


            </div>
        </div>
    </div>

<% pageContext.include("footer.html");%>

<script>

    function dofunc() {
        $("#func-task").css("background-color","#d8d8d8");
        $("#task-list-btn").css("background-color","#d8d8d8");
        if(userType == 1) {
            $("#submitbtn").attr("disabled",false);
        } else {
            $("#submitbtn").attr("disabled",true).text("没有权限").removeClass("btn-success").addClass("btn-danger");
        }
        showCommentDetail();
    }

    function showCommentDetail() {
        $.ajax({
            type: "POST",
            url: "../console/showCommentDetail?t=" + Math.random(),
            dataType: "json",
            data: "commentid=" + <%=commentId%>,
            success: function (json) {
                if (json.errCode == 0) {

                    $("#comment-subject").append(json.commentId + " 作业「<a href='taskDetail.jsp?taskId=" + json.commentTask + "'>#" + json.commentTask + " " +  json.commentTaskName + "</a>」的作答");
                    $("#comment-desc").append("作答学生：" + json.commentStuName + "（" + json.commentStuCollegeId + "），创建于" + json.commentTime);
                    $("#comment-content").append(json.commentContent);
                    $("#comment-cmt").append(json.commentCmt);
                    $("#comment-score").append(json.commentScore);
                } else {
                    $("#comment-content").append("作答查询失败");
                }
            }
        });
    }

    $(document).ready(function () {

        $("#submitbtn").click(function() {
            $("#error-msg").hide();
            $("#error-msg").empty();
            $("#submitbtn").attr("disabled",true);
            var commentCmt = $("#commentcmt").val();
            var commentScore = $("#commentscore").val();

            $.ajax({
                type:"post",
                url:"checkComment?t="+ Math.random(),
                dataType:"json",
                data:"commentcmt=" + commentCmt + "&commentscore=" + commentScore + "&commentid=" + <%=commentId%>,
                success:function (json) {
                    if(json.errCode == 0) {
                        alert("批改提交成功");
                        window.location.reload();
                    } else {
                        $("#error-msg").append(json.errCode + " 批改提交失败：" + json.errMsg);
                        $("#error-msg").show();
                        $("#submitbtn").attr("disabled",false);
                    }
                }
            });
        });

    });


</script>

</html>

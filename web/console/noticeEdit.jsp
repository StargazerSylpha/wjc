<%@ page language="java" import="java.util.*,java.io.*,java.text.*" contentType="text/html;charset=utf8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<% pageContext.include("header.html");%>
<!--中部主要内容容器-->
    <div id="content-container" class="row content-container-css">

        <!--中部左侧功能选择栏容器-->
        <div id="content-func-select" class="col-md-3 content-func-select-css">
            <div id="content-func-select-area" class="content-func-select-area-css">
                <h4>课程通知</h4>
                <hr />
                <p class="content-func-select-title-css" id="notice-list-btn">> <a href="noticeList.jsp">通知列表</a></p>

                <p class="content-func-select-cat">教师</p>
                <p class="content-func-select-title-css" id="notice-add-btn">> <a href="noticeEdit.jsp">添加通知</a></p>
            </div>
        </div>

        <!--中部右侧主要内容总容器-->
        <div id="content-main-area" class="col-md-9 content-main-area-css">
            <div id="content-area" class="content-area-css">
                <h4>编辑通知</h4>
                <hr />

                <!--<form>-->

                    <p><label>通知主题</label></p>
                    <p><input class="form-control" type="text" name="noticesubject" id="noticesubject" minlength="1" placeholder="请输入通知主题..." required="required" ></p></p>
                    <p><label>通知内容</label></p>
                    <p><textarea class="form-control" name="noticecontent" id="noticecontent" minlength="4" placeholder="请输入通知内容..." required="required"></textarea></p>

                    <button type="button" class="btn btn-success" id="submitbtn" disabled>提 交</button>
                    <p style="color: red;display: none" id="error-msg"></p>

                <!--</form>-->
            </div>
        </div>
    </div>

<% pageContext.include("footer.html");%>

<script>
    function dofunc() {
        $("#func-notice").css("background-color","#d8d8d8");
        $("#notice-add-btn").css("background-color","#d8d8d8");
        if(userType != 1) {
            alert("没有权限");
            window.location = "noticeList.jsp";
        } else {
            $("#submitbtn").attr("disabled",false);
        }
    }

    $(document).ready(function() {
        $("#submitbtn").click(function() {
            $("#error-msg").hide();
            $("#error-msg").empty();
            $("#submitbtn").attr("disabled",true);
            var noticeSubject = $("input#noticesubject").val();
            var noticeContent = $("#noticecontent").val();

            $.ajax({
                type:"post",
                url:"editNotice?t="+ Math.random(),
                dataType:"json",
                data:"noticesubject=" + noticeSubject + "&noticecontent=" + noticeContent,
                success:function (json) {
                    if(json.errCode == 0) {
                        alert("通知添加成功");
                        window.location = "noticeList.jsp";
                    } else {
                        $("#error-msg").append(json.errCode + " 通知添加失败：" + json.errMsg);
                        $("#error-msg").show();
                        $("#submitbtn").attr("disabled",false);
                    }
                }
            });
        });

    });
</script>

</html>
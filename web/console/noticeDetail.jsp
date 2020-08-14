<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<% pageContext.include("header.html");%>
<%
    String noticeId = request.getParameter("noticeId");
%>
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
                    <h4>通知详情</h4>
                    <hr />

                    <p style="font-weight: bold" id="notice-subject">#</p>
                    <p class="notice-desc" id="notice-desc"></p>
                    <div id="notice-content">

                    </div>
                </div>
            </div>
        </div>

        <% pageContext.include("footer.html");%>
<script>

    function  dofunc() {
        $("#func-notice").css("background-color","#d8d8d8");
        $("#notice-list-btn").css("background-color","#d8d8d8");
        showNoticeDetail();
    }

    function showNoticeDetail() {

        $.ajax({
            type: "GET",
            url: "../console/showNoticeDetail?t=" + Math.random() + "&noticeId=" + <%=noticeId%>,
            dataType: "json",
            success: function (json) {
                if (json.errCode == 0) {
                    $("#notice-subject").append(json.noticeId + " " + json.noticeSubject);
                    $("#notice-desc").append(json.createUserName + "创建于" + json.createTime + "，最后编辑于" + json.lastChange);
                    $("#notice-content").append(json.noticeContent);
                } else {
                    $("#notice-content").append("通知查询失败");
                }


            }
        });
    }

</script>
</html>
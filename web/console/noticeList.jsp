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
                <p class="content-func-select-title-css"id="notice-add-btn">> <a href="noticeEdit.jsp">添加通知</a></p>
            </div>
        </div>

        <!--中部右侧主要内容总容器-->
        <div id="content-main-area" class="col-md-9 content-main-area-css">
            <div id="content-area" class="content-area-css">
                <h4>通知列表</h4>
                <hr />
                <table class="table table-striped table-hover" id="notice-list-table">
                    <tr>
                        <th>#</th>
                        <th>通知主题</th>
                        <th>创建用户</th>
                        <th>创建时间</th>
                        <th>最后修改</th>
                        <th>执行操作</th>
                    </tr>
                    <tr><td>-1</td><td>Loading...正在加载通知...</td></tr>
                </table>
            </div>
        </div>
    </div>

<% pageContext.include("footer.html");%>

<script>

    function dofunc() {
        $("#func-notice").css("background-color","#d8d8d8");
        $("#notice-list-btn").css("background-color","#d8d8d8");
        showNoticeList();
    }

    function showNoticeList() {
        $.ajax({
            type:"GET",
            url:"showNoticeList?t=" + Math.random(),
            dataType:"text",
            success:function(data) {
                $("table tr").eq(1).remove();
                $("#notice-list-table").append(data);
            }
        });
    }

    function deleteNotice(_noticeId) {

        if(userType != 1) {
            alert("没有权限");
        } else {
            if(confirm("是否删除通知#" + _noticeId + "（只能删除自己创建的通知）")) {
                $.ajax({
                    type:"post",
                    url:"deleteNotice?t="+ Math.random(),
                    dataType:"json",
                    data:"noticeid=" + _noticeId,
                    success:function (json) {
                        if(json.errCode == 0) {
                            alert("通知删除成功");
                            window.location = "noticeList.jsp";
                        } else {
                            alert(json.errCode + " 通知删除失败：" + json.errMsg);
                        }
                    }
                });
            }
        }
    }



</script>

</html>
<%@ page language="java" import="java.util.*,java.io.*,java.text.*" contentType="text/html;charset=utf8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<% pageContext.include("header.html");%>

<!--中部主要内容容器-->
    <div id="content-container" class="row content-container-css">

        <!--中部左侧功能选择栏容器-->
        <div id="content-func-select" class="col-md-3 content-func-select-css">
            <div id="content-func-select-area" class="content-func-select-area-css">
                <h4>学生管理</h4>
                <hr />


                <p class="content-func-select-cat-notop">教师</p>
                <p class="content-func-select-title-css" id="student-list-btn">> <a href="studentList.jsp">学生列表</a></p>
                <p class="content-func-select-title-css" id="student-add-btn">> <a href="javascript:void(0)" onclick="addStudent()">添加学生</a></p>
            </div>
        </div>

        <!--中部右侧主要内容总容器-->
        <div id="content-main-area" class="col-md-9 content-main-area-css">
            <div id="content-area" class="content-area-css">
                <h4>学生列表</h4>
                <hr />
                <table class="table table-striped table-hover" id="student-list-table">
                    <tr>
                        <th>#</th>
                        <th>学生姓名</th>
                        <th>学号</th>
                        <th>学生邮箱</th>
                        <th>学生电话</th>
                        <th>所在部门</th>
                        <th>执行操作</th>
                    </tr>
                    <tr><td>-1</td><td>Loading...正在加载学生信息...</td></tr>
                </table>
            </div>
        </div>
    </div>

<% pageContext.include("footer.html");%>

<script>

    function dofunc() {
        $("#func-student").css("background-color","#d8d8d8");
        $("#student-list-btn").css("background-color","#d8d8d8");
        if(userType != 1) {
            alert("没有权限");
            window.location = "noticeList.jsp";
        } else {
            showStudentList();
        }
    }

    function showStudentList() {
        $.ajax({
            type:"GET",
            url:"showStudentList?t=" + Math.random(),
            dataType:"text",
            success:function(data) {
                $("table tr").eq(1).remove();
                $("#student-list-table").append(data);
            }
        });
    }

    function editStudent() {
        alert("暂未开放");
    }

    function addStudent() {
        alert("暂未开放");
    }

</script>
</html>
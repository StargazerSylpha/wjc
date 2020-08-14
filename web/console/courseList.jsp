<%@ page language="java" import="java.util.*,java.io.*,java.text.*" contentType="text/html;charset=utf8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<% pageContext.include("header.html");%>

<!--中部主要内容容器-->
<div id="content-container" class="row content-container-css">

    <!--中部左侧功能选择栏容器-->
    <div id="content-func-select" class="col-md-3 content-func-select-css">
        <div id="content-func-select-area" class="content-func-select-area-css">
            <h4>课程信息</h4>
            <hr />
            <p class="content-func-select-title-css" id="course-list-btn">> <a href="courseList.jsp">课程列表</a></p>

            <p class="content-func-select-cat">教师</p>

            <p class="content-func-select-title-css" id="course-add-btn">> <a href="javascript:void(0)" onclick="addCourse()">添加课程</a></p>
        </div>
    </div>

    <!--中部右侧主要内容总容器-->
    <div id="content-main-area" class="col-md-9 content-main-area-css">
        <div id="content-area" class="content-area-css">
            <h4>课程列表</h4>
            <hr />
            <table class="table table-striped table-hover" id="course-list-table">
                <tr>
                    <th>#</th>
                    <th>课程名称</th>
                    <th>开课教师</th>
                    <th>开课时间</th>
                    <th>执行操作</th>
                </tr>
                <tr><td>-1</td><td>Loading...正在加载课程列表...</td></tr>
            </table>
        </div>
    </div>
</div>

<% pageContext.include("footer.html");%>

<script>

    function dofunc() {
        $("#func-course").css("background-color","#d8d8d8");
        $("#course-list-btn").css("background-color","#d8d8d8");
        showCourseList();
    }

    function showCourseList() {
        $.ajax({
            type:"GET",
            url:"showCourseList?t=" + Math.random(),
            dataType:"text",
            success:function(data) {
                $("table tr").eq(1).remove();
                $("#course-list-table").append(data);
            }
        });
    }

    function deleteCourse() {
        alert("暂未开放");
    }

    function addCourse() {
        alert("暂未开放");
    }

</script>
</html>
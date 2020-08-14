<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
    <% pageContext.include("header.html");%>
    <%
        String courseId = request.getParameter("courseId");
    %>
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
                <h4>课程详情</h4>
                <hr />
                <p><label>课程号</label></p>
                <p id="courseid"></p>
                <p><label>课程名称</label></p>
                <p id="coursename"></p>
                <p><label>开课教师</label></p>
                <p id="courseteachername"></p>
                <p><label>开课时间</label></p>
                <p id="coursecreatetime"></p>
                <p><label>课程介绍</label></p>
                <p id="courseintro"></p>
            </div>
        </div>
    </div>


    <% pageContext.include("footer.html");%>
    <script>

        function  dofunc() {
            $("#func-course").css("background-color","#d8d8d8");
            $("#course-list-btn").css("background-color","#d8d8d8");
            showCourseDetail();
        }

        function showCourseDetail() {

            $.ajax({
                type: "GET",
                url: "../console/showCourseDetail?t=" + Math.random() + "&courseId=" + <%=courseId%>,
                dataType: "json",
                success: function (json) {
                    if (json.errCode == 0) {
                        $("#courseid").append(json.courseId);
                        $("#coursename").append(json.courseName);
                        $("#courseteachername").append(json.courseTeacherName);
                        $("#coursecreatetime").append(json.courseTime);
                        $("#courseintro").append(json.courseIntro);

                    } else {
                        $("#courseid").append("课程查询失败");
                    }


                }
            });
        }

        function addCourse() {
            alert("暂未开放");
        }

    </script>
</html>
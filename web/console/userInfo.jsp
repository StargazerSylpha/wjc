<%@ page language="java" import="java.util.*,java.io.*,java.text.*" contentType="text/html;charset=utf8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<% pageContext.include("header.html");%>
    <div id="content-container" class="row content-container-css">

        <!--中部左侧功能选择栏容器-->
        <div id="content-func-select" class="col-md-3 content-func-select-css">
            <div id="content-func-select-area" class="content-func-select-area-css">
                <h4>用户中心</h4>
                <hr />
                <p class="content-func-select-title-css" id="user-info-btn">> <a href="userInfo.jsp">用户信息</a></p>
                <p class="content-func-select-title-css">> <a href="javascript:void(0)" onclick="changePwd()">修改密码</a></p>

            </div>
        </div>

        <!--中部右侧主要内容总容器-->
        <div id="content-main-area" class="col-md-9 content-main-area-css">
            <div id="content-area" class="content-area-css">
                <h4>用户信息</h4>
                <hr />
                <p id="uid"><span style="font-weight: bold" >UID：</span></p>
                <p id="user-college-id"><span style="font-weight: bold" >学号（工号）：</span></p>
                <p id="user-id"><span style="font-weight: bold" >用户名：</span></p>
                <p id="user-utype"><span style="font-weight: bold" >用户类型：</span></p>
                <p id="user-name"><span style="font-weight: bold">姓名：</span></p>
                <p id="user-email"><span style="font-weight: bold">电子邮箱：</span></p>
                <p id="user-phone"><span style="font-weight: bold">手机号：</span></p>
                <p id="user-department"><span style="font-weight: bold">所在部门：</span></p>
            </div>
        </div>
    </div>

<% pageContext.include("footer.html");%>

<script>

    function dofunc() {
        $("#func-user").css("background-color","#d8d8d8");
        $("#user-info-btn").css("background-color","#d8d8d8");
        showUserInfo();
    }

    function showUserInfo() {
        $.ajax({
            type:"GET",
            url:"showUserInfo?t=" + Math.random(),
            dataType:"json",
            success:function(json) {

                if(json.errCode == 0) {
                    $("#uid").append(json.uid);
                    $("#user-college-id").append(json.userCollegeId);
                    $("#user-id").append(json.userId);
                    $("#user-name").append(json.userName);
                    $("#user-email").append(json.userEmail);
                    $("#user-phone").append(json.userPhone);
                    $("#user-department").append(json.userDepartment);
                    /**
                     * 不知道为什么 这里写#user-type 老是无法append内容
                     * 然而能正常从接口读取和使用内容
                     * $("#user-utype").append(json.userType);
                     */


                    let userTypeString = "";

                    if(json.userType == 1) {
                        $("#user-utype").append("1 教师");
                    }
                    if(json.userType == 2) {
                        $("#user-utype").append("2 学生");
                    }

                } else {
                    $("#uid").append("获取用户信息失败");
                }
            }
        });
    }

    function changePwd() {
        alert("暂未开放");
    }



</script>

</html>
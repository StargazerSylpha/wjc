package wjc.servlet;

import com.alibaba.fastjson.JSON;
import wjc.classes.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="DeleteTaskServlet",urlPatterns = "/console/deleteTask")
public class DeleteTaskServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();
		int errCode = -1;
		//String errMsg = new String();
		int userType = -1;
		TaskDao td = new TaskDao();
		ErrorJson dtr = new ErrorJson(); //DeleteTaskResult

		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");
		if(sessUid == null) {
			errCode = - 1;

		} else {
			try {
				UserDao ud = new UserDao();
				User u = new User();
				u.setUid(Integer.parseInt(sessUid.toString()));
				userType = ud.getUserInfo(u).getUserType();
			} catch (Exception e) {
				System.out.println("5004-获取用户权限失败-" + e.getMessage());
				errCode = 5004;
			} finally {
				if(userType == 1) {
					String taskId = _request.getParameter("taskid");
					try {
						errCode = td.deleteTask(taskId,sessUid.toString());
					} catch (Exception e) {
						System.out.println("5007-删除通知失败-" + e.getMessage());
						errCode = 5007;
					}
				} else {
					errCode = 5008;
					//errMsg = "没有权限";
				}
			}
		}

		dtr.setErrCode(errCode + "");
		dtr.setErrMsg(ErrorHelper.getErrMsg(errCode));
		out.println(JSON.toJSONString(dtr));
	}
}
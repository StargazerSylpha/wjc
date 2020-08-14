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

@WebServlet(name = "SetTaskStatusServlet",urlPatterns = "/console/setTaskStatus")
public class SetTaskStatus extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		int userType = -1;
		UserDao ud = new UserDao();
		TaskDao td = new TaskDao();
		Task t = new Task();
		int errCode;
		ErrorJson stsr = new ErrorJson(); //SetTaskStatusResult

		String taskId = _request.getParameter("taskid");

		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");
		userType = ud.getUserType(sessUid);

		if(userType == 1) {

			try {
				errCode = td.setTaskStatus(taskId,sessUid.toString());
			} catch (Exception e) {
				System.out.println("6012-更改作业状态失败" + e.getMessage());
				errCode = 6012;
			}

		} else {
			errCode = 6011;
		}
		stsr.setErrCode(errCode + "");
		stsr.setErrMsg(ErrorHelper.getErrMsg(errCode));

		out.println(JSON.toJSONString(stsr));

	}
}

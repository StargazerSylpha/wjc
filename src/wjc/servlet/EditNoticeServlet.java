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
import java.util.Date;

@WebServlet(name="EditNoticeServlet",urlPatterns = "/console/editNotice")
public class EditNoticeServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		NoticeDao nd = new NoticeDao();
		Notice n = new Notice();
		int errCode = -1;
		//String errMsg = new String();
		ErrorJson enr = new ErrorJson(); //EditNoticeResult
		int userType = -1;

		UserDao ud = new UserDao();
		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");
		userType = ud.getUserType(sessUid);

		if(userType == 1) {
			n.setNoticeSubject(_request.getParameter("noticesubject"));
			n.setNoticeContent(_request.getParameter("noticecontent"));
			try {
				errCode = nd.editNotice(n,sessUid.toString());
			} catch (Exception e) {
				System.out.println("4006-编辑通知失败-" + e.getMessage());
				errCode = 4006;
			}
		} else {
			errCode = 4008;
			//errMsg = "没有权限";
		}

		enr.setErrCode(errCode + "");
		enr.setErrMsg(ErrorHelper.getErrMsg(errCode));

		out.println(JSON.toJSONString(enr));
	}
}
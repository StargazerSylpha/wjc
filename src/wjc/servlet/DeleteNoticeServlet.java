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

@WebServlet(name="DeleteNoticeServlet",urlPatterns = "/console/deleteNotice")
public class DeleteNoticeServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();
		int errCode = -1;
		//String errMsg = new String();
		int userType = -1;
		NoticeDao nd = new NoticeDao();
		ErrorJson dnr = new ErrorJson(); //DeleteNoticeResult

		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");
		if(sessUid == null) {
			errCode = - 1;
			//errMsg = "not login";
		} else {
			try {
				UserDao ud = new UserDao();
				User u = new User();
				u.setUid(Integer.parseInt(sessUid.toString()));
				userType = ud.getUserInfo(u).getUserType();
			} catch (Exception e) {
				System.out.println("4009-获取用户权限失败-" + e.getMessage());
				errCode = 4009;
			} finally {
				if(userType == 1) {
					String noticeId = _request.getParameter("noticeid");
					try {
						errCode = nd.deleteNotice(noticeId,sessUid.toString());
					} catch (Exception e) {
						System.out.println("4013-删除通知失败-" + e.getMessage());
						errCode = 4013;
					}
				} else {
					errCode = 4010;
					//errMsg = "没有权限";
				}
			}
		}

		dnr.setErrCode(errCode + "");
		dnr.setErrMsg(ErrorHelper.getErrMsg(errCode));
		out.println(JSON.toJSONString(dnr));
	}
}
package wjc.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import com.alibaba.fastjson.JSON;

import wjc.classes.ErrorHelper;
import wjc.classes.ErrorJson;
import wjc.classes.User;
import wjc.classes.UserDao;



@WebServlet(name="LoginServlet",urlPatterns = "/login/doLogin")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		User u = new User();
		UserDao ud = new UserDao();
		int errCode = -1;

		String inputUserId = _request.getParameter("username");
		String inputPassword = _request.getParameter("password");

		u.setUserId(inputUserId);
		u.setUserPassword(inputPassword);

		try {
			errCode = ud.userLogin(u);

		} catch (Exception e) {
			System.out.println("2004-请求登录异常-" + e.getMessage());
			errCode = 2004;
			e.printStackTrace();
		} finally {
			ErrorJson em = new ErrorJson(); //ErrorMsg
			//String errMsg = new String();

			//errMsg = "Login success";
			//初始化session，有效期一年
			HttpSession sess = _request.getSession();
			sess.setMaxInactiveInterval(31536000);

			//初始化cookie，有效期一年
			//

			try {

				u = ud.getUserInfo(u);
				String ckUid_ = u.getUid() + "";

				Cookie ckUid = new Cookie("ckUid", ckUid_);
				Cookie ckTicket = new Cookie("ckLoginTicket",ud.getLoginTicket(u));
				ckUid.setMaxAge(31536000);
				ckTicket.setMaxAge(31536000);
				ckUid.setPath("/");
				ckTicket.setPath("/");

				//尝试设置
				sess.setAttribute("sessUid",u.getUid());
				_response.addCookie(ckUid);
				_response.addCookie(ckTicket);
			} catch (Exception ex) {
				System.out.println("2005-cookie/session设置失败-" + ex.getMessage());
				ex.printStackTrace();
				errCode = 2005;
				//errMsg = "Login error";
			}

			em.setErrCode(errCode + "");
			em.setErrMsg(ErrorHelper.getErrMsg(errCode));

			out.println(JSON.toJSONString(em));
		}

	}
}
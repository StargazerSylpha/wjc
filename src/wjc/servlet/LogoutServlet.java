package wjc.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet",urlPatterns = "/login/logout")
public class LogoutServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		try {
			//校验失败，cookie、session销毁
			_request.getSession().invalidate();

			Cookie ckUid_ = new Cookie("ckUid",null);
			ckUid_.setMaxAge(0);
			ckUid_.setPath("/");
			Cookie ckTicket_ = new Cookie("ckLoginTicket",null);
			ckTicket_.setMaxAge(0);
			ckTicket_.setPath("/");

			_response.addCookie(ckUid_);
			_response.addCookie(ckTicket_);

			_response.sendRedirect("login.html");

		}catch (Exception e) {
			System.out.println("3006-登出时cookie/session销毁失败");
			//这一个无关紧要所以不需要返回errCode
		}
	}
}

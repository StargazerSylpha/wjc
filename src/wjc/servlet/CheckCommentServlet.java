package wjc.servlet;

import com.alibaba.fastjson.JSON;
import wjc.classes.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 教师批改作答Servlet
 */

@WebServlet(name = "CheckCommentServlet",urlPatterns = "/console/checkComment")
public class CheckCommentServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		Object sessUid = _request.getSession().getAttribute("sessUid");
		int errCode = -1;
		int userType = -1;
		UserDao ud = new UserDao();
		userType = ud.getUserType(sessUid);
		CommentDao cd = new CommentDao();
		Comment c = new Comment();
		ErrorJson ccr = new ErrorJson();

		if(userType == 1) {
			c.setCommentCmt(_request.getParameter("commentcmt"));
			c.setCommentId(_request.getParameter("commentid"));
			c.setCommentScore(_request.getParameter("commentscore"));

			try {
				errCode = cd.checkComment(c,sessUid.toString());
			} catch (Exception e) {
				System.out.println("8015-作答评价异常-" + e.getMessage());
				errCode = 8015;
			}
		} else {
			errCode = 8010; //没有权限
		}

		ccr.setErrCode(errCode + "");
		ccr.setErrMsg(ErrorHelper.getErrMsg(errCode));
		out.println(JSON.toJSONString(ccr));
	}
}

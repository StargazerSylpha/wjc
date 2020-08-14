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

@WebServlet(name = "EditCommentServlet",urlPatterns = "/console/editComment")
public class EditCommentServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		CommentDao cd = new CommentDao();
		Comment c = new Comment();
		int errCode = -1;
		ErrorJson ecr = new ErrorJson(); //EditCommentResult
		int userType = -1;


		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");
		UserDao ud = new UserDao();
		userType = ud.getUserType(sessUid);

		if(userType == 2) {
			c.setCommentContent(_request.getParameter("commentcontent"));
			c.setCommentTask(_request.getParameter("commenttask"));
			c.setCommentStu(sessUid.toString());
			try {
				errCode = cd.editComment(c);
			} catch (Exception e) {
				System.out.println("6003-作答失败-" + e.getMessage());
				errCode = 6003;
			}
		} else {
			errCode = 6002;
			//errMsg = "没有权限";
		}

		ecr.setErrCode(errCode + "");
		ecr.setErrMsg(ErrorHelper.getErrMsg(errCode));

		out.println(JSON.toJSONString(ecr));
	}
}


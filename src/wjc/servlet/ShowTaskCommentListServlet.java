package wjc.servlet;

import wjc.classes.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ShowTaskCommentServlet",urlPatterns = "/console/showTaskCommentList")
public class ShowTaskCommentListServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		CommentDao cd = new CommentDao();
		ArrayList<Comment> alc = new ArrayList<>();
		String commentHtml = "";
		String taskId = _request.getParameter("taskid");

		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");
		UserDao ud = new UserDao();
		int userType = -1;

		int errCode = -1;

		userType = ud.getUserType(sessUid);
		if(userType == 1) {
			try {
				alc = cd.showTaskCommentList(taskId);
				errCode = 0;
			} catch (Exception e) {
				System.out.println("6009-获取作答列表失败");
				errCode = 6009;
			} finally {
				for(int i = alc.size() -1;i > -1; i--) {
					Comment c = alc.get(i);
					String cs = "";
					String cc = "";
					String cct = "";

					if(c.getCommentScore() == null) {
						cs = "";
					} else {
						cs = c.getCommentScore();
					}
					if(c.getCommentCmt() == null) {
						cc = "";
					} else {
						/**
						 * substring方法在字符串不够长的时候强行截取超过长度的字符串会抛异常
						 * StringIndexOutOfBoundsException
						 */
						cc = c.getCommentCmt().length() > 6 ? c.getCommentCmt().substring(0,5) + "..." : c.getCommentCmt();
					}
					cct = c.getCommentContent().length() > 6 ? c.getCommentContent().substring(0,5) + "..." : c.getCommentContent();

					commentHtml = commentHtml + "<tr><td>" + c.getCommentId() +"</td><td>" + c.getCommentStuName() + "（"+ c.getCommentStuCollegeId() +"）" + "</td><td>"
							+ cct + "</td><td>"+ c.getCommentTime() + "</td><td>" + cc + "</td><td>" + cs + "</td><td>" +
							" <a href='commentDetail.jsp?commentId=" + c.getCommentId() +"'>批改</a></td></tr>";
				}
			}
		}



		out.println(commentHtml);
	}
}

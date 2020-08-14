package wjc.servlet;

import wjc.classes.Notice;
import wjc.classes.User;
import wjc.classes.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name="ShowStudentListServlet",urlPatterns = "/console/showStudentList")
public class ShowStudentListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");
		UserDao ud = new UserDao();
		int userType = -1;
		int errCode = -1;
		String studentHtml = "";

		userType = ud.getUserType(sessUid);
		ArrayList<User> alu = new ArrayList<>();

		if(userType == 1) { //只有教师才能查看
			try {
				alu = ud.showStudentList();
			} catch (Exception e) {
				System.out.println("7002-获取学生列表异常-" + e.getMessage());
				errCode = 7002;
			} finally {
				for(int i = alu.size() -1;i > -1; i--) {
					User stu = alu.get(i);
					studentHtml = studentHtml + "<tr><td>" + stu.getUid() + "</td><td>" + stu.getUserName() +
							"</td><td>" + stu.getUserCollegeId() + "</td><td>" + (stu.getUserEmail() == null ? "" : stu.getUserEmail()) + "</td><td>" +
							(stu.getUserPhone() == null ? "" : stu.getUserPhone()) + "</td><td>" + (stu.getUserDepartment() == null ? "" :stu.getUserDepartment()) + "</td><td><a href='javascript:editStudent(" + stu.getUid() +")'>编辑</a></td></tr>";
				}
			}
		} else {
			errCode = 7001;
		}

		out.println(studentHtml);
	}
}

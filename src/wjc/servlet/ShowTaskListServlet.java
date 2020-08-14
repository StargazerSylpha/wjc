package wjc.servlet;

import wjc.classes.Task;
import wjc.classes.TaskDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name="ShowTaskListServlet",urlPatterns = "/console/showTaskList")
public class ShowTaskListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		TaskDao td = new TaskDao();
		ArrayList<Task> alt = new ArrayList<>();
		String taskHtml = "";

		int errCode = -1;

		try {
			alt = td.showTaskList();
			errCode = 0;
		} catch (Exception e) {
			System.out.println("5001-获取作业列表失败");
			errCode = 4001;
		} finally {
			for(int i = alt.size() -1;i > -1; i--) {
				Task t = alt.get(i);

				String ts = new String();
				if(t.getTaskStatus().equals("1")) { //作业还可以提交
					ts = "可提交</td><td><a href='javascript:setTaskStatus(" + t.getTaskId() + ")'>关闭</a>";
				} else if(t.getTaskStatus().equals("0")) { //作业已截止
					ts = "已截止</td><td><a href='javascript:setTaskStatus(" + t.getTaskId() + ")'>开启</a>";
				}

				taskHtml = taskHtml + "<tr><td>" + t.getTaskId() +"</td><td><a href='courseDetail.jsp?courseId=" + t.getTaskCourse() + "'>" + t.getTaskCourseName() + "</a></td><td><a href='taskDetail.jsp?taskId=" + t.getTaskId() + "'>" + t.getTaskSubject() +
						"</a></td><td>" + t.getTaskUserName() + "</td><td>" + t.getTaskCreate() + "</td><td>" +
						ts + " <a href='javascript:deleteTask(" + t.getTaskId() +")'>删除</a></td></tr>";
			}
		}
		out.println(taskHtml);
	}
}

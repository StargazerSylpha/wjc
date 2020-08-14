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

@WebServlet(name="ShowTaskDetailServlet",urlPatterns = "/console/showTaskDetail")
public class ShowTaskDetailServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();
		int errCode = -1;
		//String errMsg = new String();

		//获取sessUid判断是否登录
		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");
		/**
		 * object.toString()
		 * 必须保证object不是null，否则会抛NullPointerException异常
		 * https://www.cnblogs.com/pxzbky/p/10127517.html
		 */
		TaskDao td = new TaskDao();
		Task t = new Task();
		TaskDetail tdt = new TaskDetail();

		if(sessUid == null) {
			errCode = -1;
			//errMsg = "not login";
		} else {
			try {
				String taskId = _request.getParameter("taskId");
				t = td.showTaskDetail(taskId);
			} catch (Exception e) {
				System.out.println("5002-获取公告详情异常-" + e.getMessage());
				errCode = 5002;
				//e.printStackTrace();
			} finally {
				if(t.getTaskId() == null){
					errCode = 5003;
					//errMsg = "作业不存在";
				} else {
					errCode = 0;
					//errMsg = "success";
				}
			}
		}

		tdt.setErrCode(errCode + "");
		tdt.setErrMsg(ErrorHelper.getErrMsg(errCode));
		tdt.setTaskId(t.getTaskId());
		tdt.setTaskContent(t.getTaskContent());
		tdt.setTaskCourse(t.getTaskCourse());
		tdt.setTaskCourseName(t.getTaskCourseName());
		tdt.setTaskCreate(t.getTaskCreate());
		tdt.setTaskStatus(t.getTaskStatus());
		tdt.setTaskSubject(t.getTaskSubject());
		tdt.setTaskUser(t.getTaskUser());
		tdt.setTaskUserName(t.getTaskUserName());


		out.println(JSON.toJSONString(tdt));
	}
}

class TaskDetail {
	public String errCode;
	public String errMsg;
	public String errTime = Long.toString(new Date().getTime() / 1000);

	private String taskId;
	private String taskCourse;
	private String taskSubject;
	private String taskContent;
	private String taskUser;
	private String taskCreate;
	private String taskStatus;
	private String taskUserName;
	private String taskCourseName;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrTime() {
		return errTime;
	}

	public void setErrTime(String errTime) {
		this.errTime = errTime;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskCourse() {
		return taskCourse;
	}

	public void setTaskCourse(String taskCourse) {
		this.taskCourse = taskCourse;
	}

	public String getTaskSubject() {
		return taskSubject;
	}

	public void setTaskSubject(String taskSubject) {
		this.taskSubject = taskSubject;
	}

	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	public String getTaskUser() {
		return taskUser;
	}

	public void setTaskUser(String taskUser) {
		this.taskUser = taskUser;
	}

	public String getTaskCreate() {
		return taskCreate;
	}

	public void setTaskCreate(String taskCreate) {
		this.taskCreate = taskCreate;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskUserName() {
		return taskUserName;
	}

	public void setTaskUserName(String taskUserName) {
		this.taskUserName = taskUserName;
	}

	public String getTaskCourseName() {
		return taskCourseName;
	}

	public void setTaskCourseName(String taskCourseName) {
		this.taskCourseName = taskCourseName;
	}
}
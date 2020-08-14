package wjc.classes;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;

public class TaskDao {

	public TaskDao() {
		//...
	}

	public ArrayList<Task> showTaskList() throws Exception{
		ArrayList<Task> taskList = new ArrayList<>();
		String queryString = "select * from task,user,course where task.task_user = user.uid and task.task_course = course.course_id;";
		CachedRowSet rs = DbHelper.dbSelect(queryString);

		if(rs != null) {
			while (rs.next()) {
				Task t = new Task();
				t.setTaskId(rs.getInt("task_id") + "");
				t.setTaskContent(rs.getString("task_content"));
				t.setTaskCourse(rs.getInt("task_course") + "");
				t.setTaskCreate(rs.getString("task_create"));
				t.setTaskStatus(rs.getInt("task_status") + ""); //
				t.setTaskSubject(rs.getString("task_subject"));
				t.setTaskUser(rs.getInt("task_user") + "");
				t.setTaskUserName(rs.getString("user_name"));
				t.setTaskCourseName(rs.getString("course_name"));
				taskList.add(t);
			}
		}
		if(taskList.size() > 0) {
			return taskList;
		}
		return taskList;
	}

	public Task showTaskDetail(String _taskId) throws Exception {
		ArrayList<Task> taskResult = new ArrayList<>();
		String queryString = "select * from task,course,user where task.task_id = " + _taskId + " and task.task_course = course.course_id and task.task_user = user.uid;";
		CachedRowSet rs = null;
		Task t = new Task();

		if(checkTaskId(_taskId)) {
			rs = DbHelper.dbSelect(queryString);
			if(rs != null) {
				while(rs.next()) {
					Task t_ = new Task();
					t_.setTaskId(rs.getInt("task_id") + "");
					t_.setTaskContent(rs.getString("task_content"));
					t_.setTaskCourse(rs.getInt("task_course") + "");
					t_.setTaskCreate(rs.getString("task_create"));
					t_.setTaskStatus(rs.getInt("task_status") + ""); //
					t_.setTaskSubject(rs.getString("task_subject"));
					t_.setTaskUser(rs.getInt("task_user") + "");
					t_.setTaskUserName(rs.getString("user_name"));
					t_.setTaskCourseName(rs.getString("course_name"));
					taskResult.add(t_);
				}
			}

			if(taskResult.size() == 1) {
				t = taskResult.get(0);
				return  t;
			}
		}
		return t;

	}

	public boolean checkTaskId(String _taskId) {

		if(_taskId == null) {
			return false;
		}
		int taskIdInt = Integer.parseInt(_taskId);

		if(taskIdInt <= 0) {
			return false;
		}
		//字符不检查，只检查长度
		return true;
	}

	public int deleteTask(String _taskId,String _uid) throws Exception {
		String queryString = new String();
		queryString = "delete from task where task_id = '" + _taskId +"' and task_user='" + _uid +"';";
		UserDao ud = new UserDao();
		int errCode = -1;

		if(ud.checkUid(_uid)) {
			if(checkTaskId(_taskId)) {
				errCode = DbHelper.dbChange(queryString);
			} else {
				errCode = 5005;
			}
		} else {
			errCode = 5006;
		}

		return errCode;
	}

	public int setTaskStatus(String _taskId,String _uid) throws Exception {
		String queryString = new String();

		int errCode = -1;
		UserDao ud = new UserDao();

		if(ud.checkUid(_uid)) {
			if(checkTaskId(_taskId)) {
				//taskId与uid都合法

				//检查taskId是否存在、是否属于当前用户（也为了获取taskStatus）
				queryString = "select * from task where task_id = '" + _taskId + "' and task_user = '" + _uid + "';";
				String taskStatus = DbHelper.dbSelectSingle(queryString,"task_status");

				if(taskStatus == null) {
					return 5009;
				} else if(taskStatus.equals("1")) {
					queryString = "update task set task_status = 0 where task_id =" + _taskId;
				} else if(taskStatus.equals("0")) {
					queryString = "update task set task_status = 1 where task_id =" + _taskId;
				}

				errCode = DbHelper.dbChange(queryString);
			} else {
				errCode = 5011;
			}
		} else {
			errCode = 5010;
		}

		return errCode;
	}
}

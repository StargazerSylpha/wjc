package wjc.classes;


import javax.sql.rowset.CachedRowSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentDao {

	public CommentDao() {
		//...
	}

	public boolean checkCommentContent(String _commentContent) {
		if(_commentContent == null) {
			return false;
		}
		if(_commentContent.length() < 4) {
			return false;
		}
		return true;
	}

	public boolean checkCommentCmt(String _commentCmt) {

		if(_commentCmt == null) {
			return false;
		}
		if(_commentCmt.length() < 4) {
			return false;
		}
		return true;
	}

	public boolean checkCommentScore(String _commentScore) {
		if(_commentScore == null) {
			return false;
		}
		int commentScoreInt = Integer.parseInt(_commentScore);

		if(commentScoreInt < 0 || commentScoreInt > 9999) {
			/**
			 * 不会吧不会吧
			 * 应该没有人给到1w分或者负分⑧
			 */
			return false;
		}
		//字符不检查，只检查长度
		return true;
	}

	public int editComment(Comment _c) throws Exception {
		String queryString = new String();
		String taskExistQuery = new String();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ts = sdf.format(new Date());
		queryString = "INSERT INTO comment(comment_task, comment_stu, comment_content, comment_time) values('" + _c.getCommentTask() + "','" + _c.getCommentStu() + "','" + _c.getCommentContent() + "','" + ts + "');";
		taskExistQuery = "select * from task where task_id = " + _c.getCommentTask();
		int errCode = -1;
		UserDao ud = new UserDao();
		String taskExist = DbHelper.dbSelectSingle(taskExistQuery,"task_status");

		if(checkCommentContent(_c.getCommentContent())) {
			if(ud.checkUid(_c.getCommentStu())) {
				//这里受方法限制，可以随便找个表里类型为string的字段测试 存在即可
				if(taskExist == null) {
					return 6006; //课程不存在
				} else {
					if(taskExist.equals("0")) { //检测作业是否已截止
						return 6007;
					} else {
						errCode = DbHelper.dbChange(queryString);
					}
				}
			} else {
				errCode = 6005;
			}
		} else {
			errCode = 6004;
		}

		return errCode;
	}

	public int checkComment(Comment _c,String _teacherUid) throws Exception {
		String queryString = new String();
		/**
		 * update语句不要用and！！！用逗号！！！
		 */
		queryString = "update comment set comment_cmt='" + _c.getCommentCmt() + "' , comment_score='" + _c.getCommentScore() + "' where comment_id = '" + _c.getCommentId() + "';";
		System.out.println(queryString);
		String commentTaskTeacherCheckQuery = "";
		commentTaskTeacherCheckQuery = "select * from task,comment where task.task_id = comment.comment_task and comment.comment_id = '" + _c.getCommentId() + "' and task.task_user = '" + _teacherUid + "';";
		/**
		 * 检查_teacherUid、_c.getCommentCmt、_c.getCommentScore合法性
		 */
		String commentExist = DbHelper.dbSelectSingle(commentTaskTeacherCheckQuery,"task_id");
		UserDao ud = new UserDao();
		int errCode = -1;

		if(ud.checkUid(_teacherUid)) {
			if(checkCommentCmt(_c.getCommentCmt())) {
				if(checkCommentScore(_c.getCommentScore())) {
					if(commentExist == null) {
						return 8014;
					} else {
						errCode = DbHelper.dbChange(queryString);
					}
				} else {
					return 8013;
				}
			} else {
				return 8012;
			}
		} else {
			return 8011;
		}

		return errCode;
	}

	public ArrayList<Comment> showTaskCommentList(String _taskId) throws Exception{
		ArrayList<Comment> taskCommentList = new ArrayList<>();
		String queryString = "select * from comment,user where comment.comment_stu = user.uid and comment.comment_task = " + _taskId;
		TaskDao td = new TaskDao();
		if(td.checkTaskId(_taskId)) {
			CachedRowSet rs = DbHelper.dbSelect(queryString);
			if(rs != null) {
				while (rs.next()) {
					Comment c = new Comment();
					c.setCommentId(rs.getString("comment_id"));
					c.setCommentStuName(rs.getString("user_name"));
					c.setCommentStuCollegeId(rs.getString("user_college_id"));
					c.setCommentTime(rs.getString("comment_time"));
					c.setCommentCmt(rs.getString("comment_cmt"));
					c.setCommentScore(rs.getString("comment_score"));
					c.setCommentContent(rs.getString("comment_content"));
					taskCommentList.add(c);
				}
			}
		}
		if(taskCommentList.size() > 0) {
			return taskCommentList;
		}
		return taskCommentList;
	}

	public ArrayList<Comment> showStuCommentList(String _uid) throws Exception{
		ArrayList<Comment> stuCommentList = new ArrayList<>();
		String queryString = "SELECT * FROM comment,task WHERE comment.comment_task = task.task_id and comment.comment_stu = " + _uid;
		UserDao ud = new UserDao();
		if(ud.checkUid(_uid)) {
			CachedRowSet rs = DbHelper.dbSelect(queryString);
			if(rs != null) {
				while (rs.next()) {
					Comment c = new Comment();
					c.setCommentId(rs.getString("comment_id"));
					c.setCommentTask(rs.getString("comment_task"));
					c.setCommentTaskName(rs.getString("task_subject"));
					c.setCommentContent(rs.getString("comment_content"));
					c.setCommentTime(rs.getString("comment_time"));
					c.setCommentCmt(rs.getString("comment_cmt"));
					c.setCommentScore(rs.getString("comment_score"));
					stuCommentList.add(c);
				}
			}
		}

		return stuCommentList;
	}

	public Comment showCommentDetail(String _commentId) throws Exception {
		ArrayList<Comment> commentResult = new ArrayList<>();
		String queryString = "select * from task,comment,user where comment.comment_task = task.task_id and comment.comment_stu = user.uid and comment.comment_id = " + _commentId + ";";
		CachedRowSet rs = null;
		Comment c = new Comment();

		if (checkCommentId(_commentId)) {
			rs = DbHelper.dbSelect(queryString);
			if (rs != null) {
				while (rs.next()) {
					Comment c_ = new Comment();
					c_.setCommentId(rs.getString("comment_id"));
					c_.setCommentTask(rs.getString("comment_task"));
					c_.setCommentTaskName(rs.getString("task_subject"));
					c_.setCommentStuCollegeId(rs.getString("user_college_id"));
					c_.setCommentStuName(rs.getString("user_name"));
					c_.setCommentTime(rs.getString("comment_time"));
					c_.setCommentContent(rs.getString("comment_content"));
					c_.setCommentCmt(rs.getString("comment_cmt"));
					c_.setCommentScore(rs.getString("comment_score"));
					c_.setCommentStu(rs.getString("comment_stu"));
					commentResult.add(c_);
				}
			}

			if (commentResult.size() == 1) {
				c = commentResult.get(0);
				return c;
			}
		}
		return c;
	}

	public boolean checkCommentId(String _commentId) {
		if(_commentId == null) {
			return false;
		}
		int commentIdInt = Integer.parseInt(_commentId);

		if(commentIdInt <= 0) {
			return false;
		}
		//字符不检查，只检查长度
		return true;

	}
}

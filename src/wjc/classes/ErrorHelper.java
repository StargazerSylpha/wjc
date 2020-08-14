package wjc.classes;

import com.sun.tools.javac.Main;

public class ErrorHelper {

	public static String getErrMsg(int _errCode) {
		return getErrMsg(Integer.toString(_errCode));

	}

	public static String getErrMsg(String _errCode) {
		String errMsg = new String();

		switch (_errCode) {
			case "0":
				errMsg = "success";
				break;
			case "-1":
				errMsg = "not login";
				break;

			//DbHelper
			case "1001":
				errMsg = "1001-DbHelper类静态初始化异常";
				break;
			case "1002":
				errMsg = "1002-DbHelper类数据库连接测试异常";
				break;
			case "1003":
				errMsg = "1003-DbHelper类数据库连接失败";
				break;
			case "1004":
				errMsg = "1004-DbSelect初始化异常";
				break;
			case "1005":
				errMsg = "1005-关闭数据库连接失败";
				break;
			case "1006":
				errMsg = "1006-DbHelper类数据库执行select查询异常";
				break;
			case "1007":
				errMsg = "1007-DbChange初始化异常";
				break;
			case "1008":
				errMsg = "1008-DbChange执行失败";
				break;
			case "1009":
				errMsg = "1009-affected rows<=0";
				break;
			case "1010":
				errMsg = "1010-dbselectexist获取结果集失败";
				break;

			//UserDao
			case "3001":
				errMsg = "3001-找不到用户或登录名错误";
				break;
			case "3002":
				errMsg = "3002-uid不合法";
				break;
			case "3003":
				errMsg = "3003-loginTicket计算后匹配失败";
				break;
			case "2001":
				errMsg = "2001-校验用户名不合法";
				break;
			case "2002":
				errMsg = "2002-校验密码不合法";
				break;
			case "2003":
				errMsg = "2003-登录失败，信息有误";
				break;

			//CheckLoginServlet
			case "3004":
				errMsg = "3004-loginTicket计算失败";
				break;
			case "3005":
				errMsg = "3005-cookie/session销毁失败";
				break;
			case "3006":
				errMsg = "3006-session设置失败";
				break;

			//LoginServlet

			case "2004":
				errMsg = "2004-请求登录异常";
				break;
			case "2005":
				errMsg = "2005-cookie/session设置失败";
				break;

			//EditCommentServlet
			case "6001":
				errMsg = "6001-获取用户权限失败";
				break;

			case "6002":
				errMsg = "6002-没有权限";
				break;

			case "6003":
				errMsg = "6003-作答失败";
				break;

			//CommentDao

			case "6004":
				errMsg = "6004-作答内容不合法";
				break;
			case "6005":
				errMsg = "6005-uid不合法";
				break;
			case "6006":
				errMsg = "6006-课程不存在";
				break;
			case "6007":
				errMsg = "6007-作业已截止提交";
				break;
			case "6008":
				errMsg = "6008-taskId不合法";
				break;
			case "8011":
				errMsg = "8011-uid不合法";
				break;
			case "8012":
				errMsg = "8012-作答评语不合法";
				break;
			case "8013":
				errMsg = "8013-作答分数不合法";
				break;
			case "8014":
				errMsg = "8014-作答不存在或并非所属教师";
				break;


			//EditNoticeServlet
			case "4004":
				errMsg = "4004-通知内容不合法";
				break;
			case "4005":
				errMsg = "4005-通知标题不合法";
				break;
			case "4006":
				errMsg = "4006-编辑通知失败";
				break;
			case "4007":
				errMsg = "4007-获取用户权限失败";
				break;
			case "4008":
				errMsg = "4008-没有权限";
				break;

			//ShowNoticeDetailServlet
			case "4002":
				errMsg = "4002-获取公告详情异常";
				break;
			case "4003":
				errMsg = "4003-公告不存在";
				break;

			//ShowNoticeListServlet
			case "4001":
				errMsg = "4001-获取公告列表失败";
				break;

			//ShowUserInfo
			case "3007":
				errMsg = "3007-获取用户信息失败";
				break;
			case "3008":
				errMsg = "3008-获取用户信息为null";
				break;

			//DeleteNoticeServlet
			case "4009":
				errMsg = "4009-获取用户权限失败";
				break;
			case "4010":
				errMsg = "4010-没有权限";
				break;
			case "4011":
				errMsg = "4011-noticeId不合法";
				break;
			case "4012":
				errMsg = "4012-uid不合法";
				break;
			case "4013":
				errMsg = "4013-删除失败或并非所属用户";
				break;

			//ShowTaskListServlet
			case "5001":
				errMsg = "5001-获取作业列表失败";
				break;
			//ShowTaskDetailServlet
			case "5002":
				errMsg = "5002-获取公告详情异常";
				break;
			case "5003":
				errMsg = "5003-作业不存在";
				break;

			//DeleteTaskServlet
			case "5004":
				errMsg = "5004-获取用户权限失败";
				break;
			case "5007":
				errMsg = "5007-删除作业失败";
				break;
			case "5008":
				errMsg = "5008-没有权限";
				break;

			//TaskDao
			case "5005":
				errMsg = "5005-taskid不合法";
				break;
			case "5010":
				errMsg = "5010-uid不合法";
				break;
			case "5011":
				errMsg = "5011-taskid不合法";
				break;
			case "5009":
				errMsg = "5009-没有查找到符合条件的task或并非所属用户";
				break;
			case "5006":
				errMsg = "5006-uid不合法";
				break;

			//ShowTaskCommentServlet
			case "6009":
				errMsg = "6009-获取作答列表失败";
				break;
			case "6010":
				errMsg = "6010-获取用户权限失败";
				break;

			//SetTaskStatusServlet
			case "6011":
				errMsg = "6011-没有权限";
				break;
			case "6012":
				errMsg = "6012-更改作业状态失败";
				break;

			//ShowStudentListServlet
			case "7001":
				errMsg = "7001-没有权限";
				break;

			case "7002":
				errMsg = "7002-获取学生列表异常";
				break;

			//ShowCourseListServlet
			case "8001":
				errMsg = "8001-没有权限";
				break;
			case "8002":
				errMsg = "8002-获取课程列表异常";
				break;

			//ShowCourseDetailServlet
			case "8003":
				errMsg = "8003-没有权限";
				break;
			case "8004":
				errMsg = "8004-获取课程详情异常";
				break;
			case "8005":
				errMsg = "8005-课程不存在";
				break;

			//ShowStuCommentList

			case "8006":
				errMsg = "8006-获取学生作答列表异常";
				break;

			//ShowCommentDetailServlet
			case "8007":
				errMsg = "8007-获取作答详情异常";
				break;
			case "8008":
				errMsg = "8008-作答不存在";
				break;
			case "8009":
				errMsg = "8009-该作答所属学生并非当前用户";
				break;

			//CheckCommentServlet
			case "8010":
				errMsg = "8010-没有权限";
				break;
			case "8015":
				errMsg = "8015-作答评价异常";
				break;

			default:
				errMsg = "error";
				break;

		}
		return errMsg;
	}

}

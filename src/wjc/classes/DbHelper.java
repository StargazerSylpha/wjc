package wjc.classes;

//import com.mysql.cj.protocol.Resultset;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import java.sql.*;
import java.util.ArrayList;

/**
 * 开始使用：在dbUrl里填写自己mysql数据库的url（包括数据库名称），dbUsername填数据库用户名，dbPassword填数据库密码
 */

public class DbHelper {

	private static final String dbDriver = "com.mysql.cj.jdbc.Driver";
	private static final String dbUrl = "jdbc:mysql://localhost:3306/wjc?useUnicode=true&characterEncoding=utf-8&serverTimezone=PRC";
	private static final String dbUsername = "wjc";
	private static final String dbPassword = "wjc";
	private static Connection dbConn = null;
	static {
		try {
			Class.forName(dbDriver);
		} catch (Exception e) {
			System.out.println("1001-DbHelper类静态初始化异常-" + e.getMessage());
			e.printStackTrace();
		}
	}

	public static Connection getDbConn() throws Exception {
		//连接数据库
		if(dbConn == null) {
			dbConn = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
			return dbConn;
		} else {
			return dbConn;
		}
	}


	public static boolean dbConnTest() {
		//测试数据库是否连接
		try {
			Connection dbConn = DbHelper.getDbConn();
			if (dbConn != null) {
				return true;
			} else {
				System.out.println("1003-DbHelper类数据库连接失败");
				return false;
			}
		} catch (Exception e) {
			System.out.println("1002-DbHelper类数据库连接测试异常-" + e.getMessage());
			return false;
		}
	}


	/**
	 * dbSelect方法
	 * @author zl
	 * 进行select查询，传入select查询语句，返回一个CachedRowSet类型的结果集
	 */
	public static CachedRowSet dbSelect(String _query) {
		System.out.println("select-数据库是否已连接：" + DbHelper.dbConnTest());
		Connection dbConn = null;
		PreparedStatement dbPstmt = null;
		ResultSet dbRs = null;

		//https://www.cnblogs.com/lxblog0615/p/6273294.html
		//https://cloud.tencent.com/developer/ask/179931

		CachedRowSet selectResult = null;
		/**
		 * 若不关闭ResultSet、Statement以及Connection，会轻易耗尽系统资源
		 * 后期优化建议将ResultSet结果填充进ArrayList<String>并返回
		 * 然后执行三个close方法
		 *
		 * 还有拼查询字符串的改造
		 */

		try {
			dbConn = DbHelper.getDbConn();
			dbPstmt = dbConn.prepareStatement(_query);

		} catch (Exception e) {
			System.out.println("1004-DbSelect初始化异常-" + e.getMessage());
			//e.printStackTrace();
		}

		if(DbHelper.dbConnTest()) {
			try {
				selectResult = RowSetProvider.newFactory().createCachedRowSet();
				dbRs = dbPstmt.executeQuery();

				if(dbRs != null) {
					selectResult.populate(dbRs);
				}
			} catch (Exception e) {
				System.out.println("1006-DbHelper类数据库执行select查询异常-" + e.getMessage());
				//e.printStackTrace();
			}
			/**
			 * https://zhidao.baidu.com/question/557871988.html
			 * 用完之后肯定需要关闭的。。因为数据库的连接是有限的。
			 * 当然不是只是用一个功能就开关一次，这样效率会慢，你可以在需要的时候打开。。
			 * 比如你登陆网页，后台就需要打开数据库进行查找用户名密码，此时不需要立刻关闭，
			 * 你可能需要读取或者写数据库，你可以在退出登陆之后再关闭数据库，当然还是看个人的喜好吧。
			 *
			 * 连接一段时间（8个小时左右）就会连接不上，需要重启tomcat
			 * 需要连接池解决
			 */
			/*
			finally {
				try {
					if(dbRs != null) {
						dbRs.close();
					}
					if(dbStmt != null) {
						dbStmt.close();
					}
					if(dbConn != null) {
						dbConn.close();
					}
				} catch (Exception ex) {
					System.out.println("1005-关闭数据库连接失败" + ex.getMessage());
					//ex.printStackTrace();
				}
			}
			 */
		}
		return selectResult;
	}

	/**
	 * dbChange方法
	 * @author zl
	 * 进行insert into、update、delete查询
	 * 传入以上查询语句，返回int类型，0为成功，其他为失败
	 */
	public static int dbChange(String _query) {
		System.out.println("change-数据库是否已连接：" + DbHelper.dbConnTest());
		Connection dbConn = null;
		PreparedStatement dbPstmt = null;
		int affectedRows = -1;

		try {
			dbConn = DbHelper.getDbConn();
			dbPstmt = dbConn.prepareStatement(_query);

		} catch (Exception e) {
			System.out.println("1007-DbChange初始化异常-" + e.getMessage());
			return 1007;
			//e.printStackTrace();
		}

		if(DbHelper.dbConnTest()) {

			try {
				/**
				 * 若affected rows = 0 更新/删除不成功
				 * 使用excuteUpdate即可返回int类型的影响行数
				 * https://www.liudalao.com/archives/6/
				 */
				affectedRows = dbPstmt.executeUpdate();
			} catch (Exception e) {
				System.out.println("1008-DbChange执行失败-" + e.getMessage());
				//e.printStackTrace();
				return 1008;
			} finally {

				if(affectedRows < 1) {
					return 1009;
				}
			}
		}

		return 0;

	}

	/**
	 * dbSelectSingle方法
	 * @author zl
	 * 进行select单个单元格查询，传入select查询语句和要查询的字段名
	 * 返回一个String类型的字符串
	 * 可以查sql为int类型的字段（不建议）
	 * 主要用来检查某行是否存在
	 */

	public static String dbSelectSingle(String _query,String _colName) {
		/**
		 * 判断resultset填充的arraylist size是否为1即可
		 * 如<0返回false，大于0那就粗事了
		 * 后期写个方法记录错误信息到文档
		 */
		ResultSet rs = null;
		ArrayList<String> ali = new ArrayList<>();

		rs = dbSelect(_query);

		if(rs != null) {
			try {
				while(rs.next()) {
					ali.add(rs.getString(_colName));
				}
			} catch (Exception e) {
				System.out.println("1010-dbselectexist获取结果集失败-" + e.getMessage());
				return null;
			}

		}
		if (ali.size() == 1) {
			return ali.get(0) + ""; //查询不到返回null,getString可以获取int字段
		}

		return null;
	}

}

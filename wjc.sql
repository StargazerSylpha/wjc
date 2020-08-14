-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: 2020-08-01 23:19:39
-- 服务器版本： 5.7.17-log
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wjc`
--

-- --------------------------------------------------------

--
-- 表的结构 `comment`
--

CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL,
  `comment_task` int(11) NOT NULL,
  `comment_stu` int(11) NOT NULL,
  `comment_content` text NOT NULL,
  `comment_time` varchar(25) NOT NULL,
  `comment_cmt` text,
  `comment_score` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `comment`
--

INSERT INTO `comment` (`comment_id`, `comment_task`, `comment_stu`, `comment_content`, `comment_time`, `comment_cmt`, `comment_score`) VALUES
(1, 2, 3, '作答作答', '2020-06-21 20:32:23', '非常好', 10),
(2, 1, 3, '老师，我全都不会', '2020-06-21 21:16:54', 'tst', 100),
(3, 4, 3, 'sdfasdsa', '2020-06-29 00:37:20', NULL, NULL),
(4, 5, 3, '签到签到签到', '2020-07-04 05:27:17', 'ceshice2', 25),
(5, 5, 5, '11111111111111', '2020-07-04 05:27:41', NULL, NULL),
(6, 5, 6, '签到签到签到', '2020-07-04 05:27:59', '更改了评语', 10),
(7, 3, 3, '老师我全都会', '2020-07-16 08:37:08', NULL, NULL),
(8, 5, 3, 'asasfasdf', '2020-07-16 09:08:13', NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `course`
--

CREATE TABLE `course` (
  `course_id` int(11) NOT NULL,
  `course_name` text NOT NULL,
  `course_teacher` int(11) NOT NULL,
  `course_intro` text,
  `create_time` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `course`
--

INSERT INTO `course` (`course_id`, `course_name`, `course_teacher`, `course_intro`, `create_time`) VALUES
(1, 'Java Web程序设计（2019-2020-2 信1921班）', 2, '课程介绍课程介绍', '2020-06-20 19:02:57'),
(2, '摸鱼经济学与大众生活', 4, '摸鱼最爽了', '2020-06-20 11:17:38');

-- --------------------------------------------------------

--
-- 表的结构 `notice`
--

CREATE TABLE `notice` (
  `notice_id` int(11) NOT NULL,
  `notice_subject` text NOT NULL,
  `notice_content` text NOT NULL,
  `create_user` int(11) NOT NULL,
  `create_time` varchar(25) NOT NULL,
  `last_change` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `notice`
--

INSERT INTO `notice` (`notice_id`, `notice_subject`, `notice_content`, `create_user`, `create_time`, `last_change`) VALUES
(1, '欢迎来到签到作业管理系统', '我是测试公告', 1, '2020-06-14 17:16:03', '2020-06-14 17:16:03'),
(2, '今天你制造bug了吗？', '正在努力制造bug', 2, '2020-06-15 14:44:33', '2020-06-15 14:44:33'),
(3, '大作业要拍的片子', '还没有拍完，也没有剪完', 1, '2020-06-15 14:46:50', '2020-06-15 14:46:50'),
(6, 'jsq添加的通知', '11111', 2, '2020-06-19 16:56:25', '2020-06-19 16:56:25'),
(7, '数据表该怎么设计呢？', '啊啊啊啊', 1, '2020-06-20 00:48:31', '2020-06-20 00:48:31'),
(8, '【重要】明天下午13:45在三教110开教学研讨会', '时间：2020/06/21 13:45\n地点：三教（梦里）110\n准时集合，迟到扣工资！', 4, '2020-06-20 00:50:13', '2020-06-20 00:50:13'),
(11, 'test', 'test', 2, '2020-06-29 00:38:44', '2020-06-29 00:38:44'),
(10086, '<span style=\"color:red;\"><strong>【置顶】「学生签到作业管理系统」简介（及STAFF列表）</strong></span>', '本系统用以进行学生的管理、作业的提交<!--<del>以及课堂的签到功能（虽然说因为时间关系被砍掉了）</del>--> <br /><hr />\r\n技术栈：（前后端分离）<br />\r\n前端：bootstrap栅格系统进行页面布局，其他为自行设计 <br />\r\njsp用来拼接前端代码，增强前端代码复用，解决一些棘手的小问题<br />\r\n前后端数据交互：jquery ajax调用后端写好的api接口获得json然后插入到网页中，api接口获取的数据使用阿里巴巴的fastjson封装成json格式 <br />\r\n后端：java servlet<br>本公告还支持html和css代码（js应该也支持）\r\n\r\n<hr />\r\n\r\n<!--\r\ninspired by BV1Vf4y1278j\r\n-->', 1, '2020-06-20 22:23:16', '2020-06-20 22:23:16');

-- --------------------------------------------------------

--
-- 表的结构 `task`
--

CREATE TABLE `task` (
  `task_id` int(11) NOT NULL,
  `task_course` int(11) NOT NULL,
  `task_subject` text NOT NULL,
  `task_content` text,
  `task_user` int(11) NOT NULL,
  `task_create` varchar(25) NOT NULL,
  `task_status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `task`
--

INSERT INTO `task` (`task_id`, `task_course`, `task_subject`, `task_content`, `task_user`, `task_create`, `task_status`) VALUES
(1, 1, '预习第二章', '内容内容', 2, '2020-06-21 01:14:56', 0),
(2, 1, '完成习题1-3，1-9，1-11', '内容测试测内容', 2, '2020-06-21 01:17:56', 1),
(3, 2, '测试测试', '啊哈哈哈', 4, '2020-06-20 01:14:56', 0),
(4, 2, '明晚之前提交会议报告！', '内容测试啊啊啊测内容', 4, '2020-06-20 05:17:56', 0),
(5, 1, '2020年7月4日 8:00 三教112 课堂签到', '2020年7月4日 8:00 三教112 课堂签到', 2, '2020-07-04 07:58:49', 1);

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `uid` int(11) NOT NULL,
  `user_college_id` varchar(25) NOT NULL,
  `user_id` varchar(25) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `user_type` int(11) NOT NULL,
  `user_name` varchar(25) DEFAULT NULL,
  `user_email` varchar(50) DEFAULT NULL,
  `user_phone` varchar(25) DEFAULT NULL,
  `user_department` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`uid`, `user_college_id`, `user_id`, `user_password`, `user_type`, `user_name`, `user_email`, `user_phone`, `user_department`) VALUES
(1, '20200101', 'admin', '7c4a8d09ca3762af61e59520943dc26494f8941b', 1, '管理员', NULL, NULL, NULL),
(2, '20200102', 'jinsiquan', '7c4a8d09ca3762af61e59520943dc26494f8941b', 1, '金斯泉', 'jinsiquan@wjcu.cn', '15098952233', '信息工程系'),
(3, '20200103', 'chenhao', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, '辰豪', 'chenhao123@163.com', '+852-0752-915-7931', '信息工程系-信1921-1班'),
(4, '20200104', 'zhaoyegeng', '7c4a8d09ca3762af61e59520943dc26494f8941b', 1, '兆页耿', 'zhaoyegeng@wjcu.cn', '13919820303', '董事会办公室'),
(5, '20200105', 'liuhaoxiang', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, '柳豪祥', 'lhxlhx@wjcu.cn', '18815551999', '信息工程系-信1921-1班'),
(6, '20200106', 'jujiangkun', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, '鞠将昆', NULL, NULL, '信息工程系-信1921-1班');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`comment_id`),
  ADD UNIQUE KEY `comment_id` (`comment_id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`course_id`),
  ADD UNIQUE KEY `course_id` (`course_id`);

--
-- Indexes for table `notice`
--
ALTER TABLE `notice`
  ADD PRIMARY KEY (`notice_id`),
  ADD UNIQUE KEY `notice_id` (`notice_id`);

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`task_id`),
  ADD UNIQUE KEY `task_id` (`task_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`uid`),
  ADD UNIQUE KEY `user_college_id` (`user_college_id`),
  ADD UNIQUE KEY `uid` (`uid`),
  ADD UNIQUE KEY `user_id` (`user_id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `comment`
--
ALTER TABLE `comment`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- 使用表AUTO_INCREMENT `course`
--
ALTER TABLE `course`
  MODIFY `course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- 使用表AUTO_INCREMENT `notice`
--
ALTER TABLE `notice`
  MODIFY `notice_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10090;
--
-- 使用表AUTO_INCREMENT `task`
--
ALTER TABLE `task`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

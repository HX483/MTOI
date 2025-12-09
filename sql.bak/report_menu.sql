-- ====================================================
-- 报表分析模块菜单SQL
-- 创建时间：2025-01-01
-- 说明：执行此SQL文件将创建报表分析模块的菜单和权限配置
-- ====================================================

-- 一级菜单：报表分析（目录类型）
INSERT INTO sys_menu VALUES('2012', '报表分析', '0', '5', 'report', NULL, '', '', 1, 0, 'M', '0', '0', '', 'chart', 'admin', sysdate(), '', NULL, '报表分析目录');

-- 二级菜单：报表分析页面（菜单类型）
INSERT INTO sys_menu VALUES('2110', '报表分析', '2012', '1', 'index', 'report/index', '', '', 1, 0, 'C', '0', '0', 'report:dashboard:list', 'chart', 'admin', sysdate(), '', NULL, '报表分析菜单');

-- ====================================================
-- 报表分析按钮权限（按钮类型）
-- ====================================================
-- 仪表盘查询
INSERT INTO sys_menu VALUES('2111', '仪表盘查询', '2110', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'report:dashboard:list', '#', 'admin', sysdate(), '', NULL, '');

-- 库存统计按钮权限
INSERT INTO sys_menu VALUES('2112', '库存统计查询', '2110', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'report:stock:list', '#', 'admin', sysdate(), '', NULL, '');
INSERT INTO sys_menu VALUES('2113', '库存预警查询', '2110', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'report:stock:list', '#', 'admin', sysdate(), '', NULL, '');

-- 盘点分析按钮权限
INSERT INTO sys_menu VALUES('2114', '盘点分析查询', '2110', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'report:check:list', '#', 'admin', sysdate(), '', NULL, '');

-- 原料使用分析按钮权限
INSERT INTO sys_menu VALUES('2115', '原料使用查询', '2110', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'report:material:list', '#', 'admin', sysdate(), '', NULL, '');

-- 产品分析按钮权限
INSERT INTO sys_menu VALUES('2116', '产品分析查询', '2110', '6', '', '', '', '', 1, 0, 'F', '0', '0', 'report:product:list', '#', 'admin', sysdate(), '', NULL, '');

-- ====================================================
-- 为管理员角色（role_id=1）分配报表分析菜单权限
-- ====================================================
INSERT INTO sys_role_menu VALUES ('1', '2000');
INSERT INTO sys_role_menu VALUES ('1', '2001');
INSERT INTO sys_role_menu VALUES ('1', '2002');
INSERT INTO sys_role_menu VALUES ('1', '2003');
INSERT INTO sys_role_menu VALUES ('1', '2004');
INSERT INTO sys_role_menu VALUES ('1', '2005');
INSERT INTO sys_role_menu VALUES ('1', '2006');
INSERT INTO sys_role_menu VALUES ('1', '2007');

-- ====================================================
-- 为普通角色（role_id=2）分配报表分析菜单权限（可选）
-- 如需为普通角色分配权限，请取消下面的注释
-- ====================================================
-- INSERT INTO sys_role_menu VALUES ('2', '2000');
-- INSERT INTO sys_role_menu VALUES ('2', '2001');
-- INSERT INTO sys_role_menu VALUES ('2', '2002');
-- INSERT INTO sys_role_menu VALUES ('2', '2003');
-- INSERT INTO sys_role_menu VALUES ('2', '2004');
-- INSERT INTO sys_role_menu VALUES ('2', '2005');
-- INSERT INTO sys_role_menu VALUES ('2', '2006');
-- INSERT INTO sys_role_menu VALUES ('2', '2007');

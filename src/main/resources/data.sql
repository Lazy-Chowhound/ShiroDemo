INSERT INTO shiro.action (id, action_code, action_name) VALUES (1, 'INSERT', '添加');
INSERT INTO shiro.action (id, action_code, action_name) VALUES (2, 'DELETE', '删除');
INSERT INTO shiro.action (id, action_code, action_name) VALUES (3, 'UPDATE', '修改');
INSERT INTO shiro.action (id, action_code, action_name) VALUES (4, 'SELECT', '查询');

INSERT INTO shiro.module (id, module_code, module_name) VALUES (1, 'A', 'A模块');
INSERT INTO shiro.module (id, module_code, module_name) VALUES (2, 'B', 'B模块');
INSERT INTO shiro.module (id, module_code, module_name) VALUES (3, 'C', 'C模块');
INSERT INTO shiro.module (id, module_code, module_name) VALUES (4, 'D', 'D模块');

INSERT INTO shiro.permission (id, permission_name, module_id, action_id) VALUES (1, 'A:INSERT', 1, 1);
INSERT INTO shiro.permission (id, permission_name, module_id, action_id) VALUES (2, 'A:DELETE', 1, 2);
INSERT INTO shiro.permission (id, permission_name, module_id, action_id) VALUES (3, 'A:UPDATE', 1, 3);
INSERT INTO shiro.permission (id, permission_name, module_id, action_id) VALUES (4, 'A:SELECT', 1, 4);
INSERT INTO shiro.permission (id, permission_name, module_id, action_id) VALUES (5, 'B:INSERT', 2, 1);
INSERT INTO shiro.permission (id, permission_name, module_id, action_id) VALUES (6, 'B:DELETE', 2, 2);
INSERT INTO shiro.permission (id, permission_name, module_id, action_id) VALUES (7, 'B:UPDATE', 2, 3);
INSERT INTO shiro.permission (id, permission_name, module_id, action_id) VALUES (8, 'B:SELECT', 2, 4);
INSERT INTO shiro.permission (id, permission_name, module_id, action_id) VALUES (9, 'C:INSERT', 3, 1);
INSERT INTO shiro.permission (id, permission_name, module_id, action_id) VALUES (10, 'C:DELETE', 3, 2);

INSERT INTO shiro.role (id, role_name, permissions) VALUES (1, 'R1', '[4, 8, 12, 16]');
INSERT INTO shiro.role (id, role_name, permissions) VALUES (2, 'R2', '[1, 2, 3, 4, 5, 6, 7, 8]');
INSERT INTO shiro.role (id, role_name, permissions) VALUES (3, 'R3', '[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]');
INSERT INTO shiro.role (id, role_name, permissions) VALUES (4, 'R4', '[1, 2, 3, 4]');
INSERT INTO shiro.role (id, role_name, permissions) VALUES (5, 'R5', '[5, 6, 7, 8]');
INSERT INTO shiro.role (id, role_name, permissions) VALUES (6, 'R6', '[9, 10, 11, 12]');
INSERT INTO shiro.role (id, role_name, permissions) VALUES (7, 'R7', '[13, 14, 15, 16]');
INSERT INTO shiro.role (id, role_name, permissions) VALUES (8, 'ROOT', '[0]');

INSERT INTO shiro.user (password, role, id, name) VALUES ('114514', '[8]', 1, '茅厂彦晶');
INSERT INTO shiro.user (password, role, id, name) VALUES ('123456', '[3, 4]', 2, '一方通行');
INSERT INTO shiro.user (password, role, id, name) VALUES ('061210', '[6, 7]', 3, '中野三玖');
INSERT INTO shiro.user (password, role, id, name) VALUES ('111111', '[5, 6]', 4, '藏原走');
INSERT INTO shiro.user (password, role, id, name) VALUES ('000000', '[1, 2]', 5, '蕾姆');
INSERT INTO shiro.user (password, role, id, name) VALUES ('333333', '[4, 7]', 6, '和泉纱雾');
INSERT INTO shiro.user (password, role, id, name) VALUES ('444444', '[2, 6]', 7, '可可萝');
INSERT INTO shiro.user (password, role, id, name) VALUES ('555555', '[1, 3, 5]', 8, '冯宝宝');
CREATE DATABASE `team_manager` DEFAULT CHARACTER SET=utf8;

USE `zy_team_mgr`;

CREATE TABLE `a_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `a_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(512) DEFAULT NULL,
  `identifier` varchar(32) NOT NULL,
  `is_default` tinyint(2) NOT NULL,
  `name` varchar(32) NOT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_identifier` (`identifier`),
  KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `a_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `identifier` varchar(64) DEFAULT NULL,
  `is_end` tinyint(2) NOT NULL,
  `memo` varchar(512) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `priority` int(11) NOT NULL,
  `tree_level` int(11) NOT NULL,
  `type` tinyint(2) NOT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `url` varchar(512) DEFAULT NULL,
  `valid` tinyint(2) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `resource_icon` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_created_time` (`created_time`),
  KEY `idx_valid` (`valid`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `a_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_resource_id` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `a_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `url` varchar(128) DEFAULT NULL,
  `params` text DEFAULT NULL,
  `remote_ip` varchar(255) DEFAULT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `a_role` VALUES (1, '2016-05-05 17:00:53', '系统管理员', 'admin', 0, '系统管理员', '2016-05-13 10:57:50', 1);

INSERT INTO `a_user_role` VALUES (1, 1, 1);

INSERT INTO `a_resource` VALUES (1, '2016-05-05 17:02:30', null, '0', null, '系统管理', '0', '0', '1', '2016-05-13 11:38:14', null, '1', null, 'menu-icon fa fa-desktop blue');
INSERT INTO `a_resource` VALUES (2, '2016-05-05 17:04:35', null, '0', null, '用户管理', '0', '1', '1', '2016-05-05 17:05:07', '/user/list', '1', '1', 'menu-icon fa fa-users green');
INSERT INTO `a_resource` VALUES (3, '2016-05-06 13:19:32', 'arsenal:user:list', '1', null, '用户列表', '0', '2', '2', '2016-05-06 13:20:04', '/user/list', '1', '2', null);
INSERT INTO `a_resource` VALUES (4, '2016-05-10 11:09:18', 'arsenal:user:view', '1', null, '用户查看', '0', '2', '2', '2016-05-10 11:09:51', '/user/view/**', '1', '2', null);
INSERT INTO `a_resource` VALUES (5, '2016-05-13 13:43:23', 'arsenal:user:changePassword', '1', null, '密码修改', '0', '2', '2', '2016-05-13 13:43:22', '/user/password/change', '1', '2', null);
INSERT INTO `a_resource` VALUES (6, '2016-05-13 15:54:44', 'arsenal:user:role', '1', null, '分配角色', '0', '2', '2', '2016-05-16 11:34:24', '/user/role/allot/**', '1', '2', null);
INSERT INTO `a_resource` VALUES (7, '2016-05-12 11:48:33', null, '0', null, '角色管理', '0', '1', '1', '2016-05-16 14:45:26', '/role/list', '1', '1', 'menu-icon fa fa-user blue');
INSERT INTO `a_resource` VALUES (8, '2016-05-12 13:09:21', 'arsenal:role:list', '1', null, '角色列表', '0', '2', '2', '2016-05-12 13:09:50', '/role/list', '1', '7', null);
INSERT INTO `a_resource` VALUES (9, '2016-05-12 15:07:24', 'arsenal:role:view', '1', null, '角色查看', '0', '2', '2', '2016-05-12 15:08:03', '/role/view/**', '1', '7', null);
INSERT INTO `a_resource` VALUES (10, '2016-05-12 15:09:56', 'arsenal:role:enable', '1', null, '角色启用', '0', '2', '2', '2016-05-12 15:10:02', '/role/enable/**', '1', '7', null);
INSERT INTO `a_resource` VALUES (11, '2016-05-12 15:46:57', 'arsenal:role:disable', '1', null, '角色禁用', '0', '2', '2', '2016-05-12 15:47:24', '/role/disable/**', '1', '7', null);
INSERT INTO `a_resource` VALUES (12, '2016-05-12 15:48:16', 'arsenal:role:create', '1', null, '角色添加', '0', '2', '2', '2016-05-12 15:48:42', '/role/create', '1', '7', null);
INSERT INTO `a_resource` VALUES (13, '2016-05-12 15:49:01', 'arsenal:role:update', '1', null, '角色修改', '0', '2', '2', '2016-05-12 15:49:21', '/role/update/**', '1', '7', null);
INSERT INTO `a_resource` VALUES (14, '2016-05-13 17:42:50', 'arsenal:role:resource', '1', null, '分配权限', '0', '2', '2', '2016-05-16 11:44:02', '/role/resource/allot/**', '1', '7', null);
INSERT INTO `a_resource` VALUES (15, '2016-05-12 18:42:51', null, '0', null, '资源管理', '0', '1', '1', '2016-05-12 18:43:22', '/resource/list', '1', '1', 'menu-icon fa fa-key orange2');
INSERT INTO `a_resource` VALUES (16, '2016-05-12 18:45:29', 'arsenal:resource:list', '1', null, '资源列表', '0', '2', '2', '2016-05-12 18:46:04', '/resource/list', '1', '15', null);
INSERT INTO `a_resource` VALUES (17, '2016-05-12 18:46:52', 'arsenal:resource:view', '1', null, '资源查看', '0', '2', '2', '2016-05-12 18:47:22', '/resource/view/**', '1', '15', null);
INSERT INTO `a_resource` VALUES (18, '2016-05-12 18:47:53', 'arsenal:resource:enable', '1', null, '资源启用', '0', '2', '2', '2016-05-12 18:48:21', '/resource/enable/**', '1', '15', null);
INSERT INTO `a_resource` VALUES (19, '2016-05-12 18:48:45', 'arsenal:resource:disable', '1', null, '资源禁用', '0', '2', '2', '2016-05-12 18:49:10', '/resource/disable/**', '1', '15', null);
INSERT INTO `a_resource` VALUES (20, '2016-05-12 18:49:36', 'arsenal:resource:create', '1', null, '资源添加', '0', '2', '2', '2016-05-12 18:50:03', '/resource/create', '1', '15', null);
INSERT INTO `a_resource` VALUES (21, '2016-05-12 18:50:20', 'arsenal:resource:update', '1', null, '资源修改', '0', '2', '2', '2016-05-12 18:50:46', '/resource/update/**', '1', '15', null);
INSERT INTO `a_resource` VALUES (22, '2016-05-13 10:48:42', null, '0', null, '日志管理', '0', '1', '1', '2016-05-13 15:07:24', '/log/list', '1', '1', 'menu-icon fa fa-pencil-square-o grey');
INSERT INTO `a_resource` VALUES (23, '2016-05-13 10:49:35', 'arsenal:log:list', '1', null, '日志列表', '0', '2', '2', '2016-05-13 10:54:22', '/log/list', '1', '22', null);
INSERT INTO `a_resource` VALUES (24, '2016-05-13 10:56:39', 'arsenal:log:view', '1', null, '日志查看', '0', '2', '2', '2016-05-13 10:56:38', '/log/view/**', '1', '22', null);

INSERT INTO `a_role_resource` VALUES (1, 1, 1);
INSERT INTO `a_role_resource` VALUES (2, 1, 2);
INSERT INTO `a_role_resource` VALUES (3, 1, 3);
INSERT INTO `a_role_resource` VALUES (4, 1, 4);
INSERT INTO `a_role_resource` VALUES (5, 1, 5);
INSERT INTO `a_role_resource` VALUES (6, 1, 6);
INSERT INTO `a_role_resource` VALUES (7, 1, 7);
INSERT INTO `a_role_resource` VALUES (8, 1, 8);
INSERT INTO `a_role_resource` VALUES (9, 1, 9);
INSERT INTO `a_role_resource` VALUES (10, 1, 10);
INSERT INTO `a_role_resource` VALUES (11, 1, 11);
INSERT INTO `a_role_resource` VALUES (12, 1, 12);
INSERT INTO `a_role_resource` VALUES (13, 1, 13);
INSERT INTO `a_role_resource` VALUES (14, 1, 14);
INSERT INTO `a_role_resource` VALUES (15, 1, 15);
INSERT INTO `a_role_resource` VALUES (16, 1, 16);
INSERT INTO `a_role_resource` VALUES (17, 1, 17);
INSERT INTO `a_role_resource` VALUES (18, 1, 18);
INSERT INTO `a_role_resource` VALUES (19, 1, 19);
INSERT INTO `a_role_resource` VALUES (20, 1, 20);
INSERT INTO `a_role_resource` VALUES (21, 1, 21);
INSERT INTO `a_role_resource` VALUES (22, 1, 22);
INSERT INTO `a_role_resource` VALUES (23, 1, 23);
INSERT INTO `a_role_resource` VALUES (24, 1, 24);
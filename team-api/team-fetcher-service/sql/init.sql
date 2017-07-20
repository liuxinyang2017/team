CREATE DATABASE `zy_team` DEFAULT CHARACTER SET=utf8;

use `zy_team`;

CREATE TABLE `proxy_data` (
  `id` bigint(20) NOT NULL,
  `host` varchar(32) NOT NULL,
  `port` int(11) NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '',
  `password` varchar(64) NOT NULL DEFAULT '',
  `proxy_validate_status` int(11) NOT NULL,
  `failed_count` int(11) NOT NULL DEFAULT 0,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `begin_test_time` TIMESTAMP NULL DEFAULT NULL,
  `end_test_time` TIMESTAMP NULL DEFAULT NULL,
  `score` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_host_port` (`host`, `port`),
  INDEX `idx_status_created_time` (`proxy_validate_status`, `created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
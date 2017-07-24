CREATE DATABASE `zy_team` DEFAULT CHARACTER SET=utf8;

use `zy_team`;

CREATE TABLE `proxy_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
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

CREATE TABLE `fetcher_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `proxy_id` bigint(20) NOT NULL,
  `host` varchar(32) NOT NULL,
  `port` int(11) NOT NULL,
  `fetcher_type`  int(11) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `begin_test_time` TIMESTAMP NULL DEFAULT NULL,
  `end_test_time` TIMESTAMP NULL DEFAULT NULL,
  `spent_mills` int(11) NOT NULL DEFAULT 0,
  `success`  TINYINT(3) NOT NULL,
  `message`  varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  INDEX `idx_proxy_id` (`proxy_id`),
  INDEX `idx_success` (`success`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `number_lottery_fetch_detail_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fetch_result_id` bigint(20) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lottery_type`  int(11) NOT NULL,
  `phase` varchar(20)  NOT NULL DEFAULT '',
  `fetcher_type`  int(11) NOT NULL,
  `fetched_time` TIMESTAMP NULL DEFAULT NULL,
  `prize_key` varchar(64)  NOT NULL DEFAULT '',
  `prize_name` varchar(64)  NOT NULL DEFAULT '',
  `prize_count` bigint(20) NOT NULL DEFAULT 0,
  `prize_amount` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `idx_fetch_result_id` (`fetch_result_id`),
  INDEX `idx_lottery_type` (`lottery_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `number_lottery_fetch_result_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lottery_type`  int(11) NOT NULL,
  `phase` varchar(20)  NOT NULL DEFAULT '',
  `fetcher_type`  int(11) NOT NULL,
  `fetched_time` TIMESTAMP NULL DEFAULT NULL,
  `result`  varchar(255)  NOT NULL DEFAULT '',
  `pool_amount` bigint(20) NOT NULL DEFAULT 0,
  `sale_amount` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `idx_lottery_type` (`lottery_type`),
  INDEX `idx_fetcher_type` (`fetcher_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `proxy_validate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `proxy_id` bigint(20) NOT NULL,
  `host` varchar(32) NOT NULL,
  `port` int(11) NOT NULL,
  `proxy_validator_type` int(11) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `begin_test_time` TIMESTAMP NULL DEFAULT NULL,
  `end_test_time` TIMESTAMP NULL DEFAULT NULL,
  `spent_mills` int(11) NOT NULL DEFAULT 0,
  `success`  TINYINT(3) NOT NULL,
  `message`  varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_host_port` (`host`, `port`),
  INDEX `idx_proxy_validator_type` (`proxy_validator_type`),
  INDEX `idx_success` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
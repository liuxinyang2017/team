CREATE DATABASE `zy_team` DEFAULT CHARACTER SET=utf8;

user `zy_team`;

CREATE TABLE `number_lottery_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_type` INT NOT NULL,
  `phase` VARCHAR(16) NOT NULL,
  `phase_status` INT NOT NULL,
  `is_current` INT NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `open_time` timestamp NOT NULL,
  `close_time` timestamp NOT NULL,
  `prize_time` timestamp NOT NULL,
  `result` varchar(64)  NOT NULL DEFAULT '',
  `result_time` timestamp NULL DEFAULT NULL,
  `result_detail_time` timestamp NULL DEFAULT NULL,
  `pool_amount` bigint(20) NOT NULL DEFAULT 0,
  `sale_amount` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_phase` (`lottery_type`, `phase`),
  INDEX `idx_status_type` (`phase_status`, `lottery_type`),
  INDEX `idx_open_close_type` (`open_time`, `close_time`, `lottery_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `daemon_event_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_type` INT NOT NULL,
  `phase` VARCHAR(20) NOT NULL DEFAULT '',
  `match_num` VARCHAR(64) NOT NULL DEFAULT '',
  `type` INT NOT NULL,
  `status` INT NOT NULL,
  `execute_time` timestamp NOT NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
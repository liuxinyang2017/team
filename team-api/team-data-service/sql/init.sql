CREATE DATABASE `zy_team` DEFAULT CHARACTER SET=utf8;

CREATE TABLE `number_lottery_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_type` INT NOT NULL,
  `phase` VARCHAR(20) NOT NULL DEFAULT '',
  `phase_status` INT NOT NULL,
  `is_current` INT NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `open_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `close_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `prize_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `result` varchar(255)  NOT NULL DEFAULT '',
  `result_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `result_detail_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pool_amount` bigint(20) NOT NULL DEFAULT 0,
  `sale_amount` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_type_phase` (`lottery_type`, `phase`),
  INDEX `idx_lottery_type_phase` (`lottery_type`, `phase`),
  INDEX `idx_phase_status` (`phase_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
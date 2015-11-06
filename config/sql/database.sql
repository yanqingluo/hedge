SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `id`            INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  `name`          VARCHAR(200)     NOT NULL
  COMMENT '股票名称',
  `code`          VARCHAR(200)     NOT NULL
  COMMENT '股票代码',
  `organization`  VARCHAR(200)     NOT NULL
  COMMENT '所在交易所机构',
  `url`           VARCHAR(230)              DEFAULT NULL
  COMMENT '爬虫抓取的页面URL',
  `sync_end_date` DATE                      DEFAULT NULL
  COMMENT '数据同步截至时间',
  `total_shares`  BIGINT(20)                DEFAULT NULL
  COMMENT '总股本',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT
  COMMENT = '股票表';

DROP TABLE IF EXISTS `stock_price_day`;
CREATE TABLE `stock_price_day` (
  `id`         INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  `stock_id`   INT(11)          NOT NULL
  COMMENT 'stock.id',
  `thedate`    DATE             NOT NULL
  COMMENT '交易日期',
  `pre_date`   DATE                      DEFAULT NULL
  COMMENT '上一个交易日期',
  `next_date`  DATE                      DEFAULT NULL
  COMMENT '下一个交易日期',
  `open`       DOUBLE           NOT NULL DEFAULT '0'
  COMMENT '开盘价格',
  `close`      DOUBLE           NOT NULL DEFAULT '0'
  COMMENT '收盘价格',
  `high`       DOUBLE                    DEFAULT NULL
  COMMENT '最高价格',
  `low`        DOUBLE                    DEFAULT NULL
  COMMENT '最低价格',
  `chg`        DOUBLE                    DEFAULT NULL
  COMMENT '昨日涨跌',
  `percent`    DOUBLE                    DEFAULT NULL
  COMMENT '昨日涨跌率',
  `volume`     BIGINT(11)                DEFAULT NULL
  COMMENT '成交量（股数）',
  `turnrate`   DOUBLE                    DEFAULT NULL
  COMMENT '换手率',
  `ma5`        DOUBLE                    DEFAULT NULL
  COMMENT '5日均线',
  `ma10`       DOUBLE                    DEFAULT NULL
  COMMENT '10日均线',
  `ma20`       DOUBLE                    DEFAULT NULL
  COMMENT '20日均线',
  `ma30`       DOUBLE                    DEFAULT NULL
  COMMENT '30日均线',
  `macd`       DOUBLE                    DEFAULT NULL
  COMMENT 'macd',
  `dif`        DOUBLE                    DEFAULT NULL
  COMMENT 'dif',
  `dea`        DOUBLE                    DEFAULT NULL
  COMMENT 'dea',
  `stock_code` VARCHAR(200)              DEFAULT NULL
  COMMENT '股票编码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_shares_id_thedate` (`stock_id`, `thedate`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3376
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS `stock_price_month`;
CREATE TABLE `stock_price_month` (
  `id`         INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  `stock_id`   INT(11)          NOT NULL
  COMMENT 'stock.id',
  `thedate`    DATE             NOT NULL
  COMMENT '起始日期',
  `pre_date`   DATE                      DEFAULT NULL
  COMMENT '上一个交易日期',
  `next_date`  DATE                      DEFAULT NULL
  COMMENT '下一个交易日期',
  `open`       DOUBLE           NOT NULL DEFAULT '0'
  COMMENT '开盘价格',
  `close`      DOUBLE           NOT NULL DEFAULT '0'
  COMMENT '收盘价格',
  `high`       DOUBLE                    DEFAULT NULL
  COMMENT '最高价格',
  `low`        DOUBLE                    DEFAULT NULL
  COMMENT '最低价格',
  `chg`        DOUBLE                    DEFAULT NULL
  COMMENT '昨日涨跌',
  `percent`    DOUBLE                    DEFAULT NULL
  COMMENT '昨日涨跌率',
  `volume`     BIGINT(11)                DEFAULT NULL
  COMMENT '成交量（股数）',
  `turnrate`   DOUBLE                    DEFAULT NULL
  COMMENT '换手率',
  `ma5`        DOUBLE                    DEFAULT NULL
  COMMENT '5日均线',
  `ma10`       DOUBLE                    DEFAULT NULL
  COMMENT '10日均线',
  `ma20`       DOUBLE                    DEFAULT NULL
  COMMENT '20日均线',
  `ma30`       DOUBLE                    DEFAULT NULL
  COMMENT '30日均线',
  `macd`       DOUBLE                    DEFAULT NULL
  COMMENT 'macd',
  `dif`        DOUBLE                    DEFAULT NULL
  COMMENT 'dif',
  `dea`        DOUBLE                    DEFAULT NULL
  COMMENT 'dea',
  `stock_code` VARCHAR(200)              DEFAULT NULL
  COMMENT '股票编码',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 165
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS `stock_price_week`;
CREATE TABLE `stock_price_week` (
  `id`         INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  `stock_id`   INT(11)          NOT NULL
  COMMENT 'stock.id',
  `thedate`    DATE             NOT NULL
  COMMENT '起始日期',
  `pre_date`   DATE                      DEFAULT NULL
  COMMENT '上一个交易日期',
  `next_date`  DATE                      DEFAULT NULL
  COMMENT '下一个交易日期',
  `open`       DOUBLE           NOT NULL DEFAULT '0'
  COMMENT '开盘价格',
  `close`      DOUBLE           NOT NULL DEFAULT '0'
  COMMENT '收盘价格',
  `high`       DOUBLE                    DEFAULT NULL
  COMMENT '最高价格',
  `low`        DOUBLE                    DEFAULT NULL
  COMMENT '最低价格',
  `chg`        DOUBLE                    DEFAULT NULL
  COMMENT '昨日涨跌',
  `percent`    DOUBLE                    DEFAULT NULL
  COMMENT '昨日涨跌率',
  `volume`     BIGINT(11)                DEFAULT NULL
  COMMENT '成交量（股数）',
  `turnrate`   DOUBLE                    DEFAULT NULL
  COMMENT '换手率',
  `ma5`        DOUBLE                    DEFAULT NULL
  COMMENT '5日均线',
  `ma10`       DOUBLE                    DEFAULT NULL
  COMMENT '10日均线',
  `ma20`       DOUBLE                    DEFAULT NULL
  COMMENT '20日均线',
  `ma30`       DOUBLE                    DEFAULT NULL
  COMMENT '30日均线',
  `macd`       DOUBLE                    DEFAULT NULL
  COMMENT 'macd',
  `dif`        DOUBLE                    DEFAULT NULL
  COMMENT 'dif',
  `dea`        DOUBLE                    DEFAULT NULL
  COMMENT 'dea',
  `stock_code` VARCHAR(200)              DEFAULT NULL
  COMMENT '股票编码',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4454
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;


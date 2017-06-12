USE CSMDB_TENANTX;

/*--------------------------------------------------------
--  DDL for Table REF_EXTERNAL_PLATFORM
--------------------------------------------------------*/
CREATE TABLE `REF_EXTERNAL_PLATFORM` (
	`EXTERNAL_PLATFORM_NAME` VARCHAR(100) NOT NULL COMMENT 'External platform name',
	`EXTERNAL_PLATFORM_DESCRIPTION` VARCHAR(255) COMMENT 'External platform description',
	`CREATED_BY_ID` VARCHAR(100) COMMENT 'ID of creator',
	`CREATED_DATE` DATE COMMENT 'Datetime of creation',
	`UPDATED_BY_ID` VARCHAR(100) COMMENT 'ID of last modifier',
	`UPDATED_DATE` DATE COMMENT 'Datetime of last modification',
	PRIMARY KEY (`EXTERNAL_PLATFORM_NAME`)
	) ENGINE = INNODB COMMENT 'Table for external platform definition';
	
/*--------------------------------------------------------
--  DDL for Table SDP_SOLUTION_OFFER_EXTERNAL_ID
--------------------------------------------------------*/
CREATE TABLE `SDP_SOLUTION_OFFER_EXTERNAL_ID` (
	`SOLUTION_OFFER_ID` BIGINT NOT NULL COMMENT 'Solution offer identifier',
	`EXTERNAL_PLATFORM_NAME` VARCHAR(100) NOT NULL COMMENT 'Identifier of External platform',
	`EXTERNAL_ID` VARCHAR(100) COMMENT 'Identifier for external system',
	CONSTRAINT `PK-SDP_SOLUTION_OFFER_EXTERNAL_ID` PRIMARY KEY (
		`SOLUTION_OFFER_ID`,
		`EXTERNAL_PLATFORM_NAME`
		),
	CONSTRAINT `FK-SDP_SOLUTION_OFFER_EXTERNALID-SOL_OFF_ID` FOREIGN KEY (`SOLUTION_OFFER_ID`) REFERENCES `SDP_SOLUTION_OFFER`(`SOLUTION_OFFER_ID`),
	CONSTRAINT `FK-SDP_SOLUTION_OFFER_EXTERNALID-EXT_PLATFORM_NAME` FOREIGN KEY (`EXTERNAL_PLATFORM_NAME`) REFERENCES `REF_EXTERNAL_PLATFORM`(`EXTERNAL_PLATFORM_NAME`),
	INDEX `IDX-SDP_SOLUTION_OFFER_EXTERNALID-EXTERNAL_ID` (`EXTERNAL_ID`)
	) ENGINE = INNODB COMMENT 'Table for external id of solution offers';

/*--------------------------------------------------------
--  DDL to alter Table SDP_SOLUTION_OFFER
--------------------------------------------------------*/
ALTER TABLE `SDP_SOLUTION_OFFER`
	DROP COLUMN `EXTERNAL_ID`, 
	DROP INDEX `UK-SDP_SOLTN_OFFER-EXTERNAL_ID`;


/*--------------------------------------------------------
--  POPULATE TABLES
--------------------------------------------------------*/
INSERT INTO REF_EXTERNAL_PLATFORM (EXTERNAL_PLATFORM_NAME, EXTERNAL_PLATFORM_DESCRIPTION, CREATED_BY_ID, CREATED_DATE) VALUES ('PlayStore', 'Android Store', 'Configurator', curdate());
INSERT INTO REF_EXTERNAL_PLATFORM (EXTERNAL_PLATFORM_NAME, EXTERNAL_PLATFORM_DESCRIPTION, CREATED_BY_ID, CREATED_DATE) VALUES ('AppleStore', 'Apple Store', 'Configurator', curdate());


/*-----------------------
 *-- CRICKET AUSTRALIA --
 *-----------------------*/
/*--------------------------------------------------------
--  DDL for Table REF_SOLUTION_OFFER_TYPE
--------------------------------------------------------*/
CREATE TABLE `REF_SOLUTION_OFFER_TYPE` (
	`SOLUTION_OFFER_TYPE_NAME` VARCHAR(50) NOT NULL COMMENT 'Solution Offer typology name',
	`SOLUTION_OFFER_TYPE_DESCRIPTION` VARCHAR(100) COMMENT 'Solution Offer typology description',
	PRIMARY KEY (`SOLUTION_OFFER_TYPE_NAME`)
) ENGINE = INNODB COMMENT 'Ref table for Solution Offer types';

INSERT INTO REF_SOLUTION_OFFER_TYPE (SOLUTION_OFFER_TYPE_NAME, SOLUTION_OFFER_TYPE_DESCRIPTION) VALUES
	('Default', 'Default Commercial Package'),
	('Membership', 'Membership Commercial Package'),
	('Mobile', 'Mobile Commercial Package'),
	('Online', 'Web Commercial Package');


ALTER TABLE `sdp_solution_offer`
ADD COLUMN `DURATION` BIGINT(20) NULL DEFAULT NULL AFTER `IS_BASIC_PROFILE` , 
ADD COLUMN `SOLUTION_OFFER_TYPE` VARCHAR(100) NULL DEFAULT NULL  AFTER `DURATION`, 
ADD CONSTRAINT `FK-SDP_SOLUTION_OFFER-SOTYPE` FOREIGN KEY (`SOLUTION_OFFER_TYPE`) REFERENCES `REF_SOLUTION_OFFER_TYPE` (`SOLUTION_OFFER_TYPE_NAME`);


/*--------------------------------------------------------
--  DDL for Table SDP_VOUCHER_CAMPAIGN
--------------------------------------------------------*/
CREATE TABLE `SDP_VOUCHER_CAMPAIGN` (
	`VOUCHER_TYPE` VARCHAR(50) NOT NULL COMMENT 'Identifier of promotion',
	`SOLUTION_OFFER_ID` BIGINT DEFAULT NULL COMMENT 'Solution Offer identifier',
	`VALIDITY_PERIOD` BIGINT DEFAULT NULL COMMENT 'Validity of promotion in days',
	`START_DATE` DATETIME COMMENT 'Validity start date',
	`END_DATE` DATETIME COMMENT 'Validity end date',
	`CREATED_BY_ID` VARCHAR(100) DEFAULT NULL COMMENT 'ID of creator',
	`CREATED_DATE` DATETIME DEFAULT NULL COMMENT 'Datetime of creation',
	`UPDATED_BY_ID` VARCHAR(100) DEFAULT NULL COMMENT 'ID of last modifier',
	`UPDATED_DATE` DATETIME DEFAULT NULL COMMENT 'Datetime of last modification',
	PRIMARY KEY (`VOUCHER_TYPE`),
	KEY `FK-VOUCHER-SOLUTION_OFFER_ID`(`SOLUTION_OFFER_ID`),
	CONSTRAINT `FK-VOUCHER-SOLUTION_OFFER_ID` FOREIGN KEY (`SOLUTION_OFFER_ID`) REFERENCES `SDP_SOLUTION_OFFER`(`SOLUTION_OFFER_ID`)
	) ENGINE = InnoDB COMMENT = 'Table for console voucher campaign information';
	

/*--------------------------------------------------------
--  DDL for Table SDP_VOUCHER---
--------------------------------------------------------*/
DROP TABLE SDP_VOUCHER;

CREATE TABLE `SDP_VOUCHER` (
	`VOUCHER_ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Voucher identifier',
	`VOUCHER_CODE` VARCHAR(50) NOT NULL COMMENT 'Code of voucher',
	`VOUCHER_TYPE` VARCHAR(50) NOT NULL COMMENT 'Identifier of promotion',
	`PARTY_ID` BIGINT DEFAULT NULL COMMENT 'Identifier if Party (voucher is used)',
	`CREATED_BY_ID` VARCHAR(100) DEFAULT NULL COMMENT 'ID of creator',
	`CREATED_DATE` DATETIME DEFAULT NULL COMMENT 'Datetime of creation',
	`UPDATED_BY_ID` VARCHAR(100) DEFAULT NULL COMMENT 'ID of last modifier',
	`UPDATED_DATE` DATETIME DEFAULT NULL COMMENT 'Datetime of last modification',
	PRIMARY KEY (`VOUCHER_ID`),
	UNIQUE KEY `UK-SDP_VOUCHER-VOUCHER_CODE`(`VOUCHER_CODE`),
	KEY `FK-SDP_VOUCHER-PARTY_ID`(`PARTY_ID`),
	KEY `FK-SDP_VOUCHER-SOLOFFER_ID`(`VOUCHER_TYPE`),
	CONSTRAINT `FK-SDP_VOUCHER-TYPE` FOREIGN KEY (`VOUCHER_TYPE`) REFERENCES `SDP_VOUCHER_CAMPAIGN`(`VOUCHER_TYPE`)
	) ENGINE = InnoDB COMMENT = 'Table for console voucher information';
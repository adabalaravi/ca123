CREATE DATABASE CSMDB_CONF;
ALTER SCHEMA CSMDB_CONF DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE CSMDB_CONF;

/*--------------------------------------------------------
--  DDL for Table REF_STATUS_TYPE
--------------------------------------------------------*/

  CREATE TABLE `REF_STATUS_TYPE` 
   (	`STATUS_ID` BIGINT NOT NULL COMMENT 'Status identifier', 
	`STATUS_NAME` VARCHAR(100 ) NOT NULL COMMENT 'Status name', 
	`STATUS_DESCRIPTION` VARCHAR(255 ) COMMENT 'Status description', 
	`CREATED_BY_ID` VARCHAR(100) COMMENT 'ID of creator', 
	`CREATED_DATE` DATETIME COMMENT 'Datetime of creation', 
	`UPDATED_BY_ID` VARCHAR(100) COMMENT 'ID of last modifier', 
	`UPDATED_DATE` DATETIME COMMENT 'Datetime of last modification'
   )
   ENGINE=InnoDB
   COMMENT 'Ref table for status value type'
   ;

  ALTER TABLE `REF_STATUS_TYPE` ADD UNIQUE INDEX `UK-REF_STATUS_TYPE-STATUS_NAME`  (`STATUS_NAME`);
  
  ALTER TABLE `REF_STATUS_TYPE` ADD PRIMARY KEY (`STATUS_ID`);

/*--------------------------------------------------------
--  DDL for Table REF_PARTY_TYPE
--------------------------------------------------------*/

  CREATE TABLE `REF_PARTY_TYPE` 
   (	`PARTY_TYPE_ID` BIGINT NOT NULL COMMENT 'Party typology identifier', 
	`PARTY_TYPE_NAME` VARCHAR(100 ) NOT NULL COMMENT 'Party typology name', 
	`PARTY_TYPE_DESCRIPTION` VARCHAR(255 ) COMMENT 'Party typology description', 	
	`CREATED_BY_ID` VARCHAR(100) COMMENT 'ID of creator', 
	`CREATED_DATE` DATE COMMENT 'Datetime of creation', 
	`UPDATED_BY_ID` VARCHAR(100) COMMENT 'ID of last modifier', 
	`UPDATED_DATE` DATE COMMENT 'Datetime of last modification'
   ) 
   ENGINE=InnoDB
   COMMENT 'Ref table for party typologies (Organization,User)'
  ;

  ALTER TABLE `REF_PARTY_TYPE`
  ADD UNIQUE INDEX `UK-REF_PARTY_TYPE-PARTYTPNAME` (`PARTY_TYPE_NAME`)   
  ;

  ALTER TABLE `REF_PARTY_TYPE` ADD PRIMARY KEY (`PARTY_TYPE_ID`)
    ;
 /*--------------------------------------------------------
--  DDL for Table REF_CSM_CONSTANTS
--------------------------------------------------------*/

  CREATE TABLE `REF_CSM_CONSTANTS` 
   (	`CONSTANT_KEY` VARCHAR(50) NOT NULL COMMENT 'Unique constant name', 
	`CONSTANT_VALUE` VARCHAR(50) NOT NULL COMMENT 'Constant value')
   ENGINE=InnoDB	
   COMMENT 'Ref table for constant values used into the code'
   ;
   
  ALTER TABLE `REF_CSM_CONSTANTS` ADD PRIMARY KEY (`CONSTANT_KEY`);

/*--------------------------------------------------------
--  DDL for Table REF_OPERATOR_RESOURCE
--------------------------------------------------------*/

CREATE TABLE REF_OPERATOR_RESOURCE
( OPERATOR_RESOURCE_ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Console identifier',
  OPERATOR_RESOURCE_NAME VARCHAR(100) NOT NULL COMMENT 'Console name',
  OPERATOR_RESOURCE_DESCRIPTION VARCHAR(256) NOT NULL COMMENT 'Console description',
  CREATED_BY_ID VARCHAR(100) COMMENT 'ID of creator', 
  CREATED_DATE DATETIME COMMENT 'Datetime of creation', 
  UPDATED_BY_ID VARCHAR(100) COMMENT 'ID of last modifier', 
  UPDATED_DATE DATETIME COMMENT 'Datetime of last modification',
  CONSTRAINT `PK-REF_OPERATOR_RESOURCE` PRIMARY KEY (`OPERATOR_RESOURCE_ID`),
  CONSTRAINT `UK-REF_OPERATOR_RESOURCE-OPERATOR_RESOURCE_NAME` UNIQUE (`OPERATOR_RESOURCE_NAME`)
)
ENGINE=InnoDB
COMMENT 'Ref table for consoles'
;

/*--------------------------------------------------------
--  DDL for Table SDP_OPERATOR
--------------------------------------------------------*/

CREATE TABLE SDP_OPERATOR
( USERNAME VARCHAR(100) NOT NULL COMMENT 'Username for console operator',
  FIRST_NAME VARCHAR(256) NOT NULL COMMENT 'FirstName for console operator',
  LAST_NAME VARCHAR(256) NOT NULL COMMENT 'LastName for console operator',
  PASSWORD VARCHAR(256) NOT NULL COMMENT 'Password for console operator',
  EMAIL VARCHAR(100) COMMENT 'email for console operator',
  STATUS_ID BIGINT DEFAULT 2 NOT NULL COMMENT 'Status identifier', 
  CREATED_BY_ID VARCHAR(100) COMMENT 'ID of creator', 
  CREATED_DATE DATETIME COMMENT 'Datetime of creation', 
  UPDATED_BY_ID VARCHAR(100) COMMENT 'ID of last modifier', 
  UPDATED_DATE DATETIME COMMENT 'Datetime of last modification',
  CHG_STATUS_BY_ID VARCHAR(100) COMMENT 'ID of last STATUS modifier', 
  CHG_STATUS_DATE DATETIME COMMENT 'Datetime of last STATUS modification'
)
ENGINE=InnoDB
COMMENT 'Table for console operators information'
;

ALTER TABLE `SDP_OPERATOR` ADD PRIMARY KEY (USERNAME) 
;

ALTER TABLE `SDP_OPERATOR` ADD CONSTRAINT `FK-SDP_OPERATOR-STATUS_ID` FOREIGN KEY (`STATUS_ID`)
REFERENCES `REF_STATUS_TYPE` (`STATUS_ID`) ;


/*--------------------------------------------------------
--  DDL for Table REF_TENANT
--------------------------------------------------------*/

CREATE TABLE `REF_TENANT` (
  `TENANT_NAME` varchar(100) NOT NULL,
  `TENANT_DESCRIPTION` varchar(256) DEFAULT NULL,
  `PERSISTENCE_UNIT` varchar(50) NOT NULL,
  `METERING_PERSISTENCE_UNIT` varchar(50) NOT NULL,
  `STATUS_ID` bigint(20) NOT NULL,
  `CREATED_BY_ID` varchar(100) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_BY_ID` varchar(100) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `CHG_STATUS_BY_ID` varchar(100) DEFAULT NULL,
  `CHG_STATUS_DATE` datetime DEFAULT NULL,
  CONSTRAINT `PK-REF_TENANT` PRIMARY KEY (`TENANT_NAME`),
  UNIQUE KEY `UK-REF_TENANT-PERSISTENCE_UNIT` (`PERSISTENCE_UNIT`),
  KEY `FK-REF_TENANT-STATUS_ID` (`STATUS_ID`),
  CONSTRAINT `FK-REF_TENANT-STATUS_ID` FOREIGN KEY (`STATUS_ID`) REFERENCES `REF_STATUS_TYPE` (`STATUS_ID`)
) ENGINE=InnoDB ;


/*--------------------------------------------------------
--  DDL for Table LNK_TENANT_OPERATOR
--------------------------------------------------------*/

  CREATE TABLE `LNK_TENANT_OPERATOR` 
   (	`USERNAME` VARCHAR(100) NOT NULL COMMENT 'Operator identifier', 
		`TENANT_NAME` VARCHAR(100) NOT NULL COMMENT 'Tenant identifier',
	CONSTRAINT `PK-LNK_TENANT_OPERATOR` PRIMARY KEY (`USERNAME`,`TENANT_NAME`),
	CONSTRAINT `FK-LNK_TENANT_OPERATOR-USERNAME` FOREIGN KEY (`USERNAME`) REFERENCES `SDP_OPERATOR` (`USERNAME`),
	CONSTRAINT `FK-LNK_TENANT_OPERATOR-TENANT_NAME` FOREIGN KEY (`TENANT_NAME`) REFERENCES `REF_TENANT` (`TENANT_NAME`)
   )
   ENGINE=InnoDB
   COMMENT 'Table for mapping between tenant and operators';
	
/*--------------------------------------------------------
--  DDL for Table SDP_OPERATOR_RIGHT
--------------------------------------------------------*/

CREATE TABLE `SDP_OPERATOR_RIGHT` (
  `OPERATOR_RIGHT_ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Role id',
  `OPERATOR_RIGHT_NAME` VARCHAR(100) NOT NULL COMMENT 'Role name',
  `OPERATOR_RIGHT_DESCRIPTION` VARCHAR(255) COMMENT 'Role description',
  `OPERATOR_RESOURCE_ID` BIGINT NOT NULL COMMENT 'Console identifier (ref REF_OPERATOR_RESOURCE)',
  `CREATED_BY_ID` VARCHAR(100) COMMENT 'ID of creator',
  `CREATED_DATE` DATE COMMENT 'Datetime of creation',
  `UPDATED_BY_ID` VARCHAR(100) COMMENT 'ID of last modifier',
  `UPDATED_DATE` DATE COMMENT 'Datetime of last modification',
  CONSTRAINT `PK-SDP_OPERATOR_RIGHT` PRIMARY KEY (`OPERATOR_RIGHT_ID`),
  CONSTRAINT `FK-SDP_OPERATOR_RIGHT-OPERATOR_RESOURCE_ID` FOREIGN KEY (`OPERATOR_RESOURCE_ID`) REFERENCES `REF_OPERATOR_RESOURCE` (`OPERATOR_RESOURCE_ID`),
  UNIQUE KEY `UK-SDP_OPERATOR_RIGHT-RESOURCE_ID_RIGHT_NAME` (`OPERATOR_RIGHT_NAME`,`OPERATOR_RESOURCE_ID`)
) ENGINE=InnoDB COMMENT='Table for rights definition';

    
/*--------------------------------------------------------
--  DDL for Table LNK_OPERATOR_OPERATOR_RIGHT
--------------------------------------------------------*/

  CREATE TABLE `LNK_OPERATOR_OPERATOR_RIGHT` 
   (	`USERNAME` varchar(100) NOT NULL COMMENT 'Operator identifier', 
		`OPERATOR_RIGHT_ID` BIGINT NOT NULL COMMENT 'Operator right identifier',
	CONSTRAINT `PK-LNK_OPERATOR_OPERATOR_RIGHT` PRIMARY KEY (`USERNAME`,`OPERATOR_RIGHT_ID`),
	KEY `FK-LNK_OPERATOR_OPERATOR_RIGHT-USERNAME_idx` (`USERNAME`),
	CONSTRAINT `FK-LNK_OPERATOR_OPERATOR_RIGHT-OPERATOR_RIGHT_ID` FOREIGN KEY (`OPERATOR_RIGHT_ID`) REFERENCES `SDP_OPERATOR_RIGHT` (`OPERATOR_RIGHT_ID`)
   )
   ENGINE=InnoDB COMMENT 'Table for mapping between operators and rights';



   
/*--------------------------------------------------------
--  POPULATE TABLES
--------------------------------------------------------*/
   
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (1,'Activating','Activating','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (2,'Active','Active','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (3,'Suspended','Suspended','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (4,'Inactive','Inactive','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (5,'Deleted','Deleted','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (6,'FailedOnActivation','FailedOnActivation','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (7,'FailedOnSuspension','FailedOnSuspension','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (8,'FailedOnInactivation','FailedOnInactivation','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (9,'Updating','Updating','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (10,'Suspending','Suspending','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (11,'Inactivating','Inactivating','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (12,'Deleting','Deleting','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (13,'FailedOnUpdating','FailedOnUpdating','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (14,'FailedOnDeletion','FailedOnDeletion','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (15,'New','New','Configurator',curdate());
Insert into REF_STATUS_TYPE (STATUS_ID,STATUS_NAME,STATUS_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (16,'CheckedOut','CheckedOut','Configurator',curdate());

Insert into REF_PARTY_TYPE (PARTY_TYPE_ID,PARTY_TYPE_NAME,PARTY_TYPE_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (1,'Organization','Organization','Configurator',curdate());
Insert into REF_PARTY_TYPE (PARTY_TYPE_ID,PARTY_TYPE_NAME,PARTY_TYPE_DESCRIPTION,CREATED_BY_ID,CREATED_DATE) values (2,'User','User','Configurator',curdate());

Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('ACTIVATING','1');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('ACTIVE','2');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('SUSPENDED','3');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('INACTIVE','4');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('DELETED','5');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('FAILEDONACTIVATION','6');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('FAILEDONSUSPENSION','7');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('FAILEDONINACTIVATION','8');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('UPDATING','9');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('SUSPENDING','10');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('INACTIVATING','11');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('DELETING','12');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('FAILEDONUPDATING','13');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('FAILEDONDELETION','14');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('NEW','15');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('CHECKEDOUT','16');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('PARTY_ORGANIZATION','1');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('PARTY_USER','2');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('ROLE_ADMIN','Administrator');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('ROLE_USER','User');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('NULL_FREQUENCY','1');
Insert into REF_CSM_CONSTANTS (CONSTANT_KEY,CONSTANT_VALUE) values ('IS_METERING_ENABLED','FALSE');

-- OPERATORS
INSERT INTO `REF_OPERATOR_RESOURCE` (`OPERATOR_RESOURCE_ID`,`OPERATOR_RESOURCE_NAME`,`OPERATOR_RESOURCE_DESCRIPTION`,`CREATED_BY_ID`,`CREATED_DATE`,`UPDATED_BY_ID`,`UPDATED_DATE`) VALUES 
('1', 'ASSURANCE_CONSOLE', 'Customers', 'Configurator', curdate(), NULL, NULL),
('2', 'CATALOGUE_CONSOLE', 'Catalogue', 'Configurator', curdate(), NULL, NULL),
('3', 'CATCHUP_CONSOLE', 'Catchup', 'Configurator', curdate(), NULL, NULL),
('4', 'MULTICAMERA_CONSOLE', 'Multicamera', 'Configurator', curdate(), NULL, NULL),
('5', 'OPERATORS_CONSOLE', 'Operators', 'Configurator', curdate(), NULL, NULL);

INSERT INTO `SDP_OPERATOR_RIGHT` (`OPERATOR_RIGHT_ID`,`OPERATOR_RIGHT_NAME`,`OPERATOR_RIGHT_DESCRIPTION`,`OPERATOR_RESOURCE_ID`,`CREATED_BY_ID`,`CREATED_DATE`,`UPDATED_BY_ID`,`UPDATED_DATE`) VALUES
('1', 'READ', NULL, '1', 'Configurator', curdate(), NULL, NULL),
('2', 'WRITE', NULL, '1', 'Configurator', curdate(), NULL, NULL),
('3', 'READ', NULL, '2', 'Configurator', curdate(), NULL, NULL),
('4', 'WRITE', NULL, '2', 'Configurator', curdate(), NULL, NULL),
('5', 'READ', NULL, '3', 'Configurator', curdate(), NULL, NULL),
('6', 'WRITE', NULL, '3', 'Configurator', curdate(), NULL, NULL),
('7', 'READ', NULL, '4', 'Configurator', curdate(), NULL, NULL),
('8', 'WRITE', NULL, '4', 'Configurator', curdate(), NULL, NULL),
('9', 'READ', NULL, '5', 'Configurator', curdate(), NULL, NULL),
('10', 'WRITE', NULL, '5', 'Configurator', curdate(), NULL, NULL);

INSERT INTO `SDP_OPERATOR` VALUES ('user', 'user', 'user', 'v9d3BgRY1pXvEfFBirh9jQ==', 'user@avs.com', 2, 'Configurator', curdate(), NULL, NULL, NULL, NULL);
INSERT INTO `LNK_OPERATOR_OPERATOR_RIGHT` VALUES ('user',1),('user',2),('user',3),('user',4),('user',5),('user',6),('user',7),('user',8),('user',9),('user',10);


commit;

use CSMDB_CONF;

INSERT INTO REF_TENANT (TENANT_NAME,TENANT_DESCRIPTION,PERSISTENCE_UNIT,METERING_PERSISTENCE_UNIT,STATUS_ID,CREATED_BY_ID,CREATED_DATE)
VALUES ('tenantX', 'tenantX', UCASE('SDP_TENANTX_PU'), UCASE('SDP_TENANTX_MPU'), 2, 'Configurator', curdate());

INSERT INTO `LNK_TENANT_OPERATOR` VALUES ('user','tenantX');

commit;
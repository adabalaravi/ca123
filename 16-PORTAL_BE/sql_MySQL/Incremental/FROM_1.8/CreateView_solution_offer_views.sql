create or replace view avs_be.sdp_solution_offer_view as select so.SOLUTION_OFFER_ID AS SOLUTION_OFFER_ID,so.PARENT_SOLUTION_OFFER_ID AS PARENT_SOLUTION_OFFER_ID,so.SOLUTION_ID AS SOLUTION_ID,so.SOLUTION_OFFER_NAME AS SOLUTION_OFFER_NAME,so.SOLUTION_OFFER_DESCRIPTION AS SOLUTION_OFFER_DESCRIPTION,so.STATUS_ID AS STATUS_ID,so.SOLUTION_OFFER_PROFILE AS SOLUTION_OFFER_PROFILE,NULL AS EXTERNAL_ID,so.START_DATE AS START_DATE,so.END_DATE AS END_DATE,so.CREATED_BY_ID AS CREATED_BY_ID,so.CREATED_DATE AS CREATED_DATE,so.UPDATED_BY_ID AS UPDATED_BY_ID,so.UPDATED_DATE AS UPDATED_DATE,so.DELETED_BY_ID AS DELETED_BY_ID,so.DELETED_DATE AS DELETED_DATE,so.CHG_STATUS_BY_ID AS CHG_STATUS_BY_ID,so.CHG_STATUS_DATE AS CHG_STATUS_DATE,so.IS_BASIC_PROFILE AS IS_BASIC_PROFILE,so.DURATION AS DURATION,so.SOLUTION_OFFER_TYPE AS SOLUTION_OFFER_TYPE from csmdb_tenant_1.sdp_solution_offer_view so;

create or replace view sdp_solution_offer_external_id_view as select sdp_solution_offer_external_id.SOLUTION_OFFER_ID AS SOLUTION_OFFER_ID,sdp_solution_offer_external_id.EXTERNAL_PLATFORM_NAME AS EXTERNAL_PLATFORM_NAME,sdp_solution_offer_external_id.EXTERNAL_ID AS EXTERNAL_ID from csmdb_tenant_1.sdp_solution_offer_external_id;
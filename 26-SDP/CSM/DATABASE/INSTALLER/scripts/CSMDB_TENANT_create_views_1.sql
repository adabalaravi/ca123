USE CSMDB_TENANTX;

-- SDP VIEWS TO AVS
create or replace view LNK_PARTY_PARTY_GROUP_VIEW as select * from LNK_PARTY_PARTY_GROUP;
create or replace view REF_CURRENCY_TYPE_VIEW as select * from REF_CURRENCY_TYPE;
create or replace view REF_FREQUENCY_TYPE_VIEW as select * from REF_FREQUENCY_TYPE;
create or replace view REF_PARTY_GROUP_VIEW as select * from REF_PARTY_GROUP;
create or replace view REF_PRICE_CATALOG_VIEW as select * from REF_PRICE_CATALOG;
create or replace view REF_SOLUTION_TYPE_VIEW as select * from REF_SOLUTION_TYPE;
create or replace view SDP_DISCOUNT_VIEW as select * from SDP_DISCOUNT;
create or replace view SDP_OFFER_VIEW as select * from SDP_OFFER;
create or replace view SDP_PACKAGE_GROUP_VIEW as select * from SDP_PACKAGE_GROUP;
create or replace view SDP_PACKAGE_PRICE_VIEW as select * from SDP_PACKAGE_PRICE;
create or replace view SDP_PACKAGE_VIEW as select * from SDP_PACKAGE;
create or replace view SDP_SOLUTION_OFFER_VIEW as select * from SDP_SOLUTION_OFFER;
create or replace view SDP_SOLUTION_VIEW as select * from SDP_SOLUTION;
create or replace view SDP_VOUCHER_VIEW as select * from SDP_VOUCHER;

commit;
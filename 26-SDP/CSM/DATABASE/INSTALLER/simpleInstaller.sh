#!/bin/sh

source ./init.cfg

if [ $# -lt 2 ]
then
	echo
	echo "wrong number of parameter"
	echo
	echo "USAGE: $0 <SERVER_IP> <TENANT_NAME...> "
	echo
	echo " PARAMETER description:"
	echo
	echo "SERVER_IP: the ip address for CSMDB access grant"
	echo "TENANT_NAME...: whitespace separated list of TENANT_NAME schema names"
	echo
	echo " EXAMPLE of usage:"
	echo "$0 % TENANT1 TENANT2"
	echo
	exit 1
fi

NUMBER_OF_TENANT=`expr $# - 1`
TENANT_INDEX=1
TENANT_NAMEs=("$@")

echo "CONFIGURING CSMDB_CONF"
./installer.sh INSTALL_CSMDB_CONF
./installer.sh GRANT_CSMDB_CONF $1

while [ "$TENANT_INDEX" -le "$NUMBER_OF_TENANT" ]
do
	echo "CONFIGURING ${TENANT_NAMEs[${TENANT_INDEX}]}"
	./installer.sh INSTALL_CSMDB_TENANT ${TENANT_NAMEs[${TENANT_INDEX}]}
	./installer.sh POPULATE_TENANT_CONF ${TENANT_NAMEs[${TENANT_INDEX}]}
	./installer.sh GRANT_CSMDB_TENANT ${TENANT_NAMEs[${TENANT_INDEX}]} $1
#	./installer.sh INSTALL_METERING_TENANT ${TENANT_NAMEs[${TENANT_INDEX}]}
#	./installer.sh GRANT_METERING_TENANT ${TENANT_NAMEs[${TENANT_INDEX}]} $1
	./installer.sh CREATE_TENANT_VIEWS_1 $TENANT_INDEX ${TENANT_NAMEs[${TENANT_INDEX}]}
	((TENANT_INDEX++))
done

exit 0
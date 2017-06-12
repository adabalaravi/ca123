#!/bin/sh

source ./init.cfg

if [ -z "$1" ]
then
	echo 
	echo "wrong number of parameter"
	echo ""
	echo "USAGE: $0 <MODE> "
	echo 
	echo " MODE values:"
	echo 
	echo "INSTALL_CSMDB_TENANT: to install CSMDB TENANT"
	echo "INSTALL_CSMDB_CONF: to install CSMDB CONF"
	echo "UNINSTALL_CSMDB_TENANT: to uninstall CSMDB TENANT"
    echo "UNINSTALL_CSMDB_CONF: to uninstall CSMDB CONF"
	echo "GRANT_CSMDB_TENANT: to give CSM_USER grant to CSMDB TENANT DB to external servers"
    echo "GRANT_CSMDB_CONF: to give CSM_USER grant to CSMDB CONF DB to external servers"
	echo "POPULATE_TENANT_CONF: populate TENANT CONF table"
	echo "CREATE_TENANT_VIEWS_1: create views from AVS_BE"
	echo "CREATE_TENANT_VIEWS_2: create views towards AVS_BE"
	echo 
	exit 1
fi

FILE_CREATE_TENANT=./scripts/CSMDB_TENANT_create.sql
FILE_GRANT_TENANT=./scripts/CSMDB_TENANT_grant.sql

FILE_CREATE_CONF=./scripts/CSMDB_CONF_create.sql
FILE_GRANT_CONF=./scripts/CSMDB_CONF_grant.sql

FILE_POPULATE_TENANT_CONF=./scripts/CSMDB_CONF_populate_tenant.sql
FILE_CREATE_TENANT_VIEWS_1=./scripts/CSMDB_TENANT_create_views_1.sql
FILE_CREATE_TENANT_VIEWS_2=./scripts/CSMDB_TENANT_create_views_2.sql

FILE_CREATE_METERING_TENANT=./scripts/METERING_TENANT_create.sql
FILE_GRANT_METERING_TENANT=./scripts/METERING_TENANT_grant.sql

FILE_CUSTOMER_TENANT=./scripts/CUSTOMER_TENANT_diff.sql
FILE_CUSTOMER_TENANT_VIEWS=./scripts/CUSTOMER_TENANT_VIEWS_diff.sql

mkdir -p ./logs

if [ "$1" = "INSTALL_CSMDB_TENANT" ]
then
	if [ $# -ne 2 ]
	then
		echo "wrong number of parameter"
		echo "USAGE: $0 INSTALL_CSMDB_TENANT <TENANT_NAME>"
		echo "<TENANT_NAME> is the name of TENANT to install"
		exit 1
	fi
	sed 's/CSMDB_TENANTX/CSMDB_TENANT_'"$2"'/g' $FILE_CREATE_TENANT > ./scripts/temp.sql
	nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch < ./scripts/temp.sql > ./logs/create.log
	rm -rf ./scripts/temp.sql
	sed 's/CSMDB_TENANTX/CSMDB_TENANT_'"$2"'/g' $FILE_CUSTOMER_TENANT > ./scripts/temp.sql
	nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch < ./scripts/temp.sql >> ./logs/create.log
	rm -rf ./scripts/temp.sql
fi

if [ "$1" = "INSTALL_CSMDB_CONF" ]
then
	nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch < $FILE_CREATE_CONF > ./logs/create.log
fi

if [ "$1" = "UNINSTALL_CSMDB_TENANT" ]
then
	if [ $# -ne 2 ]
	then
		echo "wrong number of parameter"
		echo "USAGE: $0 UNINSTALL_CSMDB_TENANT <TENANT_NAME>"
		echo "<TENANT_NAME> is the name of TENANT to install"
		exit 1
	fi
	echo "use CSMDB_TENANT$2; drop database CSMDB_TENANT$2;" > ./scripts/temp.sql
	nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch --force < ./scripts/temp.sql 
	rm -rf ./scripts/temp.sql
fi

if [ "$1" = "UNINSTALL_CSMDB_CONF" ]
then
	echo "use CSMDB_CONF; drop database CSMDB_CONF;" > ./scripts/temp.sql
	nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch --force < ./scripts/temp.sql
	rm -rf ./scripts/temp.sql
fi

if [ "$1" = "GRANT_CSMDB_CONF" ]
then
        if [ $# -ne 2 ]
        then
                echo "wrong number of parameter"
                echo "USAGE: $0 GRANT_CSMDB_CONF <SERVER_IP>"
                echo "SERVER_IP is the ip address for CSMDB access grant"
                exit 1
        fi
        sed  's/SERVER_IP/'"$2"'/g' $FILE_GRANT_CONF > ./scripts/temp.sql
        nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch --force < ./scripts/temp.sql > ./logs/grant.log
        rm -rf ./scripts/temp.sql
fi

if [ "$1" = "GRANT_CSMDB_TENANT" ]
then
        if [ $# -ne 3 ]
        then
                echo "wrong number of parameter"
                echo "USAGE: $0 GRANT_CSMDB_TENANT <TENANT_NAME> <SERVER_IP>"
				echo "TENANT_NAME is the name of TENANT to grant to"
                echo "SERVER_IP is the ip address for CSMDB access grant"
                exit 1
        fi
		sed 's/CSMDB_TENANTX/CSMDB_TENANT_'"$2"'/g' $FILE_GRANT_TENANT > ./scripts/temp.sql
        sed -i 's/SERVER_IP/'"$3"'/g' ./scripts/temp.sql
        nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch --force < ./scripts/temp.sql > ./logs/grant.log
        rm -rf ./scripts/temp.sql
fi

if [ "$1" = "POPULATE_TENANT_CONF" ]
then
        if [ $# -ne 2 ]
        then
                echo "wrong number of parameter"
                echo "USAGE: $0 POPULATE_TENANT_CONF <TENANT_NAME>"
                echo "TENANT_NAME is the name of TENANT to grant to"
                exit 1
        fi
        sed  's/tenantX/tenant_'"$2"'/g' $FILE_POPULATE_TENANT_CONF > ./scripts/temp.sql
        sed  -i 's/SDP_TENANTX_PU/SDP_TENANT_'"$2"'_PU/g' ./scripts/temp.sql
        sed  -i 's/SDP_TENANTX_MPU/SDP_TENANT_'"$2"'_MPU/g' ./scripts/temp.sql
        nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch --force < ./scripts/temp.sql > ./logs/populate.log
        rm -rf ./scripts/temp.sql
fi

if [ "$1" = "CREATE_TENANT_VIEWS_1" ]
then
        if [ $# -ne 3 ]
        then
                echo "wrong number of parameter"
                echo "USAGE: $0 CREATE_TENANT_VIEWS_1 <TENANT_NAME> <AVS_BE>"
				echo "TENANT_NAME is the name of TENANT to grant to"
                echo "AVS_BE is the name of the schema of AVS back end"
                exit 1
        fi
		sed 's/CSMDB_TENANTX/CSMDB_TENANT_'"$2"'/g' $FILE_CREATE_TENANT_VIEWS_1 > ./scripts/temp.sql
        sed -i 's/AVS_BE/'"$3"'/g' ./scripts/temp.sql
        nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch --force < ./scripts/temp.sql > ./logs/views.log
        rm -rf ./scripts/temp.sql
		sed 's/CSMDB_TENANTX/CSMDB_TENANT_'"$2"'/g' $FILE_CUSTOMER_TENANT_VIEWS > ./scripts/temp.sql
		nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch < ./scripts/temp.sql >> ./logs/create.log
		rm -rf ./scripts/temp.sql        
fi

if [ "$1" = "CREATE_TENANT_VIEWS_2" ]
then
        if [ $# -ne 3 ]
        then
                echo "wrong number of parameter"
                echo "USAGE: $0 CREATE_TENANT_VIEWS_2 <TENANT_NAME> <AVS_BE>"
				echo "TENANT_NAME is the name of TENANT to grant to"
                echo "AVS_BE is the name of the schema of AVS back end"
                exit 1
        fi
		sed 's/CSMDB_TENANTX/CSMDB_TENANT_'"$2"'/g' $FILE_CREATE_TENANT_VIEWS_2 > ./scripts/temp.sql
        sed -i 's/AVS_BE/'"$3"'/g' ./scripts/temp.sql
        nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch --force < ./scripts/temp.sql > ./logs/views.log
        rm -rf ./scripts/temp.sql
fi


if [ "$1" = "INSTALL_METERING_TENANT" ]
then
	if [ $# -ne 2 ]
	then
		echo "wrong number of parameter"
		echo "USAGE: $0 INSTALL_CSMDB_TENANT <TENANT_NAME>"
		echo "<TENANT_NAME> is the name of TENANT to install"
		exit 1
	fi
	sed 's/METERING_TENANTX/METERING_TENANT_'"$2"'/g' $FILE_CREATE_METERING_TENANT > ./scripts/temp.sql
	nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch < ./scripts/temp.sql > ./logs/create.log
	rm -rf ./scripts/temp.sql
fi

if [ "$1" = "GRANT_METERING_TENANT" ]
then
        if [ $# -ne 3 ]
        then
                echo "wrong number of parameter"
                echo "USAGE: $0 GRANT_METERING_TENANT <TENANT_NAME> <SERVER_IP>"
				echo "TENANT_NAME is the name of TENANT to grant to"
                echo "SERVER_IP is the ip address for CSMDB access grant"
                exit 1
        fi
		sed 's/METERING_TENANTX/METERING_TENANT_'"$2"'/g' $FILE_GRANT_METERING_TENANT > ./scripts/temp.sql
        sed -i 's/SERVER_IP/'"$3"'/g' ./scripts/temp.sql
        nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch --force < ./scripts/temp.sql > ./logs/grant.log
        rm -rf ./scripts/temp.sql
fi

if [ "$1" = "UNINSTALL_METERING_TENANT" ]
then
	if [ $# -ne 2 ]
	then
		echo "wrong number of parameter"
		echo "USAGE: $0 UNINSTALL_CSMDB_TENANT <TENANT_NAME>"
		echo "<TENANT_NAME> is the name of TENANT to install"
		exit 1
	fi
	echo "use CSMDB_TENANT$2; drop database CSMDB_TENANT$2;" > ./scripts/temp.sql
	nohup mysql --user=$USER_ROOT --password=$PWD_ROOT --host=$DB_HOST --port=$DB_PORT --batch --force < ./scripts/temp.sql 
	rm -rf ./scripts/temp.sql
fi




exit 0
#/usr/bin/env zsh

fixture=$(cat << EOF > ./tmp.sh
echo 'Running!'
export DB_NAME=springdemo
export DB_DBMS=mysql
export DB_USERNAME=springuser
export DB_PASSWORD=springpassword
export SERVER_PORT=5050
export ACTIVE_PROFILE=development
export CLIENT_ID=fooClientIdPassword
export DB_HOSTNAME=localhost
export CLIENT_SECRET=secret
export APP_HOSTNAME=localhost
export DDL_AUTO=create-drop
export JWT_SIGNING_KEY=supersecretkey
EOF
)
echo "${fixture}"
source ./tmp.sh
rm -f ./tmp.sh
echo "DB_NAME IS ${DB_NAME}"
./gradlew $@

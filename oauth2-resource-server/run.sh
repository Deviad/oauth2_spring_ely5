#!/usr/bin/env bash
SERVER_PORT=5050 CLIENT_SECRET=secret ACTIVE_PROFILE=development APP_HOSTNAME=localhost DDL_AUTO=create-drop DB_USERNAME=springuser CLIENT_ID=fooClientIdPassword DB_DBMS=mysql JWT_SIGNING_KEY=supersecretkey DB_NAME=springdemo DB_HOSTNAME=localhost DB_PASSWORD=springpassword ./gradlew clean bootRun

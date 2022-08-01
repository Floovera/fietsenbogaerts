/opt/mssql-tools//bin/sqlcmd -S mssqlserver -U sa -P Th1s1sMyPass_word -Q  'CREATE DATABASE barka_db;'
/opt/mssql-tools//bin/sqlcmd -S mssqlserver -U sa -P Th1s1sMyPass_word -d barka_db -Q  'CREATE SCHEMA magazijn;'
/opt/mssql-tools//bin/sqlcmd -S mssqlserver -U sa -P Th1s1sMyPass_word -d barka_db -Q  'CREATE SCHEMA klant;'
/opt/mssql-tools//bin/sqlcmd -S mssqlserver -U sa -P Th1s1sMyPass_word -d barka_db -Q  'CREATE SCHEMA leverancier;'
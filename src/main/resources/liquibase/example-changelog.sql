--liquibase formatted sql
--changeset your-name:serial-number-of-changelog
CREATE TABLE testTable(
  columnName1 VARCHAR (355)
);
--rollback DROP TABLE
--rollback testTable
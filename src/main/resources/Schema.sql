
drop  database if exists fundstransfer;
create database fundstransfer;

use fundstransfer;

CREATE TABLE if not exists acc (
    id int NOT NULL AUTO_INCREMENT,
    acc_name char(100) NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY(id)
);

CREATE TABLE if not exists balance (
    balance_id int NOT NULL AUTO_INCREMENT,
    account_id int NOT NULL,
    balance_amount float NOT NULL,
    CONSTRAINT balance_pk PRIMARY KEY(balance_id),
    CONSTRAINT account_balance_fk FOREIGN KEY(account_id) REFERENCES acc (id)
);

INSERT INTO acc (acc_name) values ('Fred');
INSERT INTO acc (acc_name) values ('Barney');
INSERT INTO balance (account_id, balance_amount) values ('1', 1000.00);
INSERT INTO balance (account_id, balance_amount) values ('2', 4000.00);
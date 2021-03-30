create table user (
    id_user                        int(10)	auto_increment,
    username                       varchar(255),
    password                       varchar(100)
)
;

create table transaction (
    id_transaction                 auto_increment,
    type_transaction		   varchar(255),
    desc_transaction               varchar(255),
    date_transaction               date,
    amount                         decimal(19,2)
)
;

create table balance (
    id_balance                     int(10)	auto_increment,
    balance_init                   decimal(19,2),
    balance_act                    decimal(19,2),
    date			   Date,
    description                    varchar(255)
)
;
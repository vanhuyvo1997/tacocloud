create table taco (id bigint not null, created_at datetime(6), name varchar(32) not null, order_id bigint, primary key (id)) engine=InnoDB;
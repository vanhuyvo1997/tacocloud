create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );



create table taco_order (id bigint not null, cccvv varchar(255), cc_expiration varchar(255), cc_number varchar(255), delivery_city varchar(255), delivery_name varchar(255), delivery_state varchar(255), delivery_street varchar(255), delivery_zip varchar(255), placed_at datetime(6), user_id bigint, primary key (id)) engine=InnoDB;
create table user (id bigint not null, password varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
alter table taco add constraint FK184lm2u57rhvl81xqayd08nwx foreign key (order_id) references taco_order (id);
alter table taco_ingredients add constraint FK7y679y77n5e75s3ss1v7ff14j foreign key (ingredients_id) references ingredient (id);
alter table taco_ingredients add constraint FK1qxlnmlm6t23d3x32kofmhoit foreign key (tacos_id) references taco (id);
alter table taco_order add constraint FK1e7afaasuwdukhtillc612bvg foreign key (user_id) references user (id);

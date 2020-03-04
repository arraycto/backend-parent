create schema if not exists coupon collate utf8mb4_unicode_ci;

create table if not exists sales_coupon
(
	id bigint auto_increment
		primary key,
	template_id bigint null,
	user_id bigint null,
	coupon_code varchar(64) null,
	get_time datetime null comment '获得的时间',
	status int null comment '已使用的，未使用的，过期的，直接设置状态，不用设置时间，省去了检索优惠券时的逻辑判断'
);

create index sales_coupon_template_id_index
	on sales_coupon (template_id);

create index sales_coupon_user_id_index
	on sales_coupon (user_id);

create table if not exists sales_coupon_template
(
	id bigint auto_increment
		primary key,
	available tinyint null,
	expired tinyint null,
	name varchar(64) null,
	logo varchar(256) null,
	type tinyint null comment '线路本身的促销，发券，领券',
	product_line int null,
	coupon_count int null,
	create_time datetime null,
	admin_user_id bigint null,
	template_key varchar(128) null,
	dispatch_users varchar(1024) null comment '分发的人',
	rule varchar(1024) null comment '规则json字符串存储，也可以新建一张表做关联',
	constraint coupon_template_name_uindex
		unique (name)
);

create index coupon_template_category_index
	on sales_coupon_template (type);

create index coupon_template_user_id_index
	on sales_coupon_template (admin_user_id);


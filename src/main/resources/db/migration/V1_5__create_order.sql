create table `order` (
    `id` integer not null auto_increment,
    `shipped_from_id` integer,
    `customer_id` integer,
    `created_at` timestamp,
    `country` varchar(255),
    `city` varchar(255),
    `county` varchar(255),
    `street_address` varchar(255),
    constraint pk_order primary key (`id`)
);

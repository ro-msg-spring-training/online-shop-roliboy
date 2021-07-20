create table `product` (
    `id` integer not null auto_increment,
    `name` varchar(255),
    `description` varchar(255),
    `price` decimal(19, 2),
    `weight` double,
    `category_id` integer,
    `supplier_id` integer,
    `image_url` varchar(255),
    constraint pk_product primary key (`id`)
);

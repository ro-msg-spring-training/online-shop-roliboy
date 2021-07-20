--TODO: replace `id` with composite (order_id, product_id) composite key
create table `order_detail` (
    `id` integer not null auto_increment,
    `order_id` integer,
    `product_id` integer,
    `quantity` integer,
    constraint pk_order_detail primary key (`id`)
);

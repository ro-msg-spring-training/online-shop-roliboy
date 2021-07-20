--TODO: replace `id` with composite (product_id, location_id) composite key
create table `stock` (
    `id` integer not null auto_increment,
    `product_id` integer,
    `location_id` integer,
    `quantity` integer,
    constraint pk_stock primary key (`id`)
);

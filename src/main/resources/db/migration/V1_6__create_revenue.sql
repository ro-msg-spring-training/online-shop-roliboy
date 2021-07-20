create table `revenue` (
    `id` integer not null auto_increment,
    `location_id` integer,
    `date` date,
    `sum` decimal(19, 2),
    constraint pk_revenue primary key (`id`)
);

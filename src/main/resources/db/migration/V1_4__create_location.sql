create table `location` (
    `id` integer not null auto_increment,
    `name` varchar(255),
    `country` varchar(255),
    `city` varchar(255),
    `county` varchar(255),
    `street_address` varchar(255),
    constraint pk_location primary key (`id`)
);

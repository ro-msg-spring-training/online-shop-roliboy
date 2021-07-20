create table `customer` (
    `id` integer not null auto_increment,
    `first_name` varchar(255),
    `last_name` varchar(255),
    `username` varchar(255),
    `password` varchar(255),
    `email_address` varchar(255),
    constraint pk_customer primary key (`id`)
);

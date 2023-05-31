create table klant_klanten
(
    id             bigint auto_increment
        primary key,
    btw_nummer     varchar(255) null,
    bus            varchar(255) null,
    email          varchar(255) null,
    gemeente       varchar(255) null,
    huisnummer     varchar(255) null,
    klant_type     varchar(255) null,
    land           varchar(255) null,
    mobiel         varchar(255) null,
    naam           varchar(255) null,
    opmerkingen    varchar(255) null,
    postcode       varchar(255) null,
    straat         varchar(255) null,
    telefoonnummer varchar(255) null,
    uuid           varchar(255) null,
    constraint UK_l0di89ve7ps306njwqf21fn9s
        unique (uuid)
);

create table klant_orders
(
    id          bigint auto_increment
        primary key,
    klant_id    varchar(255) null,
    naam        varchar(255) null,
    opmerkingen varchar(255) null,
    uuid        varchar(255) null,
    constraint UK_pomc4bk4twnrp5r9wmd3jbfym
        unique (uuid),
    constraint klant_orders_ibfk_1
        foreign key (klant_id) references klant_klanten (uuid)
);

create index klant_id
    on klant_orders (klant_id);

create table leverancier_leveranciers
(
    id             bigint auto_increment
        primary key,
    btw_nummer     varchar(255) null,
    bus            varchar(255) null,
    email          varchar(255) null,
    fax            varchar(255) null,
    gemeente       varchar(255) null,
    huisnummer     varchar(255) null,
    land           varchar(255) null,
    mobiel         varchar(255) null,
    naam           varchar(255) null,
    opmerkingen    varchar(255) null,
    postcode       varchar(255) null,
    straat         varchar(255) null,
    telefoonnummer varchar(255) null,
    uuid           varchar(255) null,
    constraint UK_bfjh1pyw3btb69sdu45ggvhmy
        unique (uuid)
);

create table leverancier_contacten
(
    id              bigint auto_increment
        primary key,
    contact_methode int          null,
    gegevens        varchar(255) null,
    naam            varchar(255) null,
    onderwerp       varchar(255) null,
    uuid            varchar(255) null,
    leverancier_id  bigint       not null,
    constraint UK_hbe78vm6286enc9s0q4b2n641
        unique (uuid),
    constraint FKk7xucgr38ubi16vj52pwan705
        foreign key (leverancier_id) references leverancier_leveranciers (id)
);

create table magazijn_leveranciers
(
    id   bigint auto_increment
        primary key,
    naam varchar(255) null,
    uuid varchar(255) null,
    constraint UK_2bdxgompf7dhlpad1a1ri8nt8
        unique (uuid)
);

create table magazijn_artikels
(
    id               bigint auto_increment
        primary key,
    aankoop_prijs    decimal(19, 2) null,
    aantal_in_stock  int            null,
    actuele_prijs    decimal(19, 2) null,
    code             varchar(255)   null,
    merk             varchar(255)   null,
    minimum_in_stock int            null,
    omschrijving     varchar(255)   null,
    uuid             varchar(255)   null,
    verkoop_prijs    decimal(19, 2) null,
    leverancier_id   bigint         null,
    constraint UK_a2qw997fshflh0wtl87f5vk9p
        unique (uuid),
    constraint UK_qwq80jh1x6yiitek8mb3n65k7
        unique (code),
    constraint FKkipf8m320ml6gd4wd13lruyql
        foreign key (leverancier_id) references magazijn_leveranciers (id)
);
create table klant_werkuren
(
    id                bigint auto_increment
        primary key,
    aantal_uren       double       not null,
    btw_bedrag        double       not null,
    btw_perc          int          not null,
    datum             datetime     null,
    totaal_exclus_btw double       not null,
    totaal_inclus_btw double       not null,
    uuid              varchar(255) null,
    uur_tarief        double       not null,
    verkoopuuid       varchar(255) null,
    constraint UK_537jhb3kjei3bmnwbyn37e9iu
        unique (uuid),
    constraint klant_werkuren_klant_verkopen_uuid_fk
        foreign key (verkoopuuid) references klant_verkopen (uuid)
);

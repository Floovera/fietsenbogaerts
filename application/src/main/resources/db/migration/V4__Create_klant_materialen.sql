create table klant_materialen
(
    id                    bigint auto_increment
        primary key,
    aantal_artikels       int          not null,
    artikel_code          varchar(255) null,
    artikel_merk          varchar(255) null,
    artikel_omschrijving  varchar(255) null,
    artikeluuid           varchar(255) null,
    btw_bedrag            double       not null,
    btw_perc              int          not null,
    korting               int          not null,
    totaal_exclus_btw     double       not null,
    totaal_inclus_btw     double       not null,
    uuid                  varchar(255) null,
    verkoop_prijs_artikel double       not null,
    verkoopuuid           varchar(255) null,
    constraint UK_nmamcassqnoo3m8dg8qt02u9t
        unique (uuid),
    constraint klant_materialen_klant_verkopen_uuid_fk
        foreign key (verkoopuuid) references klant_verkopen (uuid)
);

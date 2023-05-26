alter table klant_verkopen
    add datum       date         null,
    add order_type  varchar(255) null,
    add order_nummer     varchar(255) null,
    add reparatie_nummer varchar(255) null;
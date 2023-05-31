alter table klant_orders
    add datum       date         null,
    add order_type  varchar(255) null,
    add order_nummer     varchar(255) null,
    add reparatie_nummer varchar(255) null;
alter table klant_werkuren
modify column uur_tarief        decimal(19, 2) null,
modify column totaal_exclus_btw decimal(19, 2) null,
modify column totaal_inclus_btw decimal(19, 2) null,
modify column btw_bedrag        decimal(19, 2) null;
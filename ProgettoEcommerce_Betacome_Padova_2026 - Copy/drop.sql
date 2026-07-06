
    set client_min_messages = WARNING;

    alter table if exists autenticazione 
       drop constraint if exists FKpy1kj4dfd284wkrcai8httg07;

    alter table if exists carrello 
       drop constraint if exists FK8wocb69w6jyqefu7etmxgni2p;

    alter table if exists indirizzi 
       drop constraint if exists FKplpq215m028r1n3bo6n2hkb2s;

    alter table if exists metodo_pagamento 
       drop constraint if exists FKkimw751gpi5ktca1qj9x33dit;

    alter table if exists ordini 
       drop constraint if exists FK6odl1p603s8jmass6j0h3bhna;

    alter table if exists pagamenti 
       drop constraint if exists FK6qxunpjghhj5j09spov7i8vuf;

    alter table if exists pagamenti 
       drop constraint if exists FKb19bn6ntprd4pcu61w9fpyqin;

    alter table if exists prodotti 
       drop constraint if exists FKbtowcuwota1u5q8828gyrhxnk;

    alter table if exists prodotti 
       drop constraint if exists FK4048s4fgw4f6m3qphnjv5jloe;

    alter table if exists prodotti_carrello 
       drop constraint if exists FKgv1pg75xggqeq3s0xmvy9k9lg;

    alter table if exists prodotti_carrello 
       drop constraint if exists FK3bqrhc1ybxv7a3ioweqoo2chm;

    alter table if exists prodotti_ordine 
       drop constraint if exists FKrbg6wjfkm0ahqtiy3vuivkfaf;

    alter table if exists prodotti_ordine 
       drop constraint if exists FK90duas5hnh3p3kb88dg9oswa0;

    alter table if exists ricevuta 
       drop constraint if exists FK3dsdedap0rkk2ptltsi5fxvt5;

    alter table if exists sconti 
       drop constraint if exists FK8wq2bkcqu3wvmsfw4fatf6xap;

    alter table if exists users 
       drop constraint if exists FK7vgts5e6ne7mf7uw9nrd8kpc3;

    drop table if exists autenticazione cascade;

    drop table if exists carrello cascade;

    drop table if exists categorie cascade;

    drop table if exists indirizzi cascade;

    drop table if exists messaggi_sistema cascade;

    drop table if exists metodo_pagamento cascade;

    drop table if exists ordini cascade;

    drop table if exists pagamenti cascade;

    drop table if exists prodotti cascade;

    drop table if exists prodotti_carrello cascade;

    drop table if exists prodotti_ordine cascade;

    drop table if exists ricevuta cascade;

    drop table if exists ruoli cascade;

    drop table if exists sconti cascade;

    drop table if exists status_ordine cascade;

    drop table if exists users cascade;


    set client_min_messages = WARNING;

    alter table if exists autenticazione 
       drop constraint if exists FKhp9xykeporddr021barv2u5y6;

    alter table if exists carrello 
       drop constraint if exists FK4lus6odvncyml62i4k7wakij6;

    alter table if exists indirizzi 
       drop constraint if exists FKsn1d63u2l9aospedj6wsojpps;

    alter table if exists metodo_pagamento 
       drop constraint if exists FKkfhintbbf2ysrb8gjb9wap6ga;

    alter table if exists ordini 
       drop constraint if exists FKj77333p7l9ognbsgl5q7sroux;

    alter table if exists ordini 
       drop constraint if exists FKn9pthby85h3scynafely8xjxj;

    alter table if exists ordini 
       drop constraint if exists FKp9v03irbus4f4l5urhtyu8rwe;

    alter table if exists ordini 
       drop constraint if exists FK8f7bjdq6rbfl3g290697eyilx;

    alter table if exists pagamenti 
       drop constraint if exists FKaj1gphc6v5nsuetqyf5ykc9d9;

    alter table if exists pagamenti 
       drop constraint if exists FKf2x11hvonh4h2qu4f4oloiv4y;

    alter table if exists pagamenti 
       drop constraint if exists FKq77eli5kv3okbvr7p1smf7v28;

    alter table if exists prodotti 
       drop constraint if exists FKbtowcuwota1u5q8828gyrhxnk;

    alter table if exists prodotti 
       drop constraint if exists FK3gc52tocic1v0wpn9wts41tv4;

    alter table if exists prodotti_carrello 
       drop constraint if exists FKculjnbh3efj9cqtjwhrf57vyk;

    alter table if exists prodotti_carrello 
       drop constraint if exists FK7j387fc10rblygr2p6ufv30jb;

    alter table if exists prodotti_ordine 
       drop constraint if exists FKoryut1a41bxv1hvmqxv3few9q;

    alter table if exists prodotti_ordine 
       drop constraint if exists FKgb812bitx3s292cna1852ykjg;

    alter table if exists ricevuta 
       drop constraint if exists FKnob6wi8vsui2745e42pkabc9c;

    alter table if exists sconti 
       drop constraint if exists FKsm4cljgbr05sljbp6l9qw7ray;

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

    drop table if exists stato_ordine cascade;

    drop table if exists stato_pagamenti cascade;

    drop table if exists users cascade;

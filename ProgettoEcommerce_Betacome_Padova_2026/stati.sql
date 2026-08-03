INSERT INTO stato_pagamenti (stato)
VALUES
    ('IN_ATTESA'),
    ('PAGATO'),
    ('FALLITO'),
    ('RIMBORSATO');

INSERT INTO stato_ordine (stato)
VALUES
	('IN ATTESA DI PAGAMENTO'),
	('ANNULLATO'),
	('SPEDITO');

INSERT INTO ruoli(ruolo)
VALUES
	('Admin'),
	('User'),
	('Venditore');
INSERT INTO stato_pagamenti (stato)
VALUES
    ('In Attesa'),
    ('Completato'),
    ('Confermato'),
    ('Fallito');

INSERT INTO stato_ordine (stato)
VALUES
	('Annullato'),
	('In Attesa'),
	('Confermato'),
	('Spedito');
	
	
INSERT INTO stato_notifica(stato)
VALUES
	('IN ATTESA'),
	('ACCETTATA'),
	('RIFIUTATA');

INSERT INTO ruoli(ruolo)
VALUES
	('Admin'),
	('User'),
	('Venditore');
	
INSERT INTO categorie(categoria)
VALUES ('Falegname'),
		('Metallurgo');
		
		
INSERT INTO sotto_categoria(categoria, sotto_categoria)
VALUES (1, 'Mobili'),
	   (1, 'Cucina'),
	   (2, 'Mobili'),
	   (2, 'Arte');
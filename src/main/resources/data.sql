INSERT INTO USUARIO(login, senha, instante) VALUES ('rick@teste.com', 'MTIzNDU2', '2021-08-03 16:49:23.532773');

INSERT INTO PERMISSION(description) VALUES('ADMIN');
INSERT INTO PERMISSION(description) VALUES('USER');

INSERT INTO USUARIO_PERMISSION(id_permissions, ID_USER) VALUES (1,1);
INSERT INTO USUARIO_PERMISSION(id_permissions, ID_USER) VALUES (2,1);

INSERT INTO CATEGORIA(nome, categoria_mae_id) values ("Eletrodomesticos", null);
INSERT INTO CATEGORIA(nome, categoria_mae_id) values ("Geladeiras", 1);
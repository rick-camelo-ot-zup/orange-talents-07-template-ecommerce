INSERT INTO USUARIO(login, senha, instante) VALUES ('rick@teste.com', 'MTIzNDU2', '2021-08-03 16:49:23.532773');
INSERT INTO USUARIO(login, senha, instante) VALUES ('teste@teste.c', 'MTIzNDU2', '2021-08-03 16:49:23.532773');
INSERT INTO USUARIO(login, senha, instante) VALUES ('teste@test', 'MTIzNDU2', '2021-08-04 16:49:23.532773');

INSERT INTO PERMISSION(description) VALUES('ADMIN');
INSERT INTO PERMISSION(description) VALUES('USER');

INSERT INTO USUARIO_PERMISSION(id_permissions, ID_USER) VALUES (1,1);
INSERT INTO USUARIO_PERMISSION(id_permissions, ID_USER) VALUES (2,1);
INSERT INTO USUARIO_PERMISSION(id_permissions, ID_USER) VALUES (2,2);
INSERT INTO USUARIO_PERMISSION(id_permissions, ID_USER) VALUES (2,3);

INSERT INTO CATEGORIA(nome, categoria_mae_id) values ("Eletrodomesticos", null);
INSERT INTO CATEGORIA(nome, categoria_mae_id) values ("Geladeiras", 1);

INSERT INTO PRODUTO(descricao, instante, nome, quantidade, valor, categoria_id, usuario_id)
VALUES ("Geladeira 450L", "2021-08-04 13:18:33.389922", "Geladeira", 10, 125.45, 1, 1);

INSERT INTO CARACTERISTICA(nome, descricao, produto_id) VALUES("Marca","Eletrolux", 1);
INSERT INTO CARACTERISTICA(nome, descricao, produto_id) VALUES("Modelo","HNGH123", 1);
INSERT INTO CARACTERISTICA(nome, descricao, produto_id) VALUES("Voltagem","220V", 1);

INSERT INTO OPINIAO_PRODUTO(descricao, nota, titulo, produto_id, usuario_id)
VALUES ("AHDASDA RESTESAD", 3, "BOM", 1, 2);
INSERT INTO OPINIAO_PRODUTO(descricao, nota, titulo, produto_id, usuario_id)
VALUES ("POKADU DSADOAS JRUNERE", 5, "ESPETACULAR", 1, 3);

INSERT INTO PERGUNTA_PRODUTO(instante, titulo, produto_id, usuario_id)
VALUES ("2021-08-04 13:18:33.389922", "AINDA ESTÁ DISPONIVEL?",1,2);
INSERT INTO PERGUNTA_PRODUTO(instante, titulo, produto_id, usuario_id)
VALUES ("2021-08-04 13:18:33.389922", "QUANTO TEMPO DURA A BATERIA?",1,2);


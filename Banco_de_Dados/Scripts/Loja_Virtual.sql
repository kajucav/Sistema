create database loja_virtual;

create table produto (
	cod_prod serial primary key,
	nome_prod varchar(255) not null,
	preco decimal(10, 2) not null,
	qtd_estoque int not null,
	departamento varchar(255) not null,
	marca varchar(100) not null,
	img_local varchar(255) not null
);

create table cliente (
	cpf_cnpj bigint primary key,
	nome_cliente varchar(255) not null,
	email varchar(255) not null,
	telefone varchar(11) not null,
	cep int not null,
	check (validar_cpf_cnpj(cpf_cnpj:: varchar)) --chama a função valida cpf
);

create table pagamento (
	cod_pag serial primary key,
	cod_prod integer not null,
	cpf_cnpj bigint not null,
	qnt_prod int not null,
	total decimal(10, 2) not null,
	metodo_pag varchar(20) not null,
	data_pag timestamp default current_timestamp,
	constraint fk_cliente foreign key(cpf_cnpj) references cliente(cpf_cnpj),
	constraint fk_produto foreign key(cod_prod) references produto(cod_prod)
);

create table usuario (
        cod_usu serial primary key,
        login varchar(255) not null unique,
        senha varchar(255) not null
);

create table log_erro (
	cod_erro serial primary key,
	cln_origem varchar(255),
	cln_pai varchar(255),
	msg text,
	data_erro timestamp default current_timestamp
);

create table hist_mod (
	cod_hist_mod serial primary key,
	tb_nome varchar(255) not null,
	cln_nome varchar(255) not null,
	cod_registro integer not null,
	valor_antigo text,
	valor_novo text,
	data_modificacao timestamp default current_timestamp
);

create table ordem_compra (
	cod_ord_compra serial primary key,
	cod_prod integer not null,
	qnt_comprar integer not null,
	data_pedido timestamp default current_timestamp,
	status_compra varchar(50) not null default 'Pendente',
	constraint fk_prod_ord foreign key (cod_prod) references produto(cod_prod)
);

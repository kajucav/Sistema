-- FUNÇÃO VERIFICA CPF CLIENTE
create or replace function validar_cpf_cnpj(vcpf varchar)
returns boolean as $$
declare
	soma int;
	indice int;
	digit_1 int;
	digit_2 int;
	nr_documento varchar(11);
	digit_1_cpf char(1);
	digit_2_cpf char(1);
begin
	vcpf := regexp_replace(vcpf, '[^0-9]', '', 'g');
	if length(vcpf) <> 11 or vcpf in ('00000000000', '11111111111', '22222222222', '33333333333', '44444444444',
	'55555555555', '66666666666', '77777777777', '88888888888', '99999999999', '12345678909', '12345678901') then
		raise exception 'CPF INVÁLIDO NÚMERO DE DIGITOS INCORRETO: %', vcpf;
	end if;

-- VERIFICADOR DE 1 DIGITO VERIFICADOR
	soma := 0;
	for indice in 1..9 loop
		soma:= soma + cast(substring(vcpf, indice, 1) as integer) * (11 - indice);
	end loop;
	digit_1 := 11 - (soma % 11);
	if digit_1 >= 10 then
		digit_1 := 0;
	end if;

-- VERIFICADOR DE 2 DIGITO VERIFICADOR
	soma := 0;
	for indice in 1..10 loop
		soma := soma + cast(substring(vcpf, indice, 1) as integer) * (12 - indice);
	end loop;
	digit_2 := 11 - (soma % 11);
	if digit_2 >= 10 then
		digit_2 := 0;
	end if;

-- VERIFICA OS 2 DIGITOS JUNTOS
	digit_1_cpf := substring(vcpf, 10, 1);
	digit_2_cpf := substring(vcpf, 11, 1);

	if digit_1 = cast(digit_1_cpf as integer) and digit_2 = cast(digit_2_cpf as integer) then 
		return true;
	else
		raise exception 'CPF INVÁLIDO DIGITE NOVAMENTE: %', vcpf;
	end if;
end;
$$ language plpgsql;
	
-- LOG ERRO DE INSERÇÃO

create or replace function tr_ins_pagamento()
returns trigger as $$
begin
	-- VERIFICA A EXISTENCIA DO CLIENTE NO BANCO DE DADOS
	if not exists (select 1 from cliente where cpf_cnpj = new.cpf_cnpj) then
		insert into log_erro(cln_origem, msg, cln_pai)
		values ('pagamento.cpf_cnpj', 'Adicione previamente o comprador na aba cliente',
				'cliente.cpf_cnpj');
		return null;
	end if;

-- VERIFICA A EXISTENCIA DO PRODUTO NO BANCO DE DADOS
	if not exists (select 1 from produto where cod_prod = new.cod_prod) then
		insert into log_erro(cln_origem, msg, cln_pai)
		values ('pagamento.cod_prod', 'Adicione previamente o produto na aba produto',
		'produto.cod_prod');
		return null;
	end if;
-- RETORNA O VALOR SE ESTIVER CORRETO A INSERÇÃO
	return new;
end;
$$ language plpgsql;

-- FUNÇÃO DE HISTORICO TABELA PRODUTO
create or replace function registra_mod_prod()
returns trigger as $$
begin
	if aux = 'update' then
		insert into hist_mod(tb_nome, cln_nome, cod_registro, valor_antigo, valor_novo)
		values ('produto', 'nome_prod', new.cod_prod, old.nome_prod, new.nome_prod),
			   ('produto', 'preco', new.cod_prod, old.preco::text, new.preco::text),
			   ('produto', 'qtd_estoque', new.cod_prod, old.qtd_estoque::text, new.qtd_estoque::text),
			   ('produto', 'departamento', new.cod_prod, old.departamento, new.departamento),
               ('produto', 'marca', new.cod_prod, old.marca, new.marca),
               ('produto', 'img_local', new.cod_prod, old.img_local, new.img_local);
		
	end if;
	return new;
end;
$$ language plpgsql;

-- FUNÇÃO DE HISTORICO TABELA CLIENTE
create or replace function registra_mod_cliente()
returns trigger as $$
begin
	if aux = 'update' then
		insert into hist_mod(tb_nome, cln_nome, cod_registro, valor_antigo, valor_novo)
		values ('cliente', 'nome_cliente', new.cpf_cnpj, old.nome_cliente, new.nome_cliente),
               ('cliente', 'email', new.cpf_cnpj, old.email, new.email),
               ('cliente', 'telefone', new.cpf_cnpj, old.telefone, new.telefone),
               ('cliente', 'cep', new.cpf_cnpj, old.cep::text, new.cep::text);
	end if;
	return new;
end;
$$ language plpgsql;

-- FUNÇÃO DE HISTORICO TABELA PAGAMENTO
create or replace function registra_mod_pag()
returns trigger as $$
begin
	if aux = 'update' then
		insert into hist_mod(tb_nome, cln_nome, cod_registro, valor_antigo, valor_novo)
		values ('pagamento', 'cod_prod', new.cod_pag, old.cod_prod::text, new.cod_prod::text),
               ('pagamento', 'cpf_cnpj', new.cod_pag, old.cpf_cnpj::text, new.cpf_cnpj::text),
               ('pagamento', 'qnt_prod', new.cod_pag, old.qnt_prod::text, new.qnt_prod::text),
               ('pagamento', 'total', new.cod_pag, old.total::text, new.total::text),
               ('pagamento', 'metodo_pag', new.cod_pag, old.metodo_pag, new.metodo_pag),
               ('pagamento', 'data_pag', new.cod_pag, old.data_pag::text, new.data_pag::text);
	end if;
	return new;
end;
$$ language plpgsql;
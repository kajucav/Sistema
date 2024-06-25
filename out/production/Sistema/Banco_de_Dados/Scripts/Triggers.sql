-- TRIGGER DE ERRO PARA VERIFICAR A TABELA PAGAMENTO 
create trigger tr_ins_pagamento
before insert on pagamento
for each row
execute function tr_ins_pagamento();

-- TRIGGER DE HISTORICO TABELA PRODUTO
create trigger tr_mod_prod
after update on produto
for each row
execute function registra_mod_prod();

-- TRIGGER DE HISTORICO TABELA CLIENTE
create trigger tr_mod_cliente
after update on cliente
for each row
execute function registra_mod_cliente();

-- TRIGGER DE HISTORICO TABELA PAGAMENTO
create trigger tr_mod_pag
after update on pagamento
for each row
execute function registra_mod_pag();
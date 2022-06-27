'''

select 
case when "*Situação do Cliente" in ('Ex-Clientes','Ex-Cliente','Ex-cliente') then 'ex_cliente'
when "*Situação do Cliente" in ('Lead') then 'lead'
when "*Situação do Cliente" in ('Visitante', 'Teste') then 'visitante'
when "*Situação do Cliente" in ('Ativo', 'Cliente') then 'ativo'
when "*Situação do Cliente" in ('Cancelado') then 'cancelado'
when "*Situação do Cliente" in ('Trancado') then 'trancado'
when "*Situação do Cliente" in ('CANCELAMENTO ESPONTÂNEO') then 'cancelamento_espontaneo'
when "*Situação do Cliente" in ('CANCELAMENTO COMPULSÓRIO') then 'cancelamento_compulsorio'
when "*Situação do Cliente" in ('EM ABERTO > 2 MESES') then 'em_aberto' 
--when "*Situação do Cliente" is null then null
end as "situacao_cliente"
from transient."3rd_active_campaign_enviados_20220615"

'''


import requests
from datetime import date
import pandas.io.sql as psql
import pandas as pd
from sqlalchemy import create_engine
from datetime import datetime, timezone
from time import sleep
import json

username =  'zillyonweb' 
password = 'pactodb2020' 
ipaddress = 'dbproself01.proselfit.com.br' 
port = '5432' 
dbname = 'PROSELF_PRD2' 
connection = f'postgresql://{username}:{password}@{ipaddress}:{port}/{dbname}'  

# parameters for connection to the database transient (datalake)
usr_datalake =  'postgres' 
pwd_datalake = 'X%p85qG!D6j[MoU%4[gEz,8BfKe^^0' 
host_datalake = '172.16.22.53' 
port_datalake = '5432' 
dbname_datalake = 'db_selfit_datalake' 
cnx_datalake = f'postgresql://{usr_datalake}:{pwd_datalake}@{host_datalake}:{port_datalake}/{dbname_datalake}'  

query = '''
        select
            pessoa.codigo as PersonCode 
            ,left(pessoa.nome, strpos(pessoa.nome, ' ') -1 ) as personname
            --,'RENATO MARKETING' as lastname
            ,right(pessoa.nome, length(pessoa.nome) - STRPOS(pessoa.nome, ' ')) as lastname
            ,plano.descricao as PlanName
            ,(select '+55' || translate(telefone.numero, '(,),-, ', '') from telefone where pessoa = cliente.codigo and tipotelefone = 'CE' limit 1 ) as mobile
            ,(select '+55' || translate(telefone.numero, '(,),-, ', '') from telefone where pessoa = cliente.codigo and tipotelefone = 'RE' limit 1 ) as telephone
            ,(select email from email where email.pessoa = cliente.pessoa limit 1) as email
            ,cidade.nome as city
            ,estado.descricao as state
            ,to_char(contrato.vigenciade, 'yyyy-MM-dd') as planstartdate
            ,to_char(contrato.vigenciaateajustada, 'yyyy-MM-dd') as planexpirationdate
            ,empresa.nome as unitname
            ,pessoa.cfp as document
            ,pessoa.sexo as gender
            ,to_char(pessoa.datanasc , 'yyyy-MM-dd')  as birthdate
            ,cliente.matricula as registernumber
            ,endereco.cep as addresscep
            --,replace(translate(endereco.cep::text, '-',''),'.','') as addresscep
            ,endereco.bairro as addressneighborhood
            ,endereco.numero as addressnumber
            ,endereco.endereco as address
            ,endereco.complemento as addresscomplement
            ,cidp.nome as addresscity
            ,estp.descricao as addressstate
        from cliente
            left join pessoa on pessoa.codigo = cliente.pessoa
            left join empresa on cliente.empresa = empresa.codigo
            left join cidade on cidade.codigo = empresa.cidade
            left join estado on estado.codigo = empresa.estado
            left join cidade cidp on cidp.codigo = pessoa.cidade
            left join estado estp on estp.codigo = pessoa.estado
            left join contrato on contrato.pessoa = pessoa.codigo
            left join plano on plano.codigo = contrato.plano
            left join endereco on endereco.pessoa = pessoa.codigo
        --where cliente.codigomatricula = 1420556 -- joel
        --where cliente.pessoa = 1277684 --rafael
        order by contrato.datamatricula desc, case contrato.Situacao when 'AT' then 1	
            when 'TR' then 2	
            when 'IN' then 3	
            when 'CA' then 4
            else 5
            end asc
            limit 1000
    '''
    
query_control = '''select * from transient."3rd_active_campaign_enviados_20220615"''' 

# criando a base
df_all = psql.read_sql(query,connection) # a enviar
df_transient = psql.read_sql(query_control,cnx_datalake) # tabela control
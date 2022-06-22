import requests
from datetime import date
import pandas.io.sql as psql
import pandas as pd
from sqlalchemy import create_engine
from datetime import datetime, timezone
from time import sleep
import json

def main():
    # production
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
    dbname_datalake = 'transient' 
    cnx_datalake = f'postgresql://{usr_datalake}:{pwd_datalake}@{host_datalake}:{port_datalake}/{dbname_datalake}'  

    '''#quality assurance
    username_dev =  'zillyonweb' 
    password_dev = 'pactodb2020' 
    ipaddress_dev = '172.16.24.6' 
    port_dev = '5432' 
    dbname_dev = 'PROSELF_PRD2'
    connection = f'postgresql://{username_dev}:{password_dev}@{ipaddress_dev}:{port_dev}/{dbname_dev}'''

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
        --limit 15
    '''

    # mudar * para "Email" aqui.
    query_control = '''select * from transient."3rd_active_campaign_enviados_20220615"''' 

    # criando a base
    df_all = psql.read_sql(query,connection) # a enviar
    df_transient = psql.read_sql(query_control,cnx_datalake) # tabela controle
    
    
    #renomeando a coluna email do df_transient   
    df_transient.rename(columns={'Email':'email'},inplace=True)
    
    #df merge é o df que contém linhas que não té memail em comum nos 2 df
    df_merge = df_all[~df_all['email'].isin(df_transient['email'])]

    # NAO DESCOMENTAR ABAIXO
    '''headers = {"Accept": "application/json","Content-Type": "application/json","Api-Token" : "e285a63bc70b73d32d44d78afea1af53d2cf8265372b58a23822cb1a017fe51516af4297"}

    for i, row in df.iterrows(): #df.index
        payload = {"contact": {"email": df['email'][i],"firstName": df['personname'][i],"lastName": df['lastname'][i],"phone": df['mobile'][i],
                    "fieldValues": [{"field": "13","value": df['birthdate'][i]},
                                    {"field": "3","value": df['planname'][i]},
                                    {"field": "5","value": df['registernumber'][i]},
                                    {"field": "6","value": df['document'][i]},
                                    {"field": "14","value": df['address'][i]},
                                    {"field": "19","value": df['addressnumber'][i]},
                                    {"field": "20","value": df['addresscomplement'][i]},
                                    {"field": "18","value": df['addressneighborhood'][i]},
                                    {"field": "17","value": df['addresscep'][i]},
                                    {"field": "15","value": df['addresscity'][i]},
                                    {"field": "16","value": df['addressstate'][i]},
                                    {"field": "2","value": df['unitname'][i]},            
                                    {"field": "8","value": df['city'][i]},
                                    {"field": "9","value": df['state'][i]},
                                    {"field": "11","value": df['gender'][i]},
                                    {"field": "12","value": df['planstartdate'][i]},
                                    {"field": "21","value": df['planexpirationdate'][i]}]}}
        print(payload)
        r = requests.post('https://selfitacademias.api-us1.com/api/3/contact/sync',headers=headers,json=payload)
        if r.status_code == 200:
            gravaJsonHomologacao('INFO: ' + str(r.status_code))          
            print('INFO: ' +  str(r.subscriber_id)) # mudar para r.text 
        else:
            gravaJsonHomologacao('ERRO: ' + str(r.status_code))
            print('ERRO: ' + str(r.subscriber_id)) # mudar para r.text
        sleep(0.2)

def gravaJsonHomologacao(log):
    file_object = open('/opt/selfit/rundeck/ROTINA_API_MARKETING_ACTIVE_CAMPAIGN_'+date.today().strftime("%Y-%m-%d")+'.log', 'a')
    file_object = open('C:/Users/USER/Documents/Selfit/Scripts/Logs/ROTINA_API_MARKETING_ACTIVE_CAMPAIGN_2'+date.today().strftime("%Y-%m-%d")+'.log', 'a')
    file_object.write("\n")
    file_object.write("["+datetime.today().strftime("%d/%m/%Y %H:%M:%S")+"] "+log)
    file_object.close()'''

main()
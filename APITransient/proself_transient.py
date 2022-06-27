from sqlalchemy import create_engine
import pandas.io.sql as psql
import pandas as pd

# parameters for connection to the database proself_prd2
usr_proself =  'zillyonweb' 
pwd_proself = 'pactodb2020' 
host_proself = 'dbproself01.proselfit.com.br' 
port_proself = '5432' 
dbname_proself  = 'PROSELF_PRD2' 

connection_proself  = f'postgresql://{usr_proself}:{pwd_proself}@{host_proself}:{port_proself}/{dbname_proself}'  

# parameters for connection to the database transient (datalake)
# documentação sql alchemy 
usr_datalake =  'postgres' 
pwd_datalake = 'X%p85qG!D6j[MoU%4[gEz,8BfKe^^0' 
host_datalake = '172.16.22.53' 
port_datalake = '5432' 
dbname_datalake = 'db_selfit_datalake' 
schema_datalake = 'transient'

connection_datalake = f'postgresql://{usr_datalake}:{pwd_datalake}@{host_datalake}:{port_datalake}/{dbname_datalake}'  


def main():
    query_list_views = ''' 
                        select * 
                        from relatorio_view
                        where relatorio_view.ativo = true
                            and fonte !~ '[[:space:]]'
                        order by nome asc
                        limit 3
                        '''
    df_views = psql.read_sql(query_list_views, connection_proself)
    print(df_views)

    cnx_datalake = create_engine(connection_datalake)

    for i, row in df_views.iterrows():
        query_write_view = "select * from {}".format(df_views['fonte'][i])
        name_view = '{}'.format(df_views['fonte'][i])
        name_view = psql.read_sql(query_write_view, connection_proself)
        name_view.to_sql(df_views['fonte'][i], con=cnx_datalake, index=False, if_exists="replace") 
        print(name_view)
              
main()




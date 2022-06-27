import requests
import pandas as pd
import urllib.request

df = pd.read_csv(r"enderecos-100-primeiros.csv"
                 ,sep = ';'
                 ,on_bad_lines='skip')

df['cep'] = df['cep'].fillna(0) 
df['cep'] = df['cep'].replace('\.', '', regex = True,)
df['cep'] = df['cep'].replace('-', '', regex = True,)


df['cep'] = df['cep'].astype(int)
#print((df.head()))


for i in df['cep']:
    url = 'https://viacep.com.br/ws/%s/json/' % i
    headers = {'User-Agent': 'Autociencia/1.0'}
    requisicao = urllib.request.Request(url, headers=headers, method='GET')
    cliente = urllib.request.urlopen(requisicao)
    conteudo = cliente.read().decode('utf-8')
    cliente.close()
    print(conteudo)
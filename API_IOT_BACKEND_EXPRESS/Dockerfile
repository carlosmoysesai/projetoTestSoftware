# Dockerfile para API Express
FROM node:14

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar os arquivos package.json e package-lock.json
COPY package*.json ./

# Instalar as dependências do projeto
RUN npm install

# Copiar o restante do código da aplicação
COPY . .

# Copiar a chave do Firebase para o container
COPY config/serviceAccountKey.json ./config/serviceAccountKey.json

# Expor a porta usada pela aplicação
EXPOSE 3001

# Comando para iniciar a aplicação
CMD ["node", "app.js"]


# Comando para executar a aplicação
#CMD ["npm", "start"]
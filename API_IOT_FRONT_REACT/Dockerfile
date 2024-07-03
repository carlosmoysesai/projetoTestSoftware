# Estagio 1 - builder
FROM node:18.20.3-slim AS builder

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar os arquivos package.json e package-lock.json
COPY package*.json ./

# Instalar as dependências do projeto
RUN npm install

# Copiar o restante do código da aplicação
COPY . .

# Construir a aplicação para produção
RUN npm run build


# Estagio 2 - aplicação
FROM nginx:alpine

# Copiar a configuração personalizada do Nginx para o container
# COPY nginx.conf /etc/nginx/nginx.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copia os arquivos compilados para a pasta padrão do Nginx
COPY --from=builder /app/build /usr/share/nginx/html

# Expõe a porta que a aplicação vai rodar
EXPOSE 80

# Comando para iniciar o Nginx
CMD ["nginx", "-g", "daemon off;"]
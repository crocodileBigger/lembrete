# Stage 1: Build da aplicação
FROM node:24-alpine

WORKDIR /app

# Copia arquivos de dependências primeiro (otimização de cache do Docker)
COPY package*.json ./

# Instala as dependências
RUN npm install

# Copia o restante dos arquivos do projeto
COPY . .

# Executa o build (gera a pasta dist)
RUN npm run build

CMD [ "sh", "-c", "cp -r dist/* /usr/share/nginx/html" ]

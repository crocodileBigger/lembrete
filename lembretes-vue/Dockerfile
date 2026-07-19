FROM node:24-slim

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 5173

# O servidor do Vite roda como o processo principal (CMD), não travando o build
CMD ["npm", "run", "dev"]

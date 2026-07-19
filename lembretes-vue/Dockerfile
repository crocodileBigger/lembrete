FROM node:24-slim

WORKDIR /app

COPY package*.json ./
# O npm install aqui já vai instalar o Tailwind CSS que está no seu package.json
RUN npm install

COPY . .

EXPOSE 5173

CMD ["npm", "run", "dev"]

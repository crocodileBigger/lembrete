#!/bin/sh

# Caminho onde o Nginx espera os certificados
CERT_DIR="/etc/letsencrypt/live/localhost"
CERT_FILE="$CERT_DIR/fullchain.pem"
KEY_FILE="$CERT_DIR/privkey.pem"

# Cria a pasta caso não exista
mkdir -p "$CERT_DIR"

# Gera o certificado APENAS se ele não existir
if [ ! -f "$CERT_FILE" ]; then
    echo "Gerando certificado autoassinado para localhost..."
    openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
      -keyout "$KEY_FILE" \
      -out "$CERT_FILE" \
      -subj "/CN=localhost"
else
    echo "Certificado já existente. Pulando geração."
fi

# Executa o comando padrão da imagem do Nginx (inicia o servidor em primeiro plano)
exec nginx -g "daemon off;"

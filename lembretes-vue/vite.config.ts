import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [vue(), tailwindcss()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  // --- ADICIONE OU SUBSTITUA O BLOCO SERVER ABAIXO ---
  server: {
    host: true,         // Escuta em 0.0.0.0 (essencial para Docker)
    port: 5173,         // Porta padrão do Vite
    strictPort: true,   // Evita que o Vite tente outra porta se a 5173 estiver ocupada
    allowedHosts: ['vue-app', 'localhost'],
    watch: {
      usePolling: true, // Força o Vite a checar o disco manualmente dentro do container
      interval: 100,    // Intervalo de checagem em milissegundos
    },
    hmr: {
      protocol: 'wss',  // Força o uso de WebSocket Seguro (necessário para o seu Nginx 443 SSL)
      host: 'localhost',// O navegador vai tentar conectar de volta no seu localhost
      clientPort: 443,  // Porta do Nginx por onde o tráfego do WebSocket vai passar
    },
  },
})

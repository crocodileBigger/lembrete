<script setup>
import { ref } from 'vue'

// Estado reativo dos campos do formulário
const name = ref('')
const email = ref('')
const password = ref('')

// Estado para controle de mensagens ou loading
const errorMessage = ref('')
const isLoading = ref(false)

// Função de submissão do formulário
const handleLogin = async () => {
  // Validação básica em client-side
  if (!name.value || !email.value || !password.value) {
    errorMessage.value = 'Todos os campos são obrigatórios.'
    return
  }

  errorMessage.value = ''
  isLoading.value = true

  try {
    // Objeto contendo o payload técnico pronto para envio
    const payload = {
      name: name.value,
      email: email.value,
      password: password.value
    }

    console.log('Enviando dados da requisição:', payload)

    // Simulação de chamada de API / Endpoint de autenticação
    // await api.post('/auth/login', payload)

  } catch (error) {
    errorMessage.value = 'Falha ao autenticar. Verifique seus dados.'
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <div class="login-container">
    <h2>Acessar Conta</h2>

    <!-- O modificador .prevent evita o recarregamento padrão da página -->
    <form @submit.prevent="handleLogin">

      <div class="form-group">
        <label for="name">Nome:</label>
        <input
          id="name"
          v-model.trim="name"
          type="text"
          placeholder="Seu nome completo"
          required
        />
      </div>

      <div class="form-group">
        <label for="email">E-mail (Gmail):</label>
        <input
          id="email"
          v-model.trim="email"
          type="email"
          placeholder="seuemail@gmail.com"
          required
        />
      </div>

      <div class="form-group">
        <label for="password">Senha:</label>
        <input
          id="password"
          v-model="password"
          type="password"
          placeholder="Sua senha"
          required
        />
      </div>

      <p v-if="errorMessage" class="error-text">{{ errorMessage }}</p>

      <button type="submit" :disabled="isLoading">
        {{ isLoading ? 'Autenticando...' : 'Entrar' }}
      </button>

    </form>
    <div class="navigation-area">
      <p>Não tem uma conta?</p>
      <RouterLink to="/cadastro" class="btn-secondary">Criar Conta</RouterLink>
    </div>
  </div>
</template>

<style scoped>
/* Estilização estrutural básica */
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-family: sans-serif;
}

.form-group {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

.form-group label {
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.error-text {
  color: #d9534f;
  font-size: 0.9rem;
  margin-top: 5px;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #42b883; /* Cor padrão do Vue */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

button:disabled {
  background-color: #a3e0c2;
  cursor: not-allowed;
}
</style>

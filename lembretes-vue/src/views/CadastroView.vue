<script setup>
import { ref } from 'vue'

// Estado reativo dos campos
const name = ref('')
const email = ref('')
const password = ref('')

// Estado de controle da UI
const errorMessage = ref('')
const successMessage = ref('')
const isLoading = ref(false)

const handleRegister = async () => {
  // Validação estrita client-side
  if (!name.value || !email.value || !password.value) {
    errorMessage.value = 'Preencha todos os campos.'
    return
  }

  errorMessage.value = ''
  successMessage.value = ''
  isLoading.value = true

  try {
    const payload = {
      name: name.value,
      email: email.value,
      password: password.value
    }

    console.log('Payload enviado para registro:', payload)

    // Simulação de chamada HTTP POST para criação de conta
    // await api.post('/auth/register', payload)

    successMessage.value = 'Conta criada com sucesso!'

    // Limpa os campos após o sucesso
    name.value = ''
    email.value = ''
    password.value = ''

  } catch (error) {
    errorMessage.value = 'Erro ao registrar usuário. Tente novamente.'
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <div class="navigation-area">
    <p>Já possui uma conta?</p>
    <RouterLink to="/" class="btn-secondary">Voltar para o Login</RouterLink>
  </div>
  <div class="register-container">
    <h2>Criar Conta</h2>

    <form @submit.prevent="handleRegister">

      <div class="form-group">
        <label for="name">Nome Completo:</label>
        <input
          id="name"
          v-model.trim="name"
          type="text"
          required
        />
      </div>

      <div class="form-group">
        <label for="email">E-mail (Gmail):</label>
        <input
          id="email"
          v-model.trim="email"
          type="email"
          required
        />
      </div>

      <div class="form-group">
        <label for="password">Senha:</label>
        <input
          id="password"
          v-model="password"
          type="password"
          required
        />
      </div>

      <!-- Feedbacks visuais -->
      <p v-if="errorMessage" class="error-text">{{ errorMessage }}</p>
      <p v-if="successMessage" class="success-text">{{ successMessage }}</p>

      <button type="submit" :disabled="isLoading">
        {{ isLoading ? 'Cadastrando...' : 'Cadastrar' }}
      </button>

    </form>
  </div>
</template>

<style scoped>

.navigation-area {
  margin-top: 20px;
  text-align: center;
  border-top: 1px solid #eee;
  padding-top: 15px;
}

.navigation-area p {
  margin-bottom: 8px;
  font-size: 0.9rem;
  color: #666;
}

/* Transforma o RouterLink (que gera uma tag <a>) em um botão visual */
.btn-secondary {
  display: block;
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border: 1px solid #35495e;
  color: #35495e;
  background: transparent;
  border-radius: 4px;
  text-align: center;
  text-decoration: none;
  font-weight: bold;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background 0.2s;
}

.btn-secondary:hover {
  background: #f4f6f8;
}

.register-container {
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

.success-text {
  color: #5cb85c;
  font-size: 0.9rem;
  margin-top: 5px;
  font-weight: bold;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #35495e; /* Azul escuro do ecossistema Vue */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

button:disabled {
  background-color: #929fae;
  cursor: not-allowed;
}
</style>

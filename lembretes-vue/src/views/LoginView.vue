<script setup>
import { ref } from 'vue'
import BaseInput from '../components/BaseInput.vue'
import BaseButton from '../components/AuthForm.vue'

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
  <div class="min-h-screen flex items-center justify-center bg-stone-100 p-4 sm:p-6">
    <div class="w-full max-w-md space-y-4">
      <h2>Acessar Conta</h2>

      <!-- O modificador .prevent evita o recarregamento padrão da página -->
      <AuthForm
        title="Acessar Conta"
        subtitle="Entre com suas credenciais do Gmail"
        :is-loading="isLoading"
        :error-message="errorMessage"
        submit-text="Entrar"
        loading-text="Autenticando..."
        @submit="handleLogin"
      >

        <BaseInput
          id="email"
          v-model.trim="email"
          type="email"
          label="E-mail (Gmail):"
          placeholder="seuemail@gmail.com"
          required
        />

        <BaseInput
          id="password"
          v-model="password"
          type="password"
          label="Senha:"
          placeholder="Sua senha"
          required
        />

        <button type="submit" :disabled="isLoading">
          {{ isLoading ? 'Autenticando...' : 'Entrar' }}
        </button>

      </AuthForm>

      <div class="navigation-area">
        <p>Não tem uma conta?</p>
        <RouterLink to="/cadastro" class="btn-secondary">Criar Conta</RouterLink>
      </div>
    </div>
  </div>
</template>

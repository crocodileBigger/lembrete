<script setup>
import { ref } from 'vue'
import { RouterLink } from 'vue-router'
import BaseInput from '../components/BaseInput.vue'
import AuthForm from '../components/AuthForm.vue'

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
  <!-- Container Principal: Centraliza a tela inteira -->
  <div class="min-h-screen flex items-center justify-center bg-stone-100 p-10 sm:p-10">

    <!-- Wrapper da área de cadastro -->
    <div class="w-full max-w-3xl space-y-4">

      <AuthForm
        title="Criar Conta"
        subtitle="Preencha os dados abaixo para se cadastrar"
        :is-loading="isLoading"
        :error-message="errorMessage"
        submit-text="Cadastrar"
        loading-text="Cadastrando..."
        @submit="handleRegister"
      >
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <!-- Campo Nome Completo -->
        <BaseInput
          id="name"
          v-model.trim="name"
          type="text"
          label="Nome Completo:"
          placeholder="Seu nome"
          required
        />

        <!-- Campo E-mail -->
        <BaseInput
          id="email"
          v-model.trim="email"
          type="email"
          label="E-mail (Gmail):"
          placeholder="seuemail@gmail.com"
          required
        />

        <!-- Campo Senha -->
        <BaseInput
          id="password"
          v-model="password"
          type="password"
          label="Senha:"
          placeholder="Sua senha"
          required
        />

        <!-- Campo Confirmar Senha -->
        <BaseInput
          id="ConfirmPassword"
          v-model="ConfirmPassword"
          type="password"
          label="Confirmar Senha:"
          placeholder="Confirmar Sua senha"
          required
        />
      </div>
        <!-- Feedback de Sucesso (Se houver) -->
        <div
          v-if="successMessage"
          class="p-3 text-sm text-emerald-800 bg-emerald-100/80 rounded-xl border border-emerald-200 font-medium text-center"
        >
          {{ successMessage }}
        </div>

        <!-- Área de Navegação Interna do Card (Voltar para o Login) -->
        <div class="pt-4 border-t border-stone-200 text-center space-y-3">
          <RouterLink
            to="/"
            class="block w-full py-2.5 px-4 text-sm font-semibold text-stone-700 bg-transparent border border-stone-300 hover:bg-stone-200/60 rounded-xl transition-colors duration-150 text-center"
          >
            Voltar para o Login
          </RouterLink>
        </div>
      </AuthForm>

    </div>

  </div>
</template>

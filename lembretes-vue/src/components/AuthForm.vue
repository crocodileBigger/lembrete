<script setup>
import BaseButton from '../components/BaseButton.vue'

defineProps({
  title: { type: String, default: '' },
  subtitle: { type: String, default: '' },
  errorMessage: { type: String, default: '' },
})

defineEmits(['submit'])
</script>

<template>
  <div class="w-full max-w-2xl p-6 sm:p-10 bg-stone-50 rounded-3xl shadow-xl border border-stone-100">
    <!-- Cabeçalho -->
    <header v-if="title || subtitle" class="mb-8 text-center">
      <h2 v-if="title" class="text-3xl font-extrabold text-stone-900 tracking-tight">
        {{ title }}
      </h2>
      <p v-if="subtitle" class="text-base text-stone-600 mt-2">
        {{ subtitle }}
      </p>
    </header>

    <!-- Formulário -->
    <form @submit.prevent="$emit('submit')" class="space-y-6" novalidate>

      <slot />
      <div
        v-if="errorMessage"
        class="p-4 text-sm text-red-800 bg-red-100/70 rounded-xl border border-red-200 font-medium text-center"
      >
        {{ errorMessage }}
      </div>

      <!-- Botão de Enviar -->
      <BaseButton></BaseButton>

    </form>
  </div>
</template>

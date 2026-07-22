<script setup>
defineProps({
  isLoading: { type: Boolean, default: false },
  submitText: { type: String, default: 'Enviar' },
  loadingText: { type: String, default: 'Carregando...' }
})

defineEmits(['submit'])
</script>

<template>
  <button
    type="submit"
    :disabled="isLoading"
    @click="$emit('submit')"
    class="w-full py-3 px-5 text-base font-semibold text-white
           bg-amber-600 hover:bg-amber-700 active:bg-amber-800
           rounded-xl transition-all duration-150
           shadow-md shadow-amber-600/20
           focus:outline-none focus:ring-2 focus:ring-amber-500 focus:ring-offset-2
           disabled:opacity-60 disabled:cursor-not-allowed disabled:hover:bg-amber-600
           flex items-center justify-center gap-2"
  >
    <!-- Spinner SVG exibido apenas durante o carregamento -->
    <svg
      v-if="isLoading"
      class="animate-spin h-5 w-5 text-white"
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="0 0 24 24"
    >
      <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
      <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
    </svg>

    <span>{{ isLoading ? loadingText : submitText }}</span>
  </button>
</template>

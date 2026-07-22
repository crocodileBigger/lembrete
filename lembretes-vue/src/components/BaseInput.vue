<script setup>
defineProps({
  id: { type: String, required: true },
  label: { type: String, required: true },
  type: { type: String, default: 'text' },
  placeholder: { type: String, default: '' },
  required: { type: Boolean, default: false }
})

// Suporte nativo ao v-model (Vue 3.4+)
const model = defineModel({ type: String, default: '' })
</script>

<!-- BaseInput.vue -->
<template>
  <div class="space-y-1.5">
    <label
      :for="id"
      class="block text-sm font-medium text-stone-700"
    >
      {{ label }}
    </label>

    <input
      :id="id"
      v-model="model"
      :type="type"
      :placeholder="placeholder"
      :required="required"
      class="w-full rounded-lg border border-stone-300 bg-stone-50 px-4 py-2.5 text-base text-stone-800 placeholder-stone-400 shadow-sm outline-none transition-colors duration-150 focus:border-amber-500 focus:ring-2 focus:ring-amber-500/20"
    />
  </div>
</template>

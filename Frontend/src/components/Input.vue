<template>
    <input :type="type" :value="modelValue" @input="$emit('update:modelValue', $event.target.value)" class="px-3 py-2 border border-gray-400 rounded text-sm" :placeholder="placeholder" required />
</template>

<script setup>
const props = defineProps({
    type: {
        type: String,
        default: ""
    },
    placeholder: {
        type: String,
        default: ""
    },
    modelValue: String
});
</script>
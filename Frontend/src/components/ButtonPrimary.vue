<template>
    <div class="col-span-2 flex justify-center">
        <button @click="handleClick" class="inline-block px-8 py-2 bg-amber-500 text-white font-medium uppercase tracking-widest rounded-full hover:bg-amber-600">
            {{ buttonText }}
        </button>
    </div>
</template>
  
<script setup>
const props = defineProps({
    buttonText: {
        type: String,
        default: ""
    }
});

const emit = defineEmits(["click"]);

const handleClick = (event) => {
  emit("click", event);
};
</script>
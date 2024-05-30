<script setup>
import { cn } from "@/lib/utils";

const props = defineProps({
  class: { type: null, required: false },
});
</script>

<template>
  <div :class="cn('pt-0', props.class)">
    <slot />
  </div>
</template>

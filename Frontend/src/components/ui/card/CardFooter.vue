<script setup>
import { cn } from "@/lib/utils";

const props = defineProps({
  class: { type: null, required: false },
});
</script>

<template>
  <div :class="cn('flex items-center gap-y-1 px-6 py-3', props.class)">
    <slot />
  </div>
</template>

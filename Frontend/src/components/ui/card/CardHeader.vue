<script setup>
import { cn } from "@/lib/utils";

const props = defineProps({
  class: { type: null, required: false },
});
</script>

<template>
  <div :class="cn('flex flex-col gap-y-2 px-6 pt-6', props.class)">
    <slot />
  </div>
</template>

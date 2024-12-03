<script setup>
import { cn } from "@/lib/utils";

const props = defineProps({
  class: { type: null, required: false },
});
</script>

<template>
  <div :class="cn(
    'rounded-lg border border-slate-200 bg-white text-slate-950 shadow-sm w-full min-h-full',
    props.class
  )
    ">
    <slot />
  </div>
</template>
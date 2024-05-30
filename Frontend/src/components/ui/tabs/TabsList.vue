<script setup>
import { computed } from "vue";
import { TabsList } from "radix-vue";
import { cn } from "@/lib/utils";

const props = defineProps({
  loop: { type: Boolean, required: false },
  asChild: { type: Boolean, required: false },
  as: { type: null, required: false },
  class: { type: null, required: false },
});

const delegatedProps = computed(() => {
  const { class: _, ...delegated } = props;

  return delegated;
});
</script>

<template>
  <TabsList v-bind="delegatedProps" :class="cn(
    'inline-flex h-10 items-center justify-center rounded-md bg-zinc-50 p-1 text-slate-600',
    props.class
  )
    ">
    <slot />
  </TabsList>
</template>

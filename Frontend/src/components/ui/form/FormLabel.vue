<script setup>
import { useFormField } from "./useFormField";
import { cn } from "@/lib/utils";
import { Label } from "@/components/ui/label";

const props = defineProps({
  for: { type: String, required: false },
  asChild: { type: Boolean, required: false },
  as: { type: null, required: false },
  class: { type: null, required: false },
});

const { error, formItemId } = useFormField();
</script>

<template>
  <Label
    :class="cn(error && 'text-red-500 dark:text-red-900', props.class)"
    :for="formItemId"
  >
    <slot />
  </Label>
</template>

<template>
    <Dialog>
        <DialogTrigger as-child>
            <Button class=" border-2 border-slate-300">
                Seleccionar ubicación
            </Button>
        </DialogTrigger>
        <DialogContent class="sm:max-w-[625px]">
            <DialogHeader>
                <DialogTitle>Edit profile</DialogTitle>
                <DialogDescription>
                    Hace click en el mapa y presiona "Guardar" para marcar tu ubicación
                </DialogDescription>
            </DialogHeader>
            <MapMarker @update-marker="handleMarkerUpdate" />
            <DialogFooter>
                <p v-if="hasSaved">Se ha guardado</p>
                <Button type="submit" @click="saveMarker">
                    Guardar
                </Button>

            </DialogFooter>
        </DialogContent>
    </Dialog>

</template>
<script setup>
import MapMarker from "@/components/MapMarker.vue";
import { ref, defineEmits } from "vue";
import { Button } from '@/components/ui/button'
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from '@/components/ui/dialog'

const markerPosition = ref(null);
const hasSaved = ref(false);

const handleMarkerUpdate = (newMarkerPosition) => {
    markerPosition.value = newMarkerPosition;
    hasSaved.value = false
};

const emit = defineEmits(['save-marker']);
const saveMarker = () => {
    hasSaved.value = true
    emit('save-marker', markerPosition.value);
};

</script>
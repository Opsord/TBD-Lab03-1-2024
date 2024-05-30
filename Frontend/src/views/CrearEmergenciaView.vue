<script setup>
import {
    FormControl,
    FormDescription,
    FormField,
    FormItem,
    FormLabel,
    FormMessage
} from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { Checkbox } from '@/components/ui/checkbox';
import { Button } from '@/components/ui/button';
import ScrollArea from '@/components/ui/scroll-area/ScrollArea.vue';
import { store, fetchUserRole } from '@/store';
import axios from 'axios'
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue'
import DialogMap from "@/components/DialogMap.vue";

const router = useRouter();

const markerPosition = ref(null);
const handleSaveMarker = (newMarkerPosition) => {
    markerPosition.value = newMarkerPosition;
    console.log('Parent marker position:', markerPosition.value);
    formModel.value.location.latitude = markerPosition.value.position.lat
    formModel.value.location.longitude = markerPosition.value.position.lng
};

const formModel = ref({
    title: '',
    status: false,
    description: '',
    coordinator: '',
    location: {
        latitude: 0,
        longitude: 0,
    }
});

const habilidadModel = ref([]);
const attributes = ref(null);

const availableAttributes = ref(attributes);
const selectedAttributes = ref([]);

const addAttribute = (attribute, compatibility) => {
    selectedAttributes.value.push({ attribute_id: attribute.attribute_id, attribute: attribute.attribute, compatibility: compatibility });
    availableAttributes.value = availableAttributes.value.filter(item => item.attribute_id !== attribute.attribute_id);
}

const removeAttribute = (attribute) => {
    selectedAttributes.value = selectedAttributes.value.filter(item => item.attribute_id !== attribute.attribute_id);
    availableAttributes.value.push(attribute);
}

async function fetchAttributes() {
    try {
        const response = await axios.get(`http://localhost:8090/attributes/all`, {
            headers: {
                Authorization: `Bearer ${store.token.token}`
            }
        });
        console.log("TERMINADO - Get atributos: ", response.data);
        attributes.value = response.data;
    } catch (error) {
        console.error('There was an error fetching the user data:', error);
    }
}

async function createEmergency(emergency) {
    try {
        const user = await fetchUserRole();
        emergency.coordinator = user.rut;

        const response = await axios.post(`http://localhost:8090/emergencies/create`, emergency, {
            headers: {
                Authorization: `Bearer ${store.token.token}`
            }
        });
        console.log("TERMINADO - Crear Emergencia: ", response.data.emergency_id);
        return response.data;
    } catch (error) {
        console.log(error);
    }
}

async function createEmergencyAttribute(emergency) {
    console.log("Emergencia: ", emergency);
    console.log("Atributos seleccionados: ", selectedAttributes.value);

    try {
        const emergencyAttributes = selectedAttributes.value.map(({ attribute_id, compatibility }) => ({
            attribute: attribute_id,
            compatibility,
            emergency: emergency.emergency_id
        }));
        console.log("Lista de emergencia-atributo: ", emergencyAttributes);

        const response = await axios.post(`http://localhost:8090/emergencyAttribute/createVarious`, emergencyAttributes, {
            headers: {
                Authorization: `Bearer ${store.token.token}`
            }
        });
        console.log("Emergencia-Atributo: ", response.data);
        return response.data;
    } catch (err) {
        console.log('Error config: ', err);
    }
}

function resetForm() {
    formModel.value = {
        title: '',
        status: false,
        description: '',
        coordinator: '',
        location: {
            latitude: 0,
            longitude: 0,
        }
    };
    selectedAttributes.value = [];
    availableAttributes.value = [...attributes.value];
}

async function onSubmit() {
    if (formModel.value.title && formModel.value.description && selectedAttributes.value.length > 0 && formModel.value.location.latitude !== 0 && formModel.value.location.longitude !== 0) {
        const emergency = await createEmergency(formModel.value);
        await createEmergencyAttribute(emergency);
        alert('La emergencia se ha creado correctamente.');
        resetForm();
        await fetchAttributes();
    } else {
        alert('Por favor complete todos los campos.');
    }
}

onMounted(fetchAttributes);
</script>
<template>
    <div class="flex justify-center">
        <div class="rounded-lg border border-slate-200 bg-white text-slate-950 shadow-sm w-6/12 my-4 p-10">
            <form class="grid grid-cols-2 gap-10" @submit="onSubmit">
                    <FormField name="titulo">
                        <FormItem>
                            <FormLabel>Titulo</FormLabel>
                                <FormControl>
                                    <Input type="text" placeholder="Titulo de la Emergencia" v-model="formModel.title" />
                                </FormControl>
                            <FormMessage />
                        </FormItem>
                    </FormField>
                    <FormField name="descripcion">
                        <FormItem>
                            <FormLabel>Descripción</FormLabel>
                                <FormControl>
                                    <Input type="text" placeholder="Descripción" v-model="formModel.description" />
                                </FormControl>
                            <FormMessage />
                        </FormItem>
                    </FormField>
                    <div class="space-y-3">
                        <p class="text-sm font-medium">Habilidades disponibles</p>
                        <ScrollArea class="h-[200px] w-full">
                            <div class="space-y-2" v-if="availableAttributes && availableAttributes.length">
                                <li class="flex gap-2 items-center text-sm" v-for="data in availableAttributes" :key="data.attribute_id">
                                    <Button type="button"
                                        v-on:click="addAttribute(data, 1)">
                                        ✅
                                    </Button>
                                    <Button type="button"
                                        v-on:click="addAttribute(data, 0)">
                                        ❌
                                    </Button>
                                    <div>
                                        {{ data.attribute }}
                                    </div>
                                </li>
                            </div>
                        </ScrollArea>
                    </div>
                    <div class="space-y-3">
                        <p class="text-sm font-medium">Habilidades seleccionadas</p>
                        <div class="flex">
                            <ScrollArea class="h-[200px] w-full">
                                <div class="space-y-2" v-if="selectedAttributes && selectedAttributes.length">
                                    <li class="flex gap-2 items-center text-sm" v-for="data in selectedAttributes" :key="data.attribute_id">
                                        <Button type="button" v-on:click="removeAttribute(data)">
                                            ❌
                                        </Button>
                                        {{ data.attribute }}
                                        <p v-if="data.compatibility === 1" class="text-green-600">&nbsp;Compatible</p>
                                        <p v-else-if="data.compatibility === 0" class="text-red-600">&nbsp;Incompatible</p>
                                    </li>
                                </div>
                            </ScrollArea>
                        </div>
                    </div>
                <DialogMap @save-marker="handleSaveMarker" />
                <div></div>
                <div>
                    <FormField name="activa" type="checkbox" v-model="formModel.status">
                        <FormItem class="flex items-end flex-row-reverse justify-end gap-4">
                            <FormLabel>¿Marcar como activa?</FormLabel>
                                <FormControl>
                                    <Checkbox id="disponibilidad" :checked="formModel.status"
                                        @update:checked="value => formModel.status = value" class="bg-slate-100 flex" />
                                </FormControl>
                            <FormMessage />
                        </FormItem>
                    </FormField>
                </div>
                <div class="flex justify-center col-span-2">
                    <Button variant="ghost" class="w-1/3 border border-zinc-600" type="button" v-on:click="onSubmit()">
                        Enviar
                    </Button>
                </div>
            </form>
        </div>
    </div>
</template>
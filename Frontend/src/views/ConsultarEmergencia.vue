<template>
    <div class="flex justify-center">
        <div class="space-y-5 rounded-lg border border-slate-200 bg-white text-slate-950 shadow-sm w-7/12 my-4 p-10">
            <div class="space-y-2">
                <h1 class="text-2xl font-semibold">Estadísticas Voluntarios</h1>
                <p>Promedio de habilidades que poseen los voluntarios registrados.</p>
            </div>
            <div class="overflow-hidden w-fit rounded-lg border border-slate-200">
                <table class="bg-gray-50">
                    <tbody>
                        <tr class="text-sm border-b border-slate-200">
                            <td class="font-semibold px-4 py-2">Voluntarios registrados</td>
                            <td class="px-4 py-2">{{ volunteers_length }}</td>
                        </tr>
                        <tr class="text-sm border-b border-slate-200">
                            <td class="font-semibold px-4 py-2">Habilidades disponibles</td>
                            <td class="px-4 py-2">{{ attributes_length }}</td>
                        </tr>
                        <tr class="text-sm">
                            <td class="font-semibold px-4 py-2">Promedio habilidades</td>
                            <td class="px-4 py-2">{{ average.toFixed(2) }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="flex justify-center">
        <div class="space-y-5 rounded-lg border border-slate-200 bg-white text-slate-950 shadow-sm w-7/12 my-4 p-10">
            <div class="space-y-2">
                <h1 class="text-2xl font-semibold">Voluntarios Cercanos</h1>
                <p>Busqueda de voluntarios más cercanos a una emergencia específica.</p>
            </div>
            <form @submit.prevent="handleSubmit" class="grid grid-cols-5 gap-4">
                <div class="col-span-2 space-y-2">
                    <label for="emergencySelect" class="text-sm font-medium">Seleccionar emergencia</label>
                    <select id="emergencySelect" v-model="emergencieSelected"
                        class="flex h-10 w-full rounded-md border border-slate-300 bg-gray-50 px-3 py-2 text-sm">
                        <option v-for="emergency in emergencies" :key="emergency.emergency_id" :value="emergency">
                            {{ emergency.title }}
                        </option>
                    </select>
                </div>
                <div class="space-y-2">
                    <label for="radiusInput" class="text-sm font-medium">Distancia (km)</label>
                    <input id="radiusInput" v-model="radius" type="number"
                        class="flex h-10 w-full rounded-md border border-slate-300 bg-gray-50 px-3 py-2 text-sm">
                </div>
                <div class="space-y-2">
                    <label for="quantityInput" class="text-sm font-medium">Cantidad</label>
                    <input id="quantityInput" v-model="quantity" type="number"
                        class="flex h-10 w-full rounded-md border border-slate-300 bg-gray-50 px-3 py-2 text-sm">
                </div>
                <div class="flex items-end">
                    <Button variant="ghost" class="w-full border border-zinc-600" type="button" @click="handleSubmit">
                        Buscar
                    </Button>
                </div>
            </form>
            <div class="space-y-2">
                <h2 class="font-semibold uppercase">Voluntarios encontrados</h2>
                <div v-if="volunteers.length" class="flex flex-wrap gap-2">
                    <div class="flex w-fit gap-2 text-sm px-4 py-3 bg-gray-50 rounded-md border border-gray-300"
                        v-for="volunteer in volunteers" :key="volunteer.rut">
                        <div class="font-semibold">
                            {{ volunteer.name }} {{ volunteer.lastname }}
                        </div>
                        <div>
                            {{ volunteer.rut }}
                        </div>
                        <div>
                            <span v-if="volunteer.availability" class="availability-indicator bg-green-600"></span>
                            <span v-else class="availability-indicator bg-red-600"></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios';
import { store } from '@/store';
import { ref, onMounted } from 'vue';
import { Button } from '@/components/ui/button';

const volunteers_length = ref(0);
const attributes_length = ref(0);
const average = ref(0);

async function fetchStatsVolunteers() {
    try {
        // Cambiar por ruta para obtener todos los voluntarios registrados
        const response_volunteers = await axios.get('http://localhost:8090/usersMongo/', {
            headers: {
                Authorization: `Bearer ${store.token}`
            }
        });
        console.log("Fetching volunteers:", response_volunteers);
        volunteers_length.value = response_volunteers.data.length;
    } catch (error) {
        console.log("Error fetching volunteers:", error);
    }

    try {
        const response_attributes = await axios.get('http://localhost:8090/skillsMongo/', {
            headers: {
                Authorization: `Bearer ${store.token}`
            }
        });
        console.log("Fetching attributes:", response_attributes);
        attributes_length.value = response_attributes.data.length;
    } catch (error) {
        console.log("Error fetching attributes:", error);
    }

    try {
        // Cambiar por ruta para obtener promedio de habilidades
        const response_average = await axios.get('http://localhost:8090/usersMongo/promedio-habilidades', {
            headers: {
                Authorization: `Bearer ${store.token}`
            }
        });
        console.log("Fetching average:", response_average);
        average.value = response_average.data;
    } catch (error) {
        console.log("Error fetching average:", error);
    }
}

const emergencies = ref([]);
const emergencieSelected = ref(null);
const radius = ref(0);
const quantity = ref(0);
const volunteers = ref([]);

async function fetchEmergencies() {
    try {
        const response = await axios.get('http://localhost:8090/emergencies/all', {
            headers: {
                Authorization: `Bearer ${store.token}`
            }
        });
        console.log("Fetched emergencies: ", response.data);
        emergencies.value = response.data;
    } catch (error) {
        console.error('Error fetching emergencies:', error);
    }
}

async function fetchNearbyVolunteers(emergency, radius, quantity) {
    try {
        const response = await axios.get(`http://localhost:8090/emergencies/nearby/${emergency.emergency_id}/${radius}/${quantity}`, {
            headers: {
                Authorization: `Bearer ${store.token}`
            }
        });
        console.log(`Fetched volunteers for emergency ${emergency.emergency_id}: `, response.data);
        volunteers.value = response.data;
    } catch (error) {
        console.error(`Error fetching nearby volunteers for emergency ${emergency.emergency_id}:`, error);
    }
}

function handleSubmit() {
    if (emergencieSelected.value && radius.value > 0 && quantity.value > 0) {
        fetchNearbyVolunteers(emergencieSelected.value, radius.value, quantity.value);
    } else {
        alert('Por favor complete todos los campos.');
    }
}

onMounted(() => {
    fetchStatsVolunteers();
    fetchEmergencies();
});

</script>

<style scoped>
.availability-indicator {
    display: inline-block;
    width: 10px;
    height: 10px;
    border-radius: 50%;
}
</style>
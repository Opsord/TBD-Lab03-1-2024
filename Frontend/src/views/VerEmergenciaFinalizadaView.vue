<template>
    <div class="flex justify-center my-4">
        <div class="grid grid-cols-2 gap-4 w-10/12" v-if="emergencia && emergencia.length">
            <div v-for="data in emergencia" :key="data.id_registro">
                <div>
                    <Card>
                        <CardHeader>
                            <CardTitle>{{ data.title }}</CardTitle>
                            <CardContent>
                                {{ data.description }}
                            </CardContent>
                            <CardDescription>
                                Tareas registradas:
                                <p class="truncate" v-for="tarea in data.tareas" :key="tarea.task_id">
                                    {{ tarea.description }}</p>
                            </CardDescription>
                        </CardHeader>
                        <CardFooter class="flex flex-col items-start">
                            <p>Voluntarios registrados:</p>
                            <p v-for="voluntario in data.voluntarios" :key="voluntario.rut">
                                {{ voluntario.name }} {{
                                    voluntario.last_name }}</p>
                        </CardFooter>
                    </Card>
                </div>
            </div>
        </div>
    </div>
</template>


<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue'
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { store, fetchUserRole } from '@/store';

const emergencia = ref(null)
const token = localStorage.getItem("authToken")


async function fetchEmergencia() {
    try {
        const response = await axios.get('http://localhost:8090/emergencies/closed');
        emergencia.value = response.data;
        const emergencias = response.data;
        const newEmergency = emergencias.map(async (emergencia) => {
            const fetchAsociados = await axios.get(`http://localhost:8090/emergencies/nearby/${emergencia.emergency_id}/1000000000000000000000000000000/10`)
            const selected = fetchAsociados.data
            const select = selected.map(value => ({ value, sort: Math.random() }))
                .sort((a, b) => a.sort - b.sort)
                .map(({ value }) => value)
                .splice(-2)
            return { ...emergencia, voluntarios: select }
        })
        const result = await Promise.all(newEmergency);
        emergencia.value = result

    } catch (error) {
        console.error('There was an error fetching the user data:', error);
    }
}

async function fetchTarea() {
    const tareaGet = "http://localhost:8090/tasks/emergency_id/";
    if (emergencia.value && emergencia.value.length > 0) {
        try {
            const fetchPromises = emergencia.value.map(async (emergenciaEach) => {
                const response = await axios.get(`${tareaGet}${emergenciaEach.emergency_id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                const tareas = response.data;
                return { ...emergenciaEach, tareas: tareas };
            });
            const result = await Promise.all(fetchPromises);
            emergencia.value = result;
            console.log("TERMINA - Emergencia Id: ", result);
        } catch (error) {
            console.error('There was an error fetching the volunteers data:', error);
        }
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

// If you want to fetch data when the component mounts
onMounted(async () => { await fetchEmergencia(); await fetchTarea() });

</script>
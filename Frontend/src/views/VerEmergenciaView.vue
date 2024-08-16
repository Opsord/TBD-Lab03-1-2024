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
                                <p v-for="tarea in data.tareas" :key="tarea.task_id">
                                    {{ tarea.description }}</p>
                            </CardDescription>
                        </CardHeader>
                        <CardFooter class="flex flex-col items-start space-y-1">
                            <p class="text-sm">Voluntarios registrados:</p>
                            <div class="flex flex-wrap gap-1">
                                <p class="text-sm px-3 py-1 bg-gray-200 rounded-2xl" v-for="user in data.volunteers" :key="user.user_id">{{ user.name }} {{ user.last_name }}</p>
                            </div>
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
import { store } from '@/store';

const emergencia = ref(null)

async function fetchEmergencia() {
    try {
        const response = await axios.get(`http://localhost:8090/emergencies/active`, {
                    headers: {
                        Authorization: `Bearer ${store.token}`
                    }
                });
        console.log("TERMINA - Emergencias Active: ", response.data);
        emergencia.value = response.data;
    } catch (error) {
        console.error('There was an error fetching the user data:', error);
    }
}

async function fetchTarea() {
    if (emergencia.value && emergencia.value.length > 0) {
        try {
            const fetchPromises = emergencia.value.map(async (emergenciaEach) => {
                const response = await axios.get(`http://localhost:8090/tasks/emergency_id/${emergenciaEach.emergency_id}`, {
                    headers: {
                        Authorization: `Bearer ${store.token}`
                    }
                });
                const tareas = response.data;
                return { ...emergenciaEach, tareas: tareas };
            });
            const result = await Promise.all(fetchPromises);    
            emergencia.value = result;
            console.log("TERMINA - Tarea: ", result);
        } catch (error) {
            console.error('There was an error fetching the volunteers data:', error);
        }
    }
}

async function fetchVoluntarios() {
    if (emergencia.value && emergencia.value.length > 0) {
        try {
            const fetchPromises = emergencia.value.map(async (emergenciaEach) => {
                const response = await axios.get(`http://localhost:8090/emergencies/getVolunteersByEmergencyId/${emergenciaEach.emergency_id}`, {
                    headers: {
                        Authorization: `Bearer ${store.token}`
                    }
                });
                const volunteers = response.data;
                return { ...emergenciaEach, volunteers: volunteers };
            });
            const result = await Promise.all(fetchPromises);
            emergencia.value = result;
            console.log("TERMINA - Voluntarios: ", result);
        } catch (error) {
            console.error('There was an error fetching the volunteers data:', error);
        }
    }
}

onMounted(async () => { await fetchEmergencia(); await fetchTarea(); await fetchVoluntarios(); });

</script>
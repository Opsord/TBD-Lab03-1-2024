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
                                    {{ tarea.type }}</p>
                            </CardDescription>
                        </CardHeader>
                        <CardFooter class="flex flex-col items-start">
                            <p>Voluntarios registrados:</p>
                            <div v-for="tarea in data.tareas" :key="tarea.task_id">
                                <p v-for="user in tarea.user" :key="user.rut">&nbsp;{{ user.name }} {{ user.lastname }}</p>
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
        const response = await axios.get(`http://localhost:8090/emergencies/active`);
        console.log("TERMINA - Emergencias Active: ", response.data);
        emergencia.value = response.data;
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
                        Authorization: `Bearer ${store.token.token}`
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

async function fetchVoluntarios() {
    const rankingGet = "http://localhost:8090/rankings/task_id/";
    if (emergencia.value && emergencia.value.length > 0) {
        try {
            const fetchPromises = emergencia.value.map(async (emergenciaEach) => {
                const tasks = emergenciaEach.tareas;
                const taskPromises = tasks.map(async (task) => {
                    const response = await axios.get(`${rankingGet}${task.task_id}`, {
                        headers: {
                            Authorization: `Bearer ${store.token.token}`
                        }
                    });
                    console.log("AQUI EL ERROR: ", response.data);
                    const user = response.data.map(ranking => ranking.user);
                    return { ...task, user: user };
                });
                const taskResults = await Promise.all(taskPromises);
                return { ...emergenciaEach, tareas: taskResults };
            });
            
            const results = await Promise.all(fetchPromises);
            emergencia.value = results;
            console.log("TERMINA - Tarea Id: ", results);
        } catch (error) {
            console.error(error);
        }
    }
}

onMounted(async () => { await fetchEmergencia(); await fetchTarea(); await fetchVoluntarios() });

</script>
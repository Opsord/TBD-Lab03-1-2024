<template>
    <CoordinatorTabs v-if="role === 'COORDINATOR'"/>
    <VolunteerTabs v-else-if="role === 'VOLUNTEER'"/>
</template>

<script setup>
import CoordinatorTabs from '../components/CoordinatorTabs.vue';
import VolunteerTabs from '../components/VolunteerTabs.vue';
import { fetchUserRole } from '@/store';
import { ref, onMounted } from 'vue';

const role = ref('SISTEMA');

onMounted(async () => {
    try {
        const user = await fetchUserRole();
        role.value = user.role;
    } catch (error) {
        console.error("Error al obtener el rol del usuario:", error);
    }
});
</script>
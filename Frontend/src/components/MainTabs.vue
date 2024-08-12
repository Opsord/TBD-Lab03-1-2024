<template>
    <CoordinatorTabs v-if="role === 'COORDINADOR'" />
    <VolunteerTabs v-else-if="role === 'VOLUNTARIO'" />
</template>

<script setup>
import CoordinatorTabs from '../components/CoordinatorTabs.vue';
import VolunteerTabs from '../components/VolunteerTabs.vue';
import { fetchUserRole } from '@/store';
import { ref, onMounted } from 'vue';

const role = ref('SISTEMA');

onMounted(async () => {
    console.log("enter")
    try {
        const user = await fetchUserRole();
        console.log(user)
        role.value = user.role;
        console.log(user.role)
    } catch (error) {
        console.error("Error al obtener el rol del usuario:", error);
    }
});
</script>
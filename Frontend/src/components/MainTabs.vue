<template>
    <!-- Mostrar un loader mientras se obtiene el rol -->
    <div v-if="loading">Cargando...</div>
    
    <!-- Mostrar los tabs según el rol -->
    <CoordinatorTabs v-if="role === 'COORDINADOR'" />
    <VolunteerTabs v-else-if="role === 'VOLUNTARIO'" />
  </template>
  
  <script setup>
  import CoordinatorTabs from '../components/CoordinatorTabs.vue';
  import VolunteerTabs from '../components/VolunteerTabs.vue';
  import { fetchUserRole } from '@/store';
  import { ref, onMounted } from 'vue';
  
  // Inicializar role y loading
  const role = ref('SISTEMA');
  const loading = ref(true); // Estado de carga
  
  onMounted(async () => {
    try {
      const user = await fetchUserRole();
      role.value = user.role;
    } catch (error) {
      console.error("Error al obtener el rol del usuario:", error);
    } finally {
      loading.value = false; // Cuando termine la carga, actualizar el estado de loading
    }
  });
  </script>
  
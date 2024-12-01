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
import { ref, onMounted, watch } from 'vue';
import { store } from '@/store';  // Asegúrate de importar tu store

// Inicializar role y loading
const role = ref('SISTEMA');
const loading = ref(true); // Estado de carga

// Función para obtener el rol de usuario
const updateUserRole = async () => {
  loading.value = true; // Establecer que está cargando
  try {
    const user = await fetchUserRole();
    role.value = user.role;  // Actualizar el rol con la respuesta de la API
  } catch (error) {
    console.error("Error al obtener el rol del usuario:", error);
  } finally {
    loading.value = false; // Terminar el estado de carga
  }
};

// Inicializar el rol cuando el componente se monta
onMounted(() => {
  updateUserRole(); // Obtener el rol por primera vez
});

// Observar cambios en store.token
watch(() => store.token, (newToken, oldToken) => {
  // Si el token cambia, obtener el rol nuevamente
  if (newToken) {
    updateUserRole();
  }
});
</script>

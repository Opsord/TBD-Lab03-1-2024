<script setup>
import { store } from '@/store';
import { reactive, computed, ref } from 'vue';

const supplies = reactive([]);

const loading = ref(true);

const fetchSupplies = async () => {
  const response = await fetch('http://localhost:8090/supplies/all',{
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${store.token}`
    }
  });
  const data = await response.status === 200 ? await response.json() : null;
  supplies.push(...data);
  loading.value = false;
};

const updateSupply = async (supply) => {
  const response = await fetch(`http://localhost:8090/supplies/update`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${store.token}`
    },
    body: JSON.stringify(supply)
  });
  const updated = await response.status === 200 ? await response.json() : null;
  return updated;
};

const createSupply = async (supply) => {
  const response = await fetch(`http://localhost:8090/supplies/create`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${store.token}`
    },
    body: JSON.stringify(supply)
  });
  const created = await response.status === 200 ? await supply : null;
  return created;
};

fetchSupplies();

const sortBy = reactive({ column: null, order: 'asc' });

const sortedSupplies = computed(() => {
  if (!sortBy.column) return supplies;

  return [...supplies].sort((a, b) => {
    const valA = a[sortBy.column];
    const valB = b[sortBy.column];

    if (valA < valB) {
      return sortBy.order === 'asc' ? -1 : 1;
    } else if (valA > valB) {
      return sortBy.order === 'asc' ? 1 : -1;
    } else {
      return 0;
    }
  });
});

const sortTable = (column) => {
  if (sortBy.column === column) {
    sortBy.order = sortBy.order === 'asc' ? 'desc' : 'asc';
  } else {
    sortBy.column = column;
    sortBy.order = 'asc';
  }
};

// Modal control
const showModal = reactive({ isVisible: false, selectedSupply: null });

const openModal = (supply) => {
  showModal.selectedSupply = { ...supply }; // Create a copy of the selected supply
  showModal.isVisible = true;
};


const handleOutsideClick = (event) => {
  if (event.target === event.currentTarget) {
    closeModal();
  }
};

const closeModal = () => {
  showModal.isVisible = false;
  showModal.selectedSupply = null;
};

const saveChanges = async () => {
  try {
    const updatedSupply = await updateSupply(showModal.selectedSupply);

    const index = supplies.findIndex(s => s.supply_id === updatedSupply.supply_id);
    if (index !== -1) {
      supplies[index] = { ...updatedSupply };
    }

    closeModal();
  } catch (error) {
    console.error("Error al actualizar el insumo:", error);
  }
};


const isAddingModalVisible = reactive({ value: false });

const newSupply = reactive({
  name: '',
  description: '',
  classification: ''
});

const openAddingModal = () => {
  isAddingModalVisible.value = true; // Abre la modal
};

const closeAddingModal = () => {
  isAddingModalVisible.value = false;
  newSupply.name = '';
  newSupply.description = '';
  newSupply.classification = '';
};


const handleSaveOutsideClick = (event) => {
  if (event.target === event.currentTarget) {
    closeAddingModal();
  }
};

const saveNewSupply = async () => {
  if (newSupply.name && newSupply.description && newSupply.classification) {
    try {
      const supplyToAdd = {
        name: newSupply.name,
        description: newSupply.description,
        classification: newSupply.classification
      };

      const createdSupply = await createSupply(supplyToAdd);

      if (createdSupply) {
        supplies.push(createdSupply);
      } else {
        alert("Hubo un error al crear el insumo.");
      }

      closeAddingModal();
    } catch (error) {
      console.error("Error al guardar el nuevo insumo:", error);
      alert("Hubo un error al guardar el insumo.");
    }
  } else {
    alert("Por favor, complete todos los campos.");
  }
};

</script>

<template>
  <div class="grid place-items-center h-full">
    <h2 v-if="loading" class="text-center text-2xl py-5"> Cargando insumos...</h2>

    <table v-if="supplies && supplies.length > 0" class="w-[90%] my-5 table-auto border-collapse border border-gray-200">
      <thead class="select-none bg-gray-600 text-white border border-b-black">
        <tr>
          <th 
            @click="sortTable('supply_id')" 
            class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
            :class="{'bg-[#14a499]': sortBy.column === 'supply_id'}">
            ID
            <span v-if="sortBy.column === 'supply_id'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
          </th>
          <th 
            @click="sortTable('name')" 
            class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
            :class="{'bg-[#14a499]': sortBy.column === 'name'}">
            Nombre
            <span v-if="sortBy.column === 'name'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
          </th>
          <th 
            @click="sortTable('description')" 
            class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
            :class="{'bg-[#14a499]': sortBy.column === 'description'}">
            Descripción
            <span v-if="sortBy.column === 'description'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
          </th>
          <th 
            @click="sortTable('classification')" 
            class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
            :class="{'bg-[#14a499]': sortBy.column === 'classification'}">
            Clasificación
            <span v-if="sortBy.column === 'classification'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
          </th>
          <th class="px-4 py-2 border border-b-red-500 border-black text-left">
            Acción
          </th>
        </tr>
      </thead>
      <tbody>
        <tr class="odd:bg-gray-100" v-for="supply in sortedSupplies" :key="supply.supply_id">
          <td class="px-4 py-2 border border-gray-300">{{ supply.supply_id }}</td>
          <td class="px-4 py-2 border border-gray-300">{{ supply.name }}</td>
          <td class="px-4 py-2 border border-gray-300">{{ supply.description }}</td>
          <td class="px-4 py-2 border border-gray-300">{{ supply.classification }}</td>
          <td class="px-4 py-2 border border-gray-300">
            <button @click="openModal(supply)" class="bg-blue-500 text-white py-1 px-3 rounded hover:saturate-200">
              Editar
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    <p v-else-if="!loading">No se encontraron suministros</p>

    <!-- Modal -->
    <div @click="handleOutsideClick" v-if="showModal.isVisible" class="fixed inset-0 bg-gray-500 bg-opacity-50 flex justify-center items-center z-50">
      <div class="bg-white p-6 rounded-lg w-1/3">
        <h2 class="text-xl font-bold mb-4">Editar Insumo</h2>
        <div class="mb-4">
          <label for="name" class="block">Nombre</label>
          <input v-model="showModal.selectedSupply.name" type="text" id="name" class="w-full px-4 py-2 border border-gray-300 rounded">
        </div>
        <div class="mb-4">
          <label for="description" class="block">Descripción</label>
          <textarea v-model="showModal.selectedSupply.description" id="description" class="w-full px-4 py-2 border border-gray-300 rounded"></textarea>
        </div>
        <div class="mb-4">
          <label for="classification" class="block">Clasificación</label>
          <input v-model="showModal.selectedSupply.classification" type="text" id="classification" class="w-full px-4 py-2 border border-gray-300 rounded">
        </div>
        <div class="flex justify-between">
          <button @click="closeModal" class="bg-gray-300 py-2 px-4 rounded hover:bg-red-200">Cancelar</button>
          <button @click="saveChanges" class="bg-green-500 text-white py-2 px-4 rounded hover:contrast-125">Guardar</button>
        </div>
      </div>
    </div>

    <button @click="openAddingModal" 
    class="fixed bottom-8 right-16 bg-[#14a499] grid place-content-center text-5xl pt-2 w-14  h-14 text-white p-4 rounded-full shadow-2xl focus:outline-none hover:saturate-200">
    +
  </button>

  <!-- Modal para new item -->
  <div @click="handleSaveOutsideClick" v-if="isAddingModalVisible.value" class="fixed inset-0 bg-gray-500 bg-opacity-50 flex justify-center items-center z-50">
    <div class="bg-white p-6 rounded-lg w-1/3">
      <h2 class="text-xl font-bold mb-4">Añadir Nuevo Insumo</h2>
      <div class="mb-4">
        <label for="name" class="block">Nombre</label>
        <input v-model="newSupply.name" type="text" id="name" class="w-full px-4 py-2 border border-gray-300 rounded" />
      </div>
      <div class="mb-4">
        <label for="description" class="block">Descripción</label>
        <textarea v-model="newSupply.description" id="description" class="w-full px-4 py-2 border border-gray-300 rounded"></textarea>
      </div>
      <div class="mb-4">
        <label for="classification" class="block">Clasificación</label>
        <input v-model="newSupply.classification" type="text" id="classification" class="w-full px-4 py-2 border border-gray-300 rounded" />
      </div>
      <div class="flex justify-between">
        <button @click="closeAddingModal" class="bg-gray-300 py-2 px-4 rounded hover:bg-red-200">Cancelar</button>
        <button @click="saveNewSupply" class="bg-green-500 text-white py-2 px-4 rounded hover:contrast-125">Guardar</button>
      </div>
    </div>
  </div>
  </div>
</template>

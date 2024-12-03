  <script setup>
  import { store } from '@/store';
  import { reactive, computed, ref } from 'vue';

  // Lista de inventarios
  const inventories = reactive([]);
  const emergencys = reactive([]);
  const supplies = reactive([]);


  // Estado de carga
  const loading = ref(true);

  const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

  // Llamada a la API para obtener los inventarios
  const fetchInventories = async () => {
    const response = await fetch('http://localhost:8090/inventoryEntries/all', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${store.token}`,
      },
    });
    const data = await response.json();
    inventories.push(...data);
    loading.value = false;
  };

  const fetchEmergencys = async () => {
    const response = await fetch('http://localhost:8090/emergencies/all', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${store.token}`,
      },
    });
    const data = await response.json();
    emergencys.push(...data);
  };


  const fetchSupplies = async () => {
    const response = await fetch('http://localhost:8090/supplies/all', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${store.token}`
      }
    });
    const data = await response.status === 200 ? await response.json() : null;
    supplies.push(...data);
  };

  // Llamar a la API para obtener los inventarios
  fetchInventories();

  fetchEmergencys();
  fetchSupplies();

  // Función para obtener el nombre de un supply a partir del supply_id
  const getSupplyName = (supply_id) => {
    const supply = supplies.find((s) => s.supply_id === supply_id);
    return supply ? supply.name : 'Desconocido';
  };

  // Función para obtener el nombre de una emergencia a partir del emergency_id
  const getEmergencyName = (emergency_id) => {
    const emergency = emergencys.find((e) => e.emergency_id === emergency_id);
    return emergency ? emergency.title : 'Desconocido';
  };

  // Funciones para editar y agregar inventarios
  const updateInventory = async (inventory) => {
    const response = await fetch(`http://localhost:8090/inventoryEntries/update`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${store.token}`,
      },
      body: JSON.stringify(inventory),
    });
    const updated = await response.status === 200 ? await response.json() : null;
    return updated;
  };

  const createInventory = async (inventory) => {
    const response = await fetch(`http://localhost:8090/inventoryEntries/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${store.token}`,
      },
      body: JSON.stringify(inventory),
    });
    const created = await response.status === 200 ? await response.json() : null;
    return created;
  };

  // Ordenar inventarios
  const sortBy = reactive({ column: null, order: 'asc' });

  const sortedInventories = computed(() => {
    if (!sortBy.column) sortBy.column = 'priority';

    return [...inventories].sort((a, b) => {
      let valA, valB;
      // Si estamos ordenando por supply_id, usamos los nombres de los supplies
      if (sortBy.column === 'supply_id') {
        valA = getSupplyName(a.supply_id);
        valB = getSupplyName(b.supply_id);
      }
      // Si estamos ordenando por emergency_id, usamos los nombres de las emergencias
      else if (sortBy.column === 'emergency_id') {
        valA = getEmergencyName(a.emergency_id);
        valB = getEmergencyName(b.emergency_id);
      }
      // Para la prioridad, usamos el índice en el array priorityOrder
      else if (sortBy.column === 'priority') {
        valA = priorityOptions.indexOf(a.priority);
        valB = priorityOptions.indexOf(b.priority);
      }
      // Para otros campos, comparamos los valores directamente
      else {
        valA = a[sortBy.column];
        valB = b[sortBy.column];
      }

      // Ordenamos de acuerdo con el valor de `sortBy.order`
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
  const showModal = reactive({ isVisible: false, selectedInventory: null });

  const openModal = (inventory) => {
    showModal.selectedInventory = { ...inventory }; // Create a copy of the selected inventory
    showModal.isVisible = true;
  };

  const closeModal = () => {
    showModal.isVisible = false;
    showModal.selectedInventory = null;
  };

  const handleOutsideClick = (event) => {
    if (event.target === event.currentTarget) {
      closeModal();
    }
  };

  const validateInventory = () => {
    const { requested, stock } = showModal.selectedInventory;
    if (requested < 0 || stock < 0) {
      return false; // No es válido si requested o stock son menores o iguales a 0
    }
    return true; // Es válido
  };


  const saveChanges = async () => {
    if (!validateInventory()) {
      alert("La cantidad solicitada y el stock deben ser mayores o iguales a 0.");
      return;
    }
    try {
      const updatedInventory = await updateInventory(showModal.selectedInventory);

      const index = inventories.findIndex(i => i.inventory_id === updatedInventory.inventory_id);
      if (index !== -1) {
        inventories[index] = { ...updatedInventory };
      }

      closeModal();
    } catch (error) {
      console.error("Error al actualizar el inventario:", error);
    }
  };

  // Modal para agregar un nuevo inventario
  const isAddingModalVisible = reactive({ value: false });

  const newInventory = reactive({
    supply_id: '',
    emergency_id: '',
    requested: '',
    stock: '',
    priority: ''
  });

  const priorityOptions = ['Crítica', 'Alta', 'Media', 'Baja'];

  const openAddingModal = () => {
    isAddingModalVisible.value = true;
  };

  const closeAddingModal = () => {
    isAddingModalVisible.value = false;
    newInventory.supply_id = '';
    newInventory.emergency_id = '';
    newInventory.requested = '';
    newInventory.stock = '';
    newInventory.priority = '';
  };

  const handleSaveOutsideClick = (event) => {
    if (event.target === event.currentTarget) {
      closeAddingModal();
    }
  };

  const validateNewInventory = () => {
    console.log(newInventory);

    const { requested, stock } = newInventory;
    if (requested < 0 || stock < 0) {
      return false; // Si requested o stock son 0 o negativos, no es válido
    }
    return true; // Es válido si requested y stock son mayores a 0
  };

  const saveNewInventory = async () => {
    if (!validateNewInventory()) {
      alert("La cantidad solicitada y el stock deben ser mayores o iguales a 0.");
      return;
    }
    if (
      newInventory.supply_id &&
      newInventory.emergency_id &&
      newInventory.requested >= 0 &&
      newInventory.stock >= 0 &&
      newInventory.priority
    ) {
      try {
        const inventoryToAdd = {
          supply_id: newInventory.supply_id,
          emergency_id: newInventory.emergency_id,
          requested: newInventory.requested,
          stock: newInventory.stock,
          priority: newInventory.priority,
        };

        const createdInventory = await createInventory(inventoryToAdd);

        if (createdInventory) {
          inventories.push(createdInventory);
        } else {
          alert("Hubo un error al crear el inventario.");
        }

        closeAddingModal();
      } catch (error) {
        console.error("Error al guardar el nuevo inventario:", error);
        alert("Hubo un error al guardar el inventario.");
      }
    } else {
      alert("Por favor, complete todos los campos.");
    }
  };
</script>

  <template>
    <div class="grid place-items-center h-full">
      <!-- Table for Inventories -->
      <h2 v-if="loading" class="text-center text-2xl py-5"> Cargando inventario...</h2>
      <table v-if="inventories && inventories.length > 0"
        class="w-[90%] my-5 table-auto border-collapse border border-gray-200">
        <thead class="select-none bg-gray-600 text-white border border-b-black">
          <tr>
            <th @click="sortTable('inventory_id')"
              class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
              :class="{ 'bg-[#14a499]': sortBy.column === 'inventory_id' }">
              ID
              <span v-if="sortBy.column === 'inventory_id'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
            </th>
            <th @click="sortTable('supply_id')"
              class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
              :class="{ 'bg-[#14a499]': sortBy.column === 'supply_id' }">
              Insumo
              <span v-if="sortBy.column === 'supply_id'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
            </th>
            <th @click="sortTable('emergency_id')"
              class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
              :class="{ 'bg-[#14a499]': sortBy.column === 'emergency_id' }">
              Emergencia
              <span v-if="sortBy.column === 'emergency_id'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
            </th>
            <th @click="sortTable('requested')"
              class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
              :class="{ 'bg-[#14a499]': sortBy.column === 'requested' }">
              Solicitado
              <span v-if="sortBy.column === 'requested'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
            </th>
            <th @click="sortTable('stock')"
              class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
              :class="{ 'bg-[#14a499]': sortBy.column === 'stock' }">
              Stock
              <span v-if="sortBy.column === 'stock'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
            </th>
            <th @click="sortTable('missing')"
              class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
              :class="{ 'bg-[#14a499]': sortBy.column === 'missing' }">
              Faltante
              <span v-if="sortBy.column === 'missing'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
            </th>
            <th @click="sortTable('priority')"
              class="px-4 py-2 border border-b-red-500 border-black text-left cursor-pointer"
              :class="{ 'bg-[#14a499]': sortBy.column === 'priority' }">
              Prioridad
              <span v-if="sortBy.column === 'priority'">{{ sortBy.order === 'asc' ? '↑' : '↓' }}</span>
            </th>
            <th class="px-4 py-2 border border-b-red-500 border-black text-left">Action</th>
          </tr>
        </thead>
        <tbody>
          <tr class="odd:bg-gray-100" v-for="inventory in sortedInventories" :key="inventory.inventory_id">
            <td class="px-4 py-2 border border-gray-300">{{ inventory.inventory_id }}</td>
            <td class="px-4 py-2 border border-gray-300">{{ getSupplyName(inventory.supply_id) }}</td>
            <td class="px-4 py-2 border border-gray-300">{{ getEmergencyName(inventory.emergency_id) }}</td>
            <td class="px-4 py-2 border border-gray-300">{{ inventory.requested }}</td>
            <td class="px-4 py-2 border border-gray-300">{{ inventory.stock }}</td>
            <td class="px-4 py-2 border border-gray-300">{{ inventory.missing }}</td>
            <td class="px-4 py-2 border border-gray-300">{{ inventory.priority }}</td>
            <td class="px-4 py-2 border border-gray-300">
              <button @click="openModal(inventory)" class="bg-blue-500 text-white py-1 px-3 rounded hover:saturate-200">
                Editar
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <p v-else-if="!loading">No se encontraron inventarios</p>

      <!-- Modal for Edit -->
      <div v-if="showModal.isVisible" @click="handleOutsideClick"
        class="fixed inset-0 bg-gray-500 bg-opacity-50 flex justify-center items-center z-50">
        <div class="bg-white p-6 rounded-lg w-1/3">
          <h2 class="text-xl font-bold mb-4">Editar Inventario</h2>
          <div class="mb-4">
            <label for="requested" class="block">Cantidad a solicitar</label>
            <input v-model="showModal.selectedInventory.requested" type="number" id="requested"
              class="w-full px-4 py-2 border border-gray-300 rounded" />
          </div>
          <div class="mb-4">
            <label for="stock" class="block">Stock</label>
            <input v-model="showModal.selectedInventory.stock" type="number" id="stock"
              class="w-full px-4 py-2 border border-gray-300 rounded" />
          </div>
          <div class="mb-4">
            <label for="priority" class="block">Prioridad</label>
            <select v-model="showModal.selectedInventory.priority" id="priority"
              class="w-full px-4 py-2 border border-gray-300 rounded">
              <option v-for="option in priorityOptions" :key="option" :value="option">{{ option }}</option>
            </select>
          </div>
          <div class="flex justify-between">
            <button @click="closeModal" class="bg-gray-300 py-2 px-4 rounded hover:bg-red-200">Cancelar</button>
            <button @click="saveChanges"
              class="bg-green-500 text-white py-2 px-4 rounded hover:contrast-125">Guardar</button>
          </div>
        </div>
      </div>

      <!-- Button for Adding New Inventory -->
      <button @click="openAddingModal"
        class="fixed bottom-8 right-16 bg-[#14a499] grid place-content-center text-5xl pt-2 w-14  h-14 text-white p-4 rounded-full shadow-2xl focus:outline-none hover:saturate-200">
        +
      </button>

      <!-- Modal para añadir nuevo inventario -->
      <div v-if="isAddingModalVisible.value" @click="handleSaveOutsideClick"
        class="fixed inset-0 bg-gray-500 bg-opacity-50 flex justify-center items-center z-50">
        <div class="bg-white p-6 rounded-lg w-1/3">
          <h2 class="text-xl font-bold mb-4">Añadir Nuevo Inventario</h2>

          <!-- Supply Select -->
          <div class="mb-4">
            <label for="supply_id" class="block">Insumo</label>
            <select v-model="newInventory.supply_id" id="supply_id"
              class="w-full px-4 py-2 border border-gray-300 rounded">
              <option value="" disabled selected>Seleccionar Insumo</option>
              <option v-for="supply in supplies" :key="supply.supply_id" :value="supply.supply_id">
                {{ supply.name }}
              </option>
            </select>
          </div>

          <!-- Emergency Select -->
          <div class="mb-4">
            <label for="emergency_id" class="block">Emergencia</label>
            <select v-model="newInventory.emergency_id" id="emergency_id"
              class="w-full px-4 py-2 border border-gray-300 rounded">
              <option value="" disabled selected>Seleccionar Emergencia</option>
              <option v-for="emergency in emergencys" :key="emergency.emergency_id" :value="emergency.emergency_id">
                {{ emergency.title }}
              </option>
            </select>
          </div>

          <!-- Requested Input -->
          <div class="mb-4">
            <label for="requested" class="block">Cantidad a solicitar</label>
            <input v-model="newInventory.requested" type="number" id="requested"
              class="w-full px-4 py-2 border border-gray-300 rounded" />
          </div>

          <!-- Stock Input -->
          <div class="mb-4">
            <label for="stock" class="block">Stock</label>
            <input v-model="newInventory.stock" type="number" id="stock"
              class="w-full px-4 py-2 border border-gray-300 rounded" />
          </div>

          <!-- Priority Select -->
          <div class="mb-4">
            <label for="priority" class="block">Prioridad</label>
            <select v-model="newInventory.priority" id="priority"
              class="w-full px-4 py-2 border border-gray-300 rounded">
              <option value='' disabled selected>Selecciona una prioridad</option>
              <option v-for="option in priorityOptions" :key="option" :value="option">{{ option }}</option>
            </select>
          </div>

          <div class="flex justify-between">
            <button @click="closeAddingModal" class="bg-gray-300 py-2 px-4 rounded hover:bg-red-200">Cancelar</button>
            <button @click="saveNewInventory"
              class="bg-green-500 text-white py-2 px-4 rounded hover:contrast-125">Guardar</button>
          </div>
        </div>
      </div>
    </div>
  </template>

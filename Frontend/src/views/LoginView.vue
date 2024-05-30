<template>
    <div class="flex flex-col gap-8 mx-auto my-20 w-1/5">
        <div class="flex flex-col items-center gap-6">
            <img class="w-24 h-24" src="../assets/logo-sistema.png" alt="">
            <h2 class=" text-lg font-bold text-teal-600">¡Es un buen día para ayudar!</h2>
        </div>
        <div class="w-full p-8 bg-gray-50 border rounded-xl shadow-md">
            <h2 class="mb-8 text-center">Por favor, ingresa tus datos</h2>
            <form class="space-y-5">
                <div class="flex flex-col gap-2">
                    <label for="rut" class="text-sm">RUT</label>
                    <Input v-model="rut" type="text" placeholder="12.345.678-9" />
                </div>
                <div class="flex flex-col gap-2">
                    <label for="password" class="text-sm">Contraseña</label>
                    <Input v-model="password" type="password" placeholder="********" />
                </div>
            </form>
            <div class="mt-5 space-y-5">
                <ButtonPrimary @click="handleSubmit" buttonText="Ingresar"/>
                <div class="flex justify-center gap-2 text-sm">
                    <span>¿Aún no tienes cuenta?</span>
                    <button class="font-bold text-teal-600 hover:underline" @click="redirectToRegister">Registrate</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { ref } from "vue";
import { store, fetchUserRole } from "../store";
import axios from 'axios';
import ButtonPrimary from '../components/ButtonPrimary.vue';
import Input from "../components/Input.vue";

const router = useRouter();

const rut = ref(null);
const password = ref(null);

const role = ref("");

const loginUser = async () => {
    const data = {
        rut: rut.value,
        password: password.value
    }
    console.log(data)
    
    try {
        const response = await axios.post('http://localhost:8090/auth/login', data);
        console.log("Usuario logeado correctamente", response.data);
        store.token = response.data;
        console.log("Token guardado: ", store.token);
        
        const user = await fetchUserRole();
        role.value = user.role;

        if (role.value === "COORDINATOR") {
            redirectToHomeCoordinator();
        } else if (role.value === "VOLUNTEER") {
            redirectToHomeVolunteer();
        }
    } catch (error) {
        console.log("Error al iniciar sesión");
    }
}

function handleSubmit() {
    if (rut.value && password.value) {
        loginUser();
    } else {
        alert('Por favor complete todos los campos.');
    }
}

const redirectToHomeVolunteer = () => {
    console.log("Redirigiendo a /emergencias-activas");
    router.push('/emergencias-activas');
}

const redirectToHomeCoordinator = () => {
    console.log("Redirigiendo a /registrar-emergencia");
    router.push('/registrar-emergencia');
}

const redirectToRegister = () => {
    console.log("Redirigiendo a /register");
    router.push('/register');
}
</script>
import { reactive, watchEffect } from "vue";
import axios from "axios";
import { jwtDecode } from "jwt-decode";

export const store = reactive({
    token: localStorage.getItem("authToken") || null, // Recuperar el token del localStorage
});

export const fetchUserRole = async () => {
    if (!store.token) {
        console.error("No se encontró el token JWT en el localStorage.");
        return;
    }

    try {
        const decodedToken = jwtDecode(store.token);
        const decodedRut = decodedToken.sub;

        const response = await axios.get(`http://localhost:8090/usersMongo/rut/${decodedRut}`, {
            headers: {
                Authorization: `Bearer ${store.token}`,
            },
        });

        console.log("Rol usuario obtenido correctamente", response.data.role);
        return response.data;
    } catch (error) {
        console.error("Ha ocurrido un error al obtener el rol de usuario", error);

        if (error.response && error.response.status === 401) {
            console.error("Token inválido. Por favor, inicie sesión nuevamente.");
            store.token = null; 
            localStorage.removeItem("authToken"); 
        }
    }
};

watchEffect(() => {
    if (store.token) {
        localStorage.setItem("authToken", store.token); // Guardar el token en el localStorage
    }
});

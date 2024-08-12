import { reactive } from "vue";
import axios from "axios";
import { jwtDecode } from "jwt-decode";

export const store = reactive({
    token: null,
});

export const fetchUserRole = async () => {
    const storedJWT = localStorage.getItem("authToken")
    store.token = storedJWT
    const decodedRut = jwtDecode(store.token).sub;

    try {
        const response = await axios.get(`http://localhost:8090/usersMongo/rut/${decodedRut}`, {
            headers: {
                Authorization: `Bearer ${store.token}`
            }
        });
        console.log("Rol usuario obtenido correctamente", response.data.role);
        console.log(response.data)
        return response.data;
    } catch (error) {
        console.error("Ha ocurrido un error al obtener el rol de usuario", error);
    }
}
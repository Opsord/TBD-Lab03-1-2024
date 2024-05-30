import { reactive } from "vue";
import axios from "axios";
import { jwtDecode } from "jwt-decode";

export const store = reactive({
    token: null,
});

export const fetchUserRole = async () => {
    const decodedRut = jwtDecode(store.token.token).sub;
    console.log("RUT USUARIO: ", decodedRut);
    
    try {
        const response = await axios.get(`http://localhost:8090/api/users/rut/${decodedRut}`, {
            headers: {
                Authorization: `Bearer ${store.token.token}`
            }
        });
        console.log("Rol usuario obtenido correctamente", response.data.role);
        return response.data;
    } catch (error) {
        console.error("Ha ocurrido un error al obtener el rol de usuario", error);
    }
}
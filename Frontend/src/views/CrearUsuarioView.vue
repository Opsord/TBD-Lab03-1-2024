<script setup>
import {
    FormControl,
    FormDescription,
    FormField,
    FormItem,
    FormLabel,
    FormMessage
} from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { Checkbox } from '@/components/ui/checkbox';
import { Button } from '@/components/ui/button';

import { ref } from 'vue';

const formModel = ref({
    rutVoluntario: '',
    nombreVoluntario: '',
    apellidoVoluntario: '',
    edadVoluntario: 17,
    sexoVoluntario: false,
    contrasena: '',
    disponibilidad: false
});

function onSubmit() {
    console.log('Form submitted!', JSON.stringify(formModel.value, null, 2))
}
</script>
<template>
    <div class="flex flex-col justify-center items-center align-middle">
        <form class=" space-y-4" @submit="onSubmit">
            <FormField name="nombre">
                <FormItem>
                    <FormLabel>Nombre:</FormLabel>
                    <FormControl>
                        <Input type="text" placeholder="Su nombre" v-model="formModel.name" />
                    </FormControl>
                    <FormMessage />
                </FormItem>
            </FormField>

            <FormField name="apellido">
                <FormItem>
                    <FormLabel>Apellido:</FormLabel>
                    <FormControl>
                        <Input type="text" placeholder="Su Apellido" v-model="formModel.lastName" />
                    </FormControl>
                    <FormMessage />
                </FormItem>
            </FormField>

            <FormField name="rut">
                <FormItem>
                    <FormLabel>R.U.T:</FormLabel>
                    <FormControl>
                        <Input type="text" placeholder="R.U.T" v-model="formModel.rut" />
                    </FormControl>
                    <FormMessage />
                </FormItem>
            </FormField>

            <FormField name="edad">
                <FormItem>
                    <FormLabel>Edad:</FormLabel>
                    <FormControl>
                        <Input type="number" placeholder="edad" v-model="formModel.edadVoluntario" />
                    </FormControl>
                    <FormMessage />
                </FormItem>
            </FormField>

            <FormField name="sexo">
                <FormItem class="space-x-2 gap-4">
                    <FormLabel>Sexo:</FormLabel>
                    <FormControl>
                        <input type="radio" placeholder="edad" value=false v-model="formModel.sex" />
                        <label for="false">Hombre</label>

                        <input type="radio" placeholder="edad" value=true v-model="formModel.sex" />
                        <label for="true">Mujer</label>
                    </FormControl>
                    <FormMessage />
                </FormItem>
            </FormField>


            <FormField name="contrasena">
                <FormItem>
                    <FormLabel>Contraseña</FormLabel>
                    <FormControl>
                        <Input type="text" placeholder="contraseña-segura" v-model="formModel.password" />
                    </FormControl>
                    <FormMessage />
                </FormItem>
            </FormField>

            <FormField name="disponibilidad" type="checkbox" v-model="formModel.availability">
                <FormItem class="flex flex-row items-center align-middle space-x-2">
                    <FormLabel>¿Disponible de inmediato?</FormLabel>
                    <FormControl>
                        <Checkbox id="disponibilidad" :checked="formModel.disponibilidad"
                            @update:checked="value => formModel.disponibilidad = value" class="bg-slate-100 flex" />
                    </FormControl>
                    <FormMessage />
                </FormItem>
            </FormField>
            <Button variant="secondary" type="submit">
                Enviar
            </Button>
        </form>
    </div>
</template>

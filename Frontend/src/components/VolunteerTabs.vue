<script setup>
import '../assets/base.css'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
</script>

<template>
    <div class="flex flex-row justify-center align-middle bg-gray-50 border-b border-gray-200">
        <Tabs default-value="account">
            <TabsList>
                <TabsTrigger value="emergencias">
                    <RouterLink to="/emergencias-activas">
                        Emergencias Activas
                    </RouterLink>
                </TabsTrigger>
                <TabsTrigger value="finalizadas">
                    <RouterLink to="/emergencias-finalizadas">
                        Emergencias Finalizadas
                    </RouterLink>
                </TabsTrigger>
                <TabsTrigger value="consultar">
                    <RouterLink to="/consultar-emergencia">
                        Consultar Emergencia
                    </RouterLink>
                </TabsTrigger>
            </TabsList>
        </Tabs>
    </div>
</template>
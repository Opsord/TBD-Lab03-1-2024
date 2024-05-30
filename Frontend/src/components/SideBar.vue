<template>
    <div className="container-sidebar">
        <div class="container-user">
            <span>Username</span>
        </div>
        <div class="container-options">
            <div class="option">
                <v-icon name="hi-home" />
                <a href="">Home</a>
            </div>
            <div class="option" @click="toggleDropdown">
                <v-icon name="hi-light-bulb" />
                <span>Emergencia</span>
                <v-icon name="hi-solid-chevron-down" />
            </div>
            <div class="dropdown" v-if="showDropdown">
                <div class="option" @click="toggleDropdown">
                    <v-icon name="hi-clipboard-copy" />
                    <a href="">Nueva Emergencia</a>
                </div>
                <div class="option" @click="toggleDropdown">
                    <v-icon name="hi-clipboard-list" />
                    <a href="">Detalles Emergencia</a>
                </div>
                <div class="option" @click="toggleDropdown">
                    <v-icon name="hi-clipboard-check" />
                    <a href="">Emergencias Finalizadas</a>
                </div>
            </div>
        </div>
        <div class="logout">
            <v-icon name="hi-logout" />
            <a href="">Logout</a>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';

let showDropdown = ref(false);

const toggleDropdown = () => {
    showDropdown.value = !showDropdown.value;
}
</script>

<style scoped>
.container-sidebar{
    display: flex;
    flex-direction: column;
    padding: 8px 0;
    background-color: #17a499;
    height: 100vh;
}

.container-user {
    padding: 12px 16px;
    margin: 12px;
    background-color: white;
    border-radius: 5px;
    text-align: center;
    font-size: 18px;
    font-weight: bold;
}

.option, .logout {
    display: flex;
    align-items: center;
    gap: 10px;
    color: white;
    font-weight: bold;
    padding: 12px 16px;
    margin: 4px 12px;
}

.option:hover,
.logout:hover {
    background: #0f6860;
    border-radius: 5px;
}

.logout {
    margin-top: auto;
}
</style>
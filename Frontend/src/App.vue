<script setup>
import './assets/base.css';
import NavBar from './components/NavBar.vue';
import MainTabs from './components/MainTabs.vue';
import { store } from './store';
</script>

<template>
    <NavBar />
    <div v-if="store.token !== null">
        <MainTabs />
    </div>
    <RouterView />
</template>

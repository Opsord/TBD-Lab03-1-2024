<script setup>
import './assets/base.css';
import NavBar from './components/NavBar.vue';
import MainTabs from './components/MainTabs.vue';
import { store } from './store';
import { watchEffect } from 'vue';
</script>

<template>
    <NavBar />
    <div v-if="store.token">
        <MainTabs />
    </div>
    <RouterView />
</template>

import './assets/base.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { OhVueIcon, addIcons } from "oh-vue-icons";
import { HiUserCircle, HiHome, HiLightBulb, HiClipboardCopy, HiClipboardList, HiClipboardCheck, HiSolidChevronDown, HiLogout } from "oh-vue-icons/icons";

addIcons(HiUserCircle, HiHome, HiLightBulb, HiClipboardCopy, HiClipboardList, HiClipboardCheck, HiSolidChevronDown, HiLogout);

const app = createApp(App)

app.component("v-icon", OhVueIcon);// 

app.use(router)

app.mount('#app')

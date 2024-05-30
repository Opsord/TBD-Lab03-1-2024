<script setup lang="ts">
import { ref, defineEmits } from "vue";
import { GoogleMap, Marker } from "vue3-google-map";

const center = { lat: -33.45737, lng: -70.66486479 };

const API_KEY = import.meta.env.VITE_GOOGLE_API;

const marker = ref(null);
const emit = defineEmits(['update-marker']);


const updateMarkerPosition = (event) => {
	marker.value = {
		position: {
			lat: event.latLng.lat(),
			lng: event.latLng.lng(),
		},
	};

	emit('update-marker', marker.value);
};
</script>

<template>
	<GoogleMap :api-key="API_KEY" style="width: 100%; height: 500px" :center="center" :zoom="12"
		@click="updateMarkerPosition">
		<Marker v-if="marker" :options="{ position: marker.position }" />
	</GoogleMap>
</template>

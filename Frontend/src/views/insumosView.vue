<script setup>
const suppliese = [
  {
    supply_id: 678,
    name: "Land",
    description: "Consumer example culture agency already future.",
    classification: "Furniture"
  },
  {
    supply_id: 290,
    name: "Draw",
    description: "Vote add serious positive approach travel train.",
    classification: "Tools"
  },
  {
    supply_id: 760,
    name: "Again",
    description: "Floor society cost carry whether find usually work.",
    classification: "Furniture"
  },
  {
    supply_id: 927,
    name: "Together",
    description: "Science admit fight nature artist again may come.",
    classification: "Food"
  },
  {
    supply_id: 809,
    name: "Heavy",
    description: "Generation pattern song management least decision.",
    classification: "Food"
  },
  {
    supply_id: 602,
    name: "Win",
    description: "Law huge law high central movement risk.",
    classification: "Electronics"
  },
  {
    supply_id: 383,
    name: "Computer",
    description: "Hundred strong early along believe dinner growth.",
    classification: "Electronics"
  },
  {
    supply_id: 132,
    name: "Dream",
    description: "Employee vote control professional finish weather.",
    classification: "Clothing"
  },
  {
    supply_id: 753,
    name: "Style",
    description: "Morning alone today large health prevent.",
    classification: "Clothing"
  },
  {
    supply_id: 516,
    name: "Skill",
    description: "Shoulder current speech every arm product.",
    classification: "Tools"
  }
];

</script>

<template>
  <div class="grid place-items-center h-full">
    <h1>Insumos</h1>
    <ul  v-if="supplies && supplies.length > 0">
      <li v-for="supply in supplies" :key="supply_id"></li>
    </ul>
    <p v-else>No se encontraron suministros</p>
  </div>
</template>
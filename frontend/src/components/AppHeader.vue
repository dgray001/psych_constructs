<script setup lang="ts">
import { RouterLink } from 'vue-router'
defineProps<{
  msg: string
}>()
</script>

<template>
  <h1 class="green">{{ msg }}</h1>
  <nav>
    <RouterLink to="/">Home</RouterLink>
    <RouterLink to="/construct/form">Construct</RouterLink>
  </nav>
</template>

<style scoped>
* {
  width: 200vw;
}
</style>

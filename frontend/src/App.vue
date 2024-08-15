<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'
</script>

<template>
  <header>
    <div class="wrapper">
      <AppHeader msg = "this is a header" />
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/construct/form">Construct</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />

  <AppFooter />
</template>

<style scoped>
</style>

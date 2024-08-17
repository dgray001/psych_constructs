<script setup lang="ts">
import { RouterView } from 'vue-router'
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'
</script>

<template>
  <div id="app">
    <div id="header">
      <AppHeader msg = "this is the header" />
    </div>

    <div id="body">
      <RouterView />
    </div>

    <div id="footer">
      <AppFooter />
    </div>

  </div>
</template>

<style scoped>
#header, #body, #footer {
  max-width: 100vw;
  overflow: auto;
}
</style>

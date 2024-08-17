<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<template>
  <div id="wrapper" class="size-max">
    <div id="title">Psych Constructs</div>
  </div>
  <nav>
    <RouterLink to="/">Home</RouterLink>
    <RouterLink to="/construct/form">Construct</RouterLink>
  </nav>
</template>

<style scoped>
#wrapper {
  display: flex;
  flex-flow: row nowrap;

  #title {
    font-size: var(--size-title);
  }
}
</style>

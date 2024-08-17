<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<template>
  <div id="wrapper" class="size-max">
    <div id="title">Psych Constructs</div>
    <nav id="nav">
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/construct">Constructs</RouterLink>
    </nav>
  </div>
</template>

<style scoped>
#wrapper {
  display: flex;
  flex-flow: row nowrap;
  gap: var(--size-larger);

  #title {
    font-size: var(--size-title);
  }

  #nav {
    display: flex;
    flex-flow: row nowrap;
    gap: var(--size-smaller);
  }
}
</style>

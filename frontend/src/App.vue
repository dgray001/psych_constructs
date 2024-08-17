<script setup lang="ts">
import { RouterView } from 'vue-router'
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'
</script>

<template>
  <div id="app">
    <div id="header" class="size-h size-w">
      <AppHeader />
    </div>

    <div id="body" class="size-w">
      <RouterView />
    </div>

    <div id="footer" class="size-h size-w">
      <AppFooter msg="this is the header" />
    </div>
  </div>
</template>

<style scoped>
#app {
  background-color: var(--bg-1);
  display: flex;
  flex-flow: column nowrap;
}

#header,
#body,
#footer {
  --size-w: 100vw;
  overflow: auto;
}

#header {
  --size-h: var(--size-header);
  background-color: var(--bg-2);
  padding: var(--size-medium) var(--size-medium) var(--size-smaller);
}

#body {
  background-color: var(--bg-1);
  padding: var(--size-medium);
  min-height: calc(100vh - var(--size-header) - var(--size-footer));
}

#footer {
  --size-h: var(--size-footer);
  background-color: var(--bg-2);
  padding: var(--size-smaller) var(--size-medium) var(--size-medium);
}
</style>

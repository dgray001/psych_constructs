<script setup lang="ts">
import { ref } from 'vue'
import BaseTable from '@/components/table/BaseTable.vue'
import { Query } from '../../../proto/query'

const data = ref({
  query: Query.create({
    search: ''
  })
})

const click = () => {
  console.log(data.value.query.search)
}
</script>

<template>
  <div class="form">
    <input v-model="data.query.search" />
    <button @click="click">search</button>
    <BaseTable :query="data.query" tableable_key="table-construct" />
  </div>
  <RouterView />
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>

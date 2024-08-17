<script setup lang="ts">
import type { Tableable } from '@/util/table/tableable';
import type { Query } from 'proto/query';
import { watch } from 'vue';

const props = defineProps<{
  query: Query,
  tableable: Tableable|undefined, // will be injected
}>()

let query: Query = props.query
  
watch(() => props.query, () => {
  console.log('updated!', props.query.search);
  query = props.query
}, {
  immediate: true,
  deep: true,
})
</script>

<template>
  <div>base table: {{ query.search }}</div>
</template>

<style scoped></style>

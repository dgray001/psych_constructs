<script setup lang="ts">
import type { Listable, Tableable } from '@/util/table/tableable';
import type { Query } from 'proto/query';
import { getCurrentInstance, inject, ref, watch } from 'vue';

const props = defineProps<{
  query: Query,
  tableable_key: string,
}>()

const tableable = inject<Tableable<any>>(props.tableable_key)
if (!tableable) {
  throw new Error(`Tableable service key not valid: ${props.tableable_key}`)
}

let query = ref<Query>(props.query)
let data = ref<Listable[]>([])
let error = ref<string>('')

watch(() => props.query, async () => {
  const result = await tableable.list(props.query);
  if (!result.success) {
    data.value = []
    error.value = result.error_message ?? ''
  } else {
    data.value = result.data ?? []
    error.value = ''
  }
  console.log(result)
}, {
  immediate: true,
  deep: true,
})
</script>

<template>
  <div>base table: {{ query.search }}</div>
  <ul v-if="!error">
    <li v-for="d in data" :key="d.id">
      {{ d.id }}: {{ d.toString() }}
    </li>
  </ul>
  <div v-else>{{ error }}</div>
</template>

<style scoped></style>

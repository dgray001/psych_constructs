<script setup lang="ts">
import type { Listable, Tableable } from '@/util/table/tableable'
import { capitalize } from '@/util/util'
import type { Query } from 'proto/query'
import { inject, ref, watch } from 'vue'

const props = defineProps<{
  query: Query
  tableable_key: string
}>()

const tableable = inject<Tableable<any>>(props.tableable_key)
if (!tableable) {
  throw new Error(`Tableable service key not valid: ${props.tableable_key}`)
}

let data = ref<Listable[]>([])
let error = ref<string>('')

watch(
  () => props.query,
  async () => {
    const result = await tableable.list(props.query)
    if (!result.success) {
      data.value = []
      error.value = result.error_message ?? ''
    } else {
      data.value = result.data ?? []
      error.value = ''
    }
    console.log(result)
  },
  {
    immediate: true,
    deep: true
  }
)
</script>

<template>
  <table v-if="tableable">
    <caption>
      {{
        capitalize(tableable.name)
      }}
      Table
    </caption>
    <thead>
      <tr>
        <th v-for="c in tableable.cols" :key="c" scope="col">
          {{ c }}
        </th>
      </tr>
    </thead>
    <tbody v-if="!error">
      <tr v-for="d in data" :key="d.id">
        <td v-for="r in tableable.row(d)" :key="r">{{ r }}</td>
      </tr>
    </tbody>
    <div v-else>{{ error }}</div>
  </table>
</template>

<style scoped>
table {
  border-collapse: collapse;
  border: 3px solid black;
  table-layout: fixed;
  width: 100%;
}

thead th:nth-child(1) {
  width: 25%;
}
</style>

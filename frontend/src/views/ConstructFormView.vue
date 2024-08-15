<script setup lang="ts">
import { ListConstructRequest, ListConstructResponse } from '../../proto/construct_api';
import { Query } from '../../proto/query';

const name = defineModel<string>('name', {default: "default name"})
const description = defineModel<string>('description', {default: "default description"})
const create = async () => {
  const request: ListConstructRequest = ListConstructRequest.create({
    query: Query.create({
      search: '',
    })
  })
  const response = await fetch('/api/construct/list', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-protobuf'
    },
    body: ListConstructRequest.toBinary(request)
  })
  const tt = await response.arrayBuffer()
  const response_construct = ListConstructResponse.fromBinary(new Uint8Array(tt))
  console.log(response_construct);
}
</script>

<template>
  <div class="form">
    <input v-model="name">
    <textarea v-model="description"></textarea>
    <button @click="create">Submit</button>
  </div>
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

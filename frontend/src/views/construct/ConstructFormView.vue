<script setup lang="ts">
import { Construct } from '../../../proto/construct'
import { CreateConstructRequest, CreateConstructResponse } from '../../../proto/construct_api'

const name = defineModel<string>('name', { default: 'default name' })
const description = defineModel<string>('description', { default: 'default description' })
const create = async () => {
  const request: CreateConstructRequest = CreateConstructRequest.create({
    construct: Construct.create({ name: name.value, description: description.value })
  })
  const response = await fetch('/api/construct/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-protobuf'
    },
    body: CreateConstructRequest.toBinary(request)
  })
  const tt = await response.arrayBuffer()
  const response_construct = CreateConstructResponse.fromBinary(new Uint8Array(tt))
  console.log(response_construct)
}
</script>

<template>
  <div class="form">
    <input v-model="name" />
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

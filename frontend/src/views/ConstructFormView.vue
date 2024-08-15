<script setup lang="ts">
import { Construct } from '../../proto/construct'

const name = defineModel<string>('name', {default: "default name"})
const description = defineModel<string>('description', {default: "default description"})
const create = async () => {
  const construct: Construct = Construct.create({
    name: name.value,
    description: description.value,
  })
  const response = await fetch('/api/construct/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-protobuf'
    },
    body: Construct.toBinary(construct)
  })
  const tt = await response.arrayBuffer()
  const response_construct = Construct.fromBinary(new Uint8Array(tt))
  console.log(response_construct.id, response_construct.name)
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

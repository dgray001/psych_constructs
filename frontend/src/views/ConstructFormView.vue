<script setup lang="ts">
import { CreateConstructRequest, CreateConstructResponse, ReadConstructRequest, ReadConstructResponse } from '../../proto/construct_api';
import { Construct } from '../../proto/construct'

const name = defineModel<string>('name', {default: "default name"})
const description = defineModel<string>('description', {default: "default description"})
const create = async () => {
  const request: CreateConstructRequest = CreateConstructRequest.create({
    construct: Construct.create({
      name: name.value,
      description: description.value,
    })
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
  console.log(response_construct.construct?.id?.toString(), response_construct.construct?.name)
  const read_request: ReadConstructRequest = ReadConstructRequest.create({id: response_construct.construct?.id})
  const response2 = await fetch('/api/construct/read', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-protobuf'
    },
    body: ReadConstructRequest.toBinary(read_request)
  })
  const tt2 = await response2.arrayBuffer()
  const response_construct2 = ReadConstructResponse.fromBinary(new Uint8Array(tt2))
  console.log(response_construct2.construct?.id?.toString(), response_construct2.construct?.name)
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

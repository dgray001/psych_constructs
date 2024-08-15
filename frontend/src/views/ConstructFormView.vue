<script setup lang="ts">
import { CreateConstructRequest, CreateConstructResponse, DeleteConstructRequest} from '../../proto/construct_api';
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
  console.log(response_construct.construct?.id?.toString(), response_construct.construct?.name, response_construct.errorMessage)
  const read_request: DeleteConstructRequest = DeleteConstructRequest.create({id: 2})
  await fetch('/api/construct/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-protobuf'
    },
    body: DeleteConstructRequest.toBinary(read_request)
  })
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

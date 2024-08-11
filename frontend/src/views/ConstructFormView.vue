<script setup lang="ts">
import { Construct } from '../../proto/construct'
import { onMounted } from 'vue';

onMounted( async () => {
const construct: Construct = Construct.create({
  name: 'tedst',
  id: BigInt(2)
})
const response = await fetch('/api/create', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/x-protobuf'
  },
  body: Construct.toBinary(construct)
})
const tt = await response.arrayBuffer()
const response_construct = Construct.fromBinary(new Uint8Array(tt))
console.log(response_construct.id, response_construct.name)
})
</script>

<template>
  <div class="form">
    <h1>This is an form page</h1>
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

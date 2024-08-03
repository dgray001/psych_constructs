<script setup lang="ts">
import TheWelcome from '../components/TheWelcome.vue'
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
  <main>
    <TheWelcome />
  </main>
</template>


import { Query } from 'proto/query'
import { ListConstructRequest, ListConstructResponse } from '../../proto/construct_api'

const create = async () => {
  const request: ListConstructRequest = ListConstructRequest.create({
    query: Query.create({
      search: ''
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
  console.log(response_construct)
}
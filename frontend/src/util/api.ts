import { MessageType } from '@protobuf-ts/runtime'

export declare interface ApiResponse<T> {
  success: boolean
  data?: T
  error_message?: string
}

export async function post<T>(
  api: string,
  request_type: MessageType<any>,
  body: object,
  return_type: MessageType<any>
): Promise<ApiResponse<T>> {
  try {
    const response = await fetch(`api/${api}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-protobuf'
      },
      body: request_type.toBinary(body)
    })
    const buffer = await response.arrayBuffer()
    return {
      success: true,
      data: return_type.fromBinary(new Uint8Array(buffer))
    }
  } catch (e: any) {
    return {
      success: false,
      error_message: e.toString()
    }
  }
}

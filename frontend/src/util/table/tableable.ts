import type { Query } from 'proto/query'

export declare interface ListResponse<T> {
  success: boolean
  data?: T[]
  error_message?: string
}

export declare interface Listable {
  id?: number
}

/** Base interface for list and table implementations */
export declare interface Tableable<T extends Listable> {
  name: string
  list: (query: Query) => Promise<ListResponse<T>>
  cols: string[]
  row: (t: T) => string[]
}

/** Loop helper function */
export const loop = (times: number, callback: (i?: number) => void) => {
  for (let i = 0; i < times; i++) {
    callback(i)
  }
}

/** Async loop helper function */
export const asyncLoop = async (times: number, callback: Function) => {
  // eslint-disable-next-line no-async-promise-executor
  return new Promise<void>(async (resolve) => {
    for (let i = 0; i < times; i++) {
      await callback(i)
    }
    resolve()
  })
}

/** Returns promise that resolves when condition function becomes true */
export function until(condition: () => boolean, poll_timer = 300): Promise<void> {
  const poll = (resolve: () => void) => {
    if (condition()) {
      resolve()
    } else {
      setTimeout(() => poll(resolve), poll_timer)
    }
  }
  return new Promise<void>(poll)
}

/** Awaits a specific amount of time */
export async function untilTimer(timer: number): Promise<void> {
  await new Promise((resolve) => setTimeout(resolve, timer))
}

/** Capitalizes each word in string */
export function capitalize(str: string): string {
  if (!str) {
    return str
  }
  const str_split = str.trim().split(' ')
  return str_split
    .map((str) => {
      const lower = str.toLowerCase()
      return str.charAt(0).toUpperCase() + lower.slice(1)
    })
    .join(' ')
}

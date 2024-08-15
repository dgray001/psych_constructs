package fyi.lnz.psych_constructs.util;

import java.util.Arrays;
import java.util.List;

public class ArrU {
  @SuppressWarnings("unchecked")
  public static <T, R> R[] map(T[] objs, Callable<T, R> fn) {
    List<R> list = Arrays.stream(objs).map(o -> fn.call(o)).toList();
    R[] array = (R[]) java.lang.reflect.Array.newInstance(list.get(0).getClass(), list.size());
    for (int i = 0; i < list.size(); i++) {
      array[i] = list.get(i);
    }
    return array;
  }
}

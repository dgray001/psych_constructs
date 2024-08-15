package fyi.lnz.psych_constructs.operations;

import fyi.lnz.psych_constructs.database.Row;

public interface Crud<T> {
  public T convertRow(Row r);

  public T create(T t);

  public T read(Integer id);

  // public T update(T t);

  // public T delete(Integer id);
}

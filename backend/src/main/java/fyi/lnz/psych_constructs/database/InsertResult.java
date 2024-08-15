package fyi.lnz.psych_constructs.database;

import java.util.List;

public record InsertResult(Integer rows, List<Long> generated_keys) {
}

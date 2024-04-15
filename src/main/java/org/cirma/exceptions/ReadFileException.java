package org.cirma.exceptions;

import java.util.List;

public class ReadFileException extends RuntimeException {

    public ReadFileException(String errors) {
        super(errors);
    }
}

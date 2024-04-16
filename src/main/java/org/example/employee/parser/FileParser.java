package org.example.employee.parser;

public interface FileParser<T> {

    T parseContent(String fileName);
}

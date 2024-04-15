package org.cirma.parser;

public interface FileParser<T> {


    T parseContent(String fileName);
}

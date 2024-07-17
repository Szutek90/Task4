package com.app.file_operator;

public interface FileOperator<T> {
    T readFile(String filename);
}

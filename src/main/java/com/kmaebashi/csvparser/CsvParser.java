package com.kmaebashi.csvparser;

import java.io.*;
import java.nio.charset.StandardCharsets;

import com.kmaebashi.csvparserimpl.CsvParserImpl;

public abstract class CsvParser implements AutoCloseable {
    public static CsvParser newInstance(String path)
            throws FileNotFoundException, IOException {
        Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8));
        return new CsvParserImpl(reader);
    }

    public static CsvParser newInstance(Reader reader) {
        return new CsvParserImpl(reader);
    }

    abstract public String[] readLine() throws IOException, CsvParseException;
    abstract public void close() throws IOException;
}

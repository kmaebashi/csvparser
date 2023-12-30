package com.kmaebashi.csvparser;

import com.kmaebashi.csvparserimpl.CsvParserImpl;

import java.io.IOException;
import java.io.StringReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {
    @org.junit.jupiter.api.Test
    void testReadLine001() throws Exception {
        CsvParser parser = CsvParser.newInstance(new StringReader("a,b,c"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
    }

    @org.junit.jupiter.api.Test
    void testReadFile001() throws Exception {
        try (var parser = CsvParser.newInstance("test_input/test001.csv");
             var writer = new FileWriter("test_output/test001_out.txt")) {
            String[] fields;
            while ((fields = parser.readLine()) != null) {
                for (int i = 0; i < fields.length; i++) {
                    writer.write("<" + fields[i] + ">");
                }
                writer.write("\r\n");
            }
        }
        assertTrue(compareFiles("test_output/test001_out.txt", "test_expected/test001_expected.txt"));
    }

    @org.junit.jupiter.api.Test
    void testReadFile002() throws Exception {
        try (var parser = CsvParser.newInstance("test_input/test002.csv");
             var writer = new FileWriter("test_output/test002_out.txt")) {
            String[] fields;
            while ((fields = parser.readLine()) != null) {
                for (int i = 0; i < fields.length; i++) {
                    writer.write("<" + fields[i] + ">");
                }
                writer.write("\r\n");
            }
        }
        assertTrue(compareFiles("test_output/test002_out.txt", "test_expected/test002_expected.txt"));
    }

    private boolean compareFiles(String path1, String path2) throws IOException {
        byte[] bytes1 = Files.readAllBytes(Paths.get(path1));
        String str1 = new String(bytes1, StandardCharsets.UTF_8);
        byte[] bytes2 = Files.readAllBytes(Paths.get(path2));
        String str2 = new String(bytes2, StandardCharsets.UTF_8);

        return str1.equals(str2);
    }
}
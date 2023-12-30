package com.kmaebashi.csvparserimpl;

import com.kmaebashi.csvparser.CsvParseException;

import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.*;

class CsvParserImplTest {
    @org.junit.jupiter.api.Test
    void testReadLine001() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
    }

    @org.junit.jupiter.api.Test
    void testReadLine002() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c\n"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLine003() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c\r\n"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLine004() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c\nd,e,f"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
        ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("d", ret[0]);
        assertEquals("e", ret[1]);
        assertEquals("f", ret[2]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLine005() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("aa,bb,cc\r\nd,e,f"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("aa", ret[0]);
        assertEquals("bb", ret[1]);
        assertEquals("cc", ret[2]);
        ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("d", ret[0]);
        assertEquals("e", ret[1]);
        assertEquals("f", ret[2]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLine006() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,,b,c\r\n"));
        String[] ret = parser.readLine();
        assertEquals(4, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("", ret[1]);
        assertEquals("b", ret[2]);
        assertEquals("c", ret[3]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLine007() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c,\r\n"));
        String[] ret = parser.readLine();
        assertEquals(4, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
        assertEquals("", ret[3]);
        ret = parser.readLine();
        assertNull(ret);
    }
    @org.junit.jupiter.api.Test
    void testReadLine008() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c,\n"));
        String[] ret = parser.readLine();
        assertEquals(4, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
        assertEquals("", ret[3]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLine009() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c\r\n\r\n"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
        ret = parser.readLine();
        assertEquals(1, ret.length);
        assertEquals("", ret[0]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLine010() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c\n\n"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
        ret = parser.readLine();
        assertEquals(1, ret.length);
        assertEquals("", ret[0]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLine011() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c\r\n\r\nd,e,f"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
        ret = parser.readLine();
        assertEquals(1, ret.length);
        assertEquals("", ret[0]);
        ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("d", ret[0]);
        assertEquals("e", ret[1]);
        assertEquals("f", ret[2]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLine012() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c\n\nd,e,f"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b", ret[1]);
        assertEquals("c", ret[2]);
        ret = parser.readLine();
        assertEquals(1, ret.length);
        assertEquals("", ret[0]);
        ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("d", ret[0]);
        assertEquals("e", ret[1]);
        assertEquals("f", ret[2]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLine013() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,b,c\rd,e,f"));
        try {
            String[] ret = parser.readLine();
            fail();
        } catch (CsvParseException ex) {
            assertEquals("CRの後にLF以外の文字が来ました。(d)", ex.getMessage());
        }
    }
    @org.junit.jupiter.api.Test
    void testReadLineQuoted001() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,\"b,b\",\"c\r\n\"\r\n"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b,b", ret[1]);
        assertEquals("c\r\n", ret[2]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLineQuoted002() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("a,\"b,b\",\"c\n\"\n"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a", ret[0]);
        assertEquals("b,b", ret[1]);
        assertEquals("c\n", ret[2]);
        ret = parser.readLine();
        assertNull(ret);
    }
    @org.junit.jupiter.api.Test
    void testReadLineQuoted003() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("\"a\"\"a\",\"a\"\"\",\"\"\"\"\r\n"));
        String[] ret = parser.readLine();
        assertEquals(3, ret.length);
        assertEquals("a\"a", ret[0]);
        assertEquals("a\"", ret[1]);
        assertEquals("\"", ret[2]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLineQuoted004() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader(" \"a\",\r\n"));
        String[] ret = parser.readLine();
        assertEquals(2, ret.length);
        assertEquals(" \"a\"", ret[0]);
        assertEquals("", ret[1]);
        ret = parser.readLine();
        assertNull(ret);
    }

    @org.junit.jupiter.api.Test
    void testReadLineQuoted005() throws Exception {
        CsvParserImpl parser = new CsvParserImpl(new StringReader("\"a\" ,b\r\n"));
        try {
            String[] ret = parser.readLine();
            fail();
        } catch (CsvParseException ex) {
            assertEquals("ダブルクォートを閉じた後がカンマでも改行でもありません。", ex.getMessage());
        }
    }

}
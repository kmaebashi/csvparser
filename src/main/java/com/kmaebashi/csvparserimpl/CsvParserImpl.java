package com.kmaebashi.csvparserimpl;

import com.kmaebashi.csvparser.CsvParser;
import com.kmaebashi.csvparser.CsvParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CsvParserImpl implements CsvParser {
    Reader reader;
    public CsvParserImpl(Reader reader) {
        this.reader = reader;
    }

    /*
     * このCSVパーサは、入力を１文字ずつ読み込みながら、「状態」を変えつつ
     * 入力を解析していきます。取りうる状態を表すのが列挙型Stateです。
     * AFTER_COMMA…カンマを読み込んだ直後の状態。初期状態もこれと同じなので
     *   代用しています。
     * IN_FIELD…ダブルクォートに囲まれていないフィールドの読み込み中です。
     *   カンマが来たらAFTER_COMMAに、改行が来たら
     * IN_QUOTE…ダブルクォートで囲まれたフィールドの読み込み中です。
     * QUOTE_IN_QUOTE…IN_QUOTEの最中に「"」が現れた状態です。
     * AFTER_CR…改行コードがCR+LFの場合、CRが来た状態。
     */
    private enum State {
        AFTER_COMMA,
        IN_FIELD,
        IN_QUOTE,
        QUOTE_IN_QUOTE,
        AFTER_CR
    }

    public String[] readLine() throws IOException, CsvParseException {
        ArrayList<String> fieldList = new ArrayList<String>();
        StringBuilder field = new StringBuilder();
        int ch;
        State state = State.AFTER_COMMA;

        while ((ch = reader.read()) != -1) {
            switch (state) {
                case AFTER_COMMA:
                    if (ch == '\"') {
                        state = State.IN_QUOTE;
                    } else if (ch == ',') {
                        fieldList.add(field.toString());
                        field = new StringBuilder();
                    } else if (ch == '\r') {
                        state = State.AFTER_CR;
                    } else if (ch == '\n') {
                        fieldList.add(field.toString());
                        return fieldList.toArray(new String[0]);
                    } else {
                        field.append((char) ch);
                        state = State.IN_FIELD;
                    }
                    break;
                case IN_FIELD:
                    if (ch == ',') {
                        fieldList.add(field.toString());
                        field = new StringBuilder();
                        state = State.AFTER_COMMA;
                    } else if (ch == '\r') {
                        state = State.AFTER_CR;
                    } else if (ch == '\n') {
                        fieldList.add(field.toString());
                        return fieldList.toArray(new String[0]);
                    } else {
                        field.append((char) ch);
                    }
                    break;
                case IN_QUOTE:
                    if (ch == '\"') {
                        state = State.QUOTE_IN_QUOTE;
                    } else {
                        field.append((char) ch);
                    }
                    break;
                case QUOTE_IN_QUOTE:
                    if (ch == '\"') {
                        field.append((char)ch);
                        state = State.IN_QUOTE;
                    } else if (ch == ',') {
                        fieldList.add(field.toString());
                        field = new StringBuilder();
                        state = State.AFTER_COMMA;
                    } else if (ch == '\r') {
                        state = State.AFTER_CR;
                    } else if (ch == '\n') {
                        fieldList.add(field.toString());
                        return fieldList.toArray(new String[0]);
                    } else {
                        throw new CsvParseException("ダブルクォートを閉じた後がカンマでも改行でもありません。");
                    }
                    break;
                case AFTER_CR:
                    if (ch == '\n') {
                        fieldList.add(field.toString());
                        return fieldList.toArray(new String[0]);
                    } else {
                        throw new CsvParseException("CRの後にLF以外の文字が来ました。(" + (char)ch + ")");
                    }
            }
        }
        if (field.length() > 0 || fieldList.size() > 0) {
            fieldList.add(field.toString());
            return fieldList.toArray(new String[0]);
        } else {
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}

package test;


import main.java.SimpleParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleParserTest {
  
    private SimpleParser setup(String loc) {
        SimpleParser parser = new SimpleParser();
        try {
            parser.runParser(new File(loc));
        } catch (IOException e) {
            assert(false);
        }
        return parser;
    }

    @Test
    public void testFileNotFound(){
        SimpleParser parser = new SimpleParser();
        assertThrows(FileNotFoundException.class, () -> {
            parser.runParser(new File("this is not a correct path"));
        });
    }

    @Test
    public void testInvalidFile() {
        SimpleParser parser = new SimpleParser();
        assertThrows(IOException.class, () -> {
            parser.runParser(new File ("src/test/invalidFile.cnf"));
        });
    }

    @Test
    public void testRunParser () {
        SimpleParser parser = setup("src/test/aim-100-1_6-no-1.cnf");

    }

    @Test
    public void testNumberOfVariables () {
        SimpleParser parser = setup("src/test/aim-100-1_6-no-1.cnf");
        assertEquals(100, parser.getNumberOfVariables());
    }

    @Test
    public void testNumberOfClauses () {
        SimpleParser parser = setup("src/test/aim-100-1_6-no-1.cnf");
        assertEquals(160, parser.getNumberOfClauses());
    }

    @Test
    public void testNumberOfUsedLiterals () {
        SimpleParser parser = setup("src/test/aim-100-1_6-no-1.cnf");
        assertEquals(200, parser.getNumberOfUsedLiterals());
    }

    @Test
    public void testClauseCount () {
        SimpleParser parser = setup("src/test/aim-100-1_6-no-1.cnf");
        assertEquals(160, parser.getClauseCount());
    }
}

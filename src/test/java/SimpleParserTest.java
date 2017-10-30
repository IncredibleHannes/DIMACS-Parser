package test;


import main.java.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleParserTest {

    private DimacsFile setup(String loc) throws Exception {
        DimacsFile parser = new DimacsFile(new File(loc));
        return parser;
    }

    @Test
    public void testNumberOfVariables () throws Exception {
        DimacsFile parser = setup("src/test/recources/testInput.cnf");
        assertEquals(100, parser.getNumberOfVariables());
    }

    @Test
    public void testNumberOfClauses () throws Exception {
        DimacsFile parser = setup("src/test/recources/testInput.cnf");
        assertEquals(160, parser.getNumberOfClauses());
    }
}

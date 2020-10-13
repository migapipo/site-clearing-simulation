package app;

import app.entity.Commands;
import app.exceptions.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandsTest {


    private String[] testCommandList;

    private String[] testCommandToStringList;

    @BeforeEach
    void init() {
        this.testCommandList = new String[] {"a 4", "advance        2", "l", "L","r", "R"};
        // expected command Object toString List
        this.testCommandToStringList = new String[] {"advance 4", "advance 2",
            "turn left", "turn left", "turn right", "turn right"};
    }

    /**
     * Validate each input command.
     * Valid command: Generate a corresponding command object if it is valid
     * Invalid command: throw parse Exception
     *
     * Test with some invalid inputs, parse Exception should be raised.
     */
    @Test
    void validateCommandAndConstruct() {
        List<Commands> commandObjList = new ArrayList<>();
        // 1. read input command String and validate, convert valid ones to corresponding command Objects
        for (String commandStr: testCommandList) {
            commandObjList.add(Commands.validateCommandAndConstruct(commandStr));
        }
        // 2. based on the commands Object, use toString() to get their commands representation
        for (int idx = 0; idx < this.testCommandToStringList.length; idx++) {
            assertEquals(this.testCommandToStringList[idx], commandObjList.get(idx).toString());
        }

        /* Test with some invalid cases. e.g advance with more than 1 step arguments, step cannot be a negative
        Integer, input in uppercase.*/
        assertThrows(ParseException.class, () -> Commands.validateCommandAndConstruct("a -10"));
        assertThrows(ParseException.class, () -> Commands.validateCommandAndConstruct("a 10 5"));
        assertThrows(ParseException.class, () -> Commands.validateCommandAndConstruct("TURN LEFT"));
    }
}

package spreadsheet;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SpreadsheetTest {

    private Spreadsheet spreadsheet = new Spreadsheet();

    @Test
    void happy_flow_test_with_given_input() {
        String[][] input = {{"A2", "4 5 *", "A1"},
                {"A1 B2 / 2 +", "3", "39 B1 B2 * /"}};
        String[][] expected = {{"20.00000", "20.00000", "20.00000"},
                {"8.66667", "3.00000", "1.50000"}};
        Assertions.assertFalse(spreadsheet.processMatrix(spreadsheet, input), "Expected to process but failed");
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                Assertions.assertEquals(input[i][j], expected[i][j], "Expected to be equal");
            }
        }
    }

    @Test
    void test_cyclic_dependency() {
        String[][] input = {{"A2", "4 5 *", "A1"},
                {"A1 B2 / 2 +", "3", "39 B1 B2 * /"},
                {"C3", "-40", "C1"}};
        Assertions.assertTrue(spreadsheet.processMatrix(spreadsheet, input), "Expected to failed but processed");

    }

    @Test
    void happy_flow_test_with_negative_value() {
        String[][] input = {{"A2", "4 5 *", "A1"},
                {"A1 B2 / 2 +", "3", "39 B1 B2 * /"},
                {"C3", "-40", "-40 3 -"}};
        String[][] expected = {{"20.00000", "20.00000", "20.00000"},
                {"8.66667", "3.00000", "1.50000"},
                {"-43.00000", "-40.00000", "-43.00000"}};
        Assertions.assertFalse(spreadsheet.processMatrix(spreadsheet, input), "Expected to process but failed");
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                Assertions.assertEquals(input[i][j], expected[i][j], "Expected to be equal");
            }
        }
    }

    @Test
    void happy_flow_test_with_increment_operator() {
        String[][] input = {{"A2", "4 5 *", "A1++"},
                {"A1 B2 / 2 +", "3", "39 B1 B2 * /"},
                {"C3", "-40", "-40 3 -"}};
        String[][] expected = {{"20.00000", "20.00000", "21.00000"},
                {"8.66667", "3.00000", "1.50000"},
                {"-43.00000", "-40.00000", "-43.00000"}};
        Assertions.assertFalse(spreadsheet.processMatrix(spreadsheet, input), "Expected to process but failed");
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                Assertions.assertEquals(input[i][j], expected[i][j], "Expected to be equal");
            }
        }
    }

    @Test
    void happy_flow_test_with_decrement_operator() {
        String[][] input = {{"A2", "4 5 *", "A1"},
                {"A1 B2 / 2 +", "3", "39 B1 B2 * /"},
                {"--C3", "-40", "-40 3 -"}};
        String[][] expected = {{"20.00000", "20.00000", "20.00000"},
                {"8.66667", "3.00000", "1.50000"},
                {"-44.00000", "-40.00000", "-43.00000"}};
        Assertions.assertFalse(spreadsheet.processMatrix(spreadsheet, input), "Expected to process but failed");
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                Assertions.assertEquals(input[i][j], expected[i][j], "Expected to be equal");
            }
        }
    }

}
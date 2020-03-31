package ua.com.foxminded.task_6.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.task_6.exceptions.FileNotFoundException;
import ua.com.foxminded.task_6.exceptions.FileProcessingException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class RacingTopTest {

    private RacingTop racingTop;
    private String startLog;
    private String endLog;
    private String abbreviations;
    private String testFile;

    @BeforeEach
    void init() {
        racingTop = new RacingTop();
        startLog = getAbsolutePath("src/test/resources/start.log");
        endLog = getAbsolutePath("src/test/resources/end.log");
        abbreviations = getAbsolutePath("src/test/resources/abbreviations.txt");
        testFile = getAbsolutePath("src/test/resources/testFile.txt");
    }

    String getAbsolutePath(String path) {
        File file = new File(path);
        return file.getAbsolutePath();
    }

    @Test
    void getReadFileShouldThrowNoSuchFileExceptionWhenWrongPathOrFileNotExistForFirstParam() {
        assertThrows(FileNotFoundException.class, () -> racingTop.readFile("wrong/file", endLog, abbreviations));
    }

    @Test
    void getReadFileShouldThrowNoSuchFileExceptionWhenWrongPathOrFileNotExistForSecondParam() {
        assertThrows(FileNotFoundException.class, () -> racingTop.readFile(startLog, "wrong/file", abbreviations));
    }

    @Test
    void getReadFileShouldThrowNoSuchFileExceptionWhenWrongPathOrFileNotExistForThirdParam() {
        assertThrows(FileNotFoundException.class, () -> racingTop.readFile(startLog, endLog, "wrong/file"));
    }

    @Test
    void getReadFileShouldThrowFileProcessingExceptionWhenFilesInappropriateFirstParam() {
        assertThrows(FileProcessingException.class, () -> racingTop.readFile(testFile, endLog, abbreviations));
    }

    @Test
    void getReadFileShouldThrowFileProcessingExceptionWhenFilesInappropriateForSecondParam() {
        assertThrows(FileProcessingException.class, () -> racingTop.readFile(startLog, testFile, abbreviations));
    }

    @Test
    void getReadFileShouldThrowFileProcessingExceptionWhenFilesInappropriateForThirdParam() {
        assertThrows(FileProcessingException.class, () -> racingTop.readFile(startLog, endLog, testFile));
    }

    @Test
    void getReadFileShouldReturnTableOfRacers() {
        racingTop.readFile(startLog, endLog, abbreviations);
        String expected = "1. Sebastian Vettel |  FERRARI  | 1:04.60575\n" +
                "2. Daniel Ricciardo |  RED BULL RACING TAG HEUER  | 1:12.67693\n" +
                "3. Valtteri Bottas |  MERCEDES  | 1:12.68114\n" +
                "4. Lewis Hamilton |  MERCEDES  | 1:12.68140\n" +
                "5. Stoffel Vandoorne |  MCLAREN RENAULT  | 1:12.68143\n" +
                "6. Kimi Raikkonen |  FERRARI  | 1:12.68319\n" +
                "7. Fernando Alonso |  MCLAREN RENAULT  | 1:12.68337\n" +
                "8. Sergey Sirotkin |  WILLIAMS MERCEDES  | 1:12.68386\n" +
                "9. Charles Leclerc |  SAUBER FERRARI  | 1:12.68509\n" +
                "10. Sergio Perez |  FORCE INDIA MERCEDES  | 1:12.68528\n" +
                "11. Romain Grosjean |  HAAS FERRARI  | 1:12.68610\n" +
                "12. Pierre Gasly |  SCUDERIA TORO ROSSO HONDA  | 1:12.68621\n" +
                "13. Carlos Sainz |  RENAULT  | 1:12.68630\n" +
                "14. Esteban Ocon |  FORCE INDIA MERCEDES  | 1:13.68648\n" +
                "15. Nico Hulkenberg |  RENAULT  | 1:13.68685\n" +
                "-------------------------------------------------------\n" +
                "16. Brendon Hartley |  SCUDERIA TORO ROSSO HONDA  | 1:13.68799\n" +
                "17. Marcus Ericsson |  SAUBER FERRARI  | 1:13.68885\n" +
                "18. Lance Stroll |  WILLIAMS MERCEDES  | 1:13.68943\n" +
                "19. Kevin Magnussen |  HAAS FERRARI  | 1:13.69013\n";

        String actual = racingTop.readFile(startLog, endLog, abbreviations);
        assertEquals(expected, actual);
    }

}


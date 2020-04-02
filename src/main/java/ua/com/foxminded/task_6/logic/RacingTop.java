package ua.com.foxminded.task_6.logic;

import ua.com.foxminded.task_6.exceptions.FileProcessingException;

import java.util.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class RacingTop {

    private final static String DELIMITER = "_";
    private final static int DROPOUT = 15;
    private static final TimeUnit TIME_UNIT_MILLIS = TimeUnit.MILLISECONDS;
    private static final int ABBREVIATION_LENGTH = 3;

    private static final String FILE_LINE_TIME_RACERS_PATTERN = "(\\w{3}\\d{4})-(\\d{2})-(\\d{2})_(\\d{2}):(\\d{2}):(\\d{2}).(\\d+)";
    private static final String FILE_LINE_ABBREVIATIONS_PATTERN = "(\\w{3})_[\\w\\s]+_[\\w\\s]+";

    public String readFile(String initialFileName, String finalFileName, String abbreviationFileName) {
        Map<String, String> tableRacers = null;

        Map<String, String> dateTimeStart;
        Map<String, String> dateTimeEnd;
        Map<String, String> abbreviations;

        try (Stream<String> initialData = Files.lines(Paths.get(initialFileName));
             Stream<String> finalData = Files.lines(Paths.get(finalFileName));
             Stream<String> setAbbreviations = Files.lines(Paths.get(abbreviationFileName))) {

            ArrayList<String> linesInitialFile = initialData
                    .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<String> linesFinalFile = finalData
                    .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<String> linesAbbreviationFile = setAbbreviations
                    .collect(Collectors.toCollection(ArrayList::new));

            boolean initialFileCheck = linesInitialFile.stream()
                    .allMatch(line -> line.matches(FILE_LINE_TIME_RACERS_PATTERN));
            boolean finalFileCheck = linesFinalFile.stream()
                    .allMatch(line -> line.matches(FILE_LINE_TIME_RACERS_PATTERN));
            boolean abbreviationFileCheck = linesAbbreviationFile.stream()
                    .allMatch(line -> line.matches(FILE_LINE_ABBREVIATIONS_PATTERN));

            if ((initialFileCheck) && (finalFileCheck) && (abbreviationFileCheck)) {

                dateTimeStart = parseFile(linesInitialFile);
                dateTimeEnd = parseFile(linesFinalFile);
                abbreviations = parseFileAbbreviation(linesAbbreviationFile);

            } else {
                throw new FileProcessingException("Inappropriate file was input");
            }

            Map<String, String> abbreviationsTime = Stream.concat(dateTimeStart.entrySet().stream(), dateTimeEnd.entrySet().stream())
                    .collect(Collectors.toMap(
                            changedKey -> changedKey.getKey().substring(0, ABBREVIATION_LENGTH),
                            Map.Entry::getValue,
                            this::getDuration));

            Map<String, String> combinedNameTime = Stream.concat(
                    abbreviationsTime.entrySet().stream(),
                    abbreviations.entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (valueTime, valueAbbreviations) -> valueAbbreviations + DELIMITER + valueTime));

            String nameTime = getValues(combinedNameTime);

            Map<String, String> resultNameTime = Arrays.stream(nameTime.split(","))
                    .map(value -> value.split(DELIMITER))
                    .collect(Collectors.toMap(key -> key[0], value -> value[1]));

            tableRacers = sortByValue(resultNameTime);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawTableRacers(tableRacers);
    }


    private String drawTableRacers(Map<String, String> map) {
        StringBuilder tableRacers = new StringBuilder();
        int counter = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (counter == DROPOUT) {
                tableRacers.append("-------------------------------------------------------").append("\n");
            }
            tableRacers.append(++counter + ". " + entry.getKey() + "  | " + entry.getValue() + "\n");
        }
        return tableRacers.toString();
    }

    private Map sortByValue(Map<String, String> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (firstValue, secondValue) -> firstValue, LinkedHashMap::new));
    }

    private String getValues(Map<String, String> map) {
        return map.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.joining(","));
    }

    private Map<String, String> parseFile(List<String> list) {
        return list.stream().map(sentence -> sentence.split(DELIMITER))
                .collect(toMap(key -> key[0], value -> value[1]));
    }

    private Map<String, String> parseFileAbbreviation(List<String> list) {
        return list.stream().map(sentence -> sentence.split(DELIMITER, ABBREVIATION_LENGTH))
                .collect(toMap(key -> key[0], value -> value[1] + " |  " + value[2]));
    }

    private String getDuration(String firstTime, String secondTime) {
        long duration = Duration.between(LocalTime.parse(firstTime), LocalTime.parse(secondTime)).toMillis();

        long minutes = TIME_UNIT_MILLIS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TIME_UNIT_MILLIS.toHours(duration));
        long seconds = TIME_UNIT_MILLIS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TIME_UNIT_MILLIS.toMinutes(duration));
        long millis = TIME_UNIT_MILLIS.toMillis(duration) - TimeUnit.MINUTES.toSeconds(TIME_UNIT_MILLIS.toSeconds(duration));

        return String.format("%01d:%02d.%d",
                minutes, seconds, millis
        );
    }
}
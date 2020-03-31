package ua.com.foxminded.task_6.logic;


import ua.com.foxminded.task_6.exceptions.FileNotFoundException;
import ua.com.foxminded.task_6.exceptions.FileProcessingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class RacingTop {

    private final static String DELIMITER = "_";
    private final static int DROPOUT = 15;
    private static final TimeUnit TIME_UNIT_MILLIS = TimeUnit.MILLISECONDS;


    public String readFile(String initialFileName, String finalFileName, String abbreviationFileName)  {
        Map<String, String> tableRacers = null;

        try (Stream<String> initialData = Files.lines(Paths.get(initialFileName));
             Stream<String> finalData = Files.lines(Paths.get(finalFileName));
             Stream<String> setAbbreviations = Files.lines(Paths.get(abbreviationFileName))) {

            Map<String, String> dateTimeStart = parseFile(initialData);

            Map<String, String> dateTimeEnd = parseFile(finalData);

            Map<String, String> abbreviations = setAbbreviations.map(str -> str.split(DELIMITER, 3))
                    .collect(toMap(key -> key[0], value -> value[1] + " |  " + value[2]));


            Map<String, String> abbreviationsTime = Stream.concat(dateTimeStart.entrySet().stream(), dateTimeEnd.entrySet().stream())
                    .collect(Collectors.toMap(
                            changedKey -> changedKey.getKey().substring(0, 3),
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
                    .map(s -> s.split(DELIMITER))
                    .collect(Collectors.toMap(s -> s[0], s -> s[1]));

            tableRacers = sortByValue(resultNameTime);


        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FileProcessingException("Wrong data in file",e);
        } catch (IOException e) {
            throw new FileNotFoundException("No such file",e);
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
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private String getValues(Map<String, String> map) {
        return map.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.joining(","));
    }

    private Map parseFile(Stream<String> stream) {
        return stream.map(sentence -> sentence.split("_"))
                .collect(toMap(key -> key[0], value -> value[1]));
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


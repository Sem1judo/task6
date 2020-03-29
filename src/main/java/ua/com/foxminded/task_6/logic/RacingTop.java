package ua.com.foxminded.task_6.logic;

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

    public static final String DELIMITER = "_";

    public void readFile(String fileName1, String fileName2, String fileName3) {
        try (Stream<String> stream1 = Files.lines(Paths.get(fileName1));
             Stream<String> stream2 = Files.lines(Paths.get(fileName2));
             Stream<String> stream3 = Files.lines(Paths.get(fileName3))) {



            //creating maps
            Map<String, String> file1 = stream1.map(str -> str.split(DELIMITER))
                    .collect(toMap(str -> str[0], str -> str[1]));

            Map<String, String> file2 = stream2.map(str -> str.split(DELIMITER))
                    .collect(toMap(str -> str[0], str -> str[1]));

            Map<String, String> abbreviationMap = stream3.map(str -> str.split(DELIMITER, 3))
                    .collect(toMap(str -> str[0], str -> str[1] + " |  " + str[2]));


            Map<String, String> abbreviationsTime = Stream.concat(file1.entrySet().stream(), file2.entrySet().stream())
                    .collect(Collectors.toMap(
                            s -> s.getKey().substring(0, 3),
                            Map.Entry::getValue,
                            (v1, v2) -> String.format("%01d:%02d.%d",
                                    TimeUnit.MILLISECONDS.toMinutes(
                                            Duration.between(LocalTime.parse(v1), LocalTime.parse(v2)).toMillis())
                                            - TimeUnit.HOURS.toMinutes
                                            (TimeUnit.MILLISECONDS.toHours(Duration.between(LocalTime.parse(v1), LocalTime.parse(v2)).toMillis())),
                                    TimeUnit.MILLISECONDS.toSeconds(Duration.between(LocalTime.parse(v1), LocalTime.parse(v2)).toMillis())
                                            - TimeUnit.MINUTES.toSeconds(
                                            TimeUnit.MILLISECONDS.toMinutes(Duration.between(LocalTime.parse(v1), LocalTime.parse(v2)).toMillis())),
                                    TimeUnit.MILLISECONDS.toMillis(Duration.between(LocalTime.parse(v1), LocalTime.parse(v2)).toMillis())
                                            - TimeUnit.MINUTES.toSeconds(
                                            TimeUnit.MILLISECONDS.toSeconds(Duration.between(LocalTime.parse(v1), LocalTime.parse(v2)).toMillis())
                                    ))
                    ));


            Map<String, String> combinedNameTime = Stream.concat(
                    abbreviationsTime.entrySet().stream(),
                    abbreviationMap.entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (value1, value2) -> value2 + DELIMITER + value1));

            String valuesString = combinedNameTime.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.joining(","));

            Map<String, String> reconstructedUtilMap = Arrays.stream(valuesString.split(","))
                    .map(s -> s.split(DELIMITER))
                    .collect(Collectors.toMap(s -> s[0], s -> s[1]));

            Map<String, String> sortedByValue = reconstructedUtilMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            int counter = 1;
            for (Map.Entry<String, String> entry : sortedByValue.entrySet()) {
                if (counter==16){
                    System.out.println("-------------------------------------------------------");
                }
                System.out.println(counter++ +". "+entry.getKey() + "  | " + entry.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package ua.com.foxminded.task_6.logic;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class RacingTop {


    public void readFile(String fileName1, String fileName2, String fileName3) {
        try (Stream<String> stream1 = Files.lines(Paths.get(fileName1));
             Stream<String> stream2 = Files.lines(Paths.get(fileName2));
             Stream<String> stream3 = Files.lines(Paths.get(fileName3))) {


            Map<String, String> file1 = stream1.map(str -> str.split("_"))
                    .collect(toMap(str -> str[0], str -> str[1]));

            Map<String, String> file2 = stream2.map(str -> str.split("_"))
                    .collect(toMap(str -> str[0], str -> str[1]));

            Map<String, String> resultFileTime = Stream.concat(file1.entrySet().stream(), file2.entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
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


            Map<String, String> result = resultFileTime.entrySet().stream().collect(
                    Collectors.toMap(s -> s.getKey().substring(0, 3), s -> s.getValue()));

            Map<String, String> sortedByCount = resultFileTime.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            System.out.println(sortedByCount);


            Map<String, String> abbreviationMap = stream3.map(str -> str.split("_", 3))
                    .collect(toMap(str -> str[0], str -> str[1] + "  | " + str[2]));

            Map<String, String> finalResult = Stream.concat(result.entrySet().stream(), abbreviationMap.entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (value1, value2) -> value2 + "  | " + value1));



            ArrayList<String> valueList = new ArrayList<String>(finalResult.values());
            valueList.stream().forEach(s -> System.out.println(s));

            valueList.stream().count();
            System.out.println();

            System.out.println(valueList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

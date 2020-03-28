package ua.com.foxminded.task_6;

import ua.com.foxminded.task_6.logic.RacingTop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class App {

    public static void main(String[] args) throws IOException {


        String f1 = "C:\\Users\\User\\IdeaProjects\\task6\\src\\main\\resources\\start.log";
        String f2 = "C:\\Users\\User\\IdeaProjects\\task6\\src\\main\\resources\\end.log";
        String f3 = "C:\\Users\\User\\IdeaProjects\\task6\\src\\main\\resources\\abbreviations.txt";
        RacingTop racingTop = new RacingTop();
        racingTop.readFile(f1, f2, f3);


        LocalTime localDateTime = LocalTime.parse("12:10:51.985");
        LocalTime localDateTime2 = LocalTime.parse("12:16:05.164");
//        long minutes = ChronoUnit.MINUTES.between(localDateTime, localDateTime2);
//
//
//        System.out.println(Duration.between(localDateTime, localDateTime2));

        Duration dur = Duration.between(localDateTime, localDateTime2);
        long millis = dur.toMillis();


        System.out.println(String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
                TimeUnit.MILLISECONDS.toMillis(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toSeconds(millis))

                )
        );

////05:13.313179 // 05:313.313179
    }
}

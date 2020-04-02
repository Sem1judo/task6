package ua.com.foxminded.task_6;

import ua.com.foxminded.task_6.exceptions.FileProcessingException;
import ua.com.foxminded.task_6.logic.RacingTop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class App {

    public static void main(String[] args) {


        String f1 = "src/resources/start.log";
        String f2 = "src/resources/end.log";
        String f3 = "src/resources/abbreviations.txt";
        RacingTop racingTop = new RacingTop();

        System.out.println(racingTop.readFile(f1, f2, f3));






//        System.out.println(String.format("%02d:%02d:%02d",
//                TimeUnit.MILLISECONDS.toMinutes(millis)
//                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
//                TimeUnit.MILLISECONDS.toSeconds(millis)
//                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
//                TimeUnit.MILLISECONDS.toMillis(millis) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toSeconds(millis))
//
//                )
//        );

    }
}

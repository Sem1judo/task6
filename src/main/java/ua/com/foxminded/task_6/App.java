package ua.com.foxminded.task_6;

import ua.com.foxminded.task_6.exceptions.FileProcessingException;
import ua.com.foxminded.task_6.logic.RacingTop;

import java.io.IOException;


public class App {

    public static void main(String[] args) throws IOException, FileProcessingException {


        String f1 = "src/test/resources/start.log";
        String f2 = "src/test/resources/end.log";
        String f3 = "src/test/resources/abbreviations.txt";
        RacingTop racingTop = new RacingTop();
        System.out.println(racingTop.readFile(f2, f2, f2));






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

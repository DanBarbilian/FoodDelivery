package Utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Logger {
    private static Logger instance;
    private static String filePath;

    public Logger() {
        filePath = "logs.csv";

        File file = new File(filePath);
        if(file.exists()) {
            file.delete();
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public static @NotNull
    ArrayList<String> readItems() {
        var res = new ArrayList<String>();
        BufferedReader csvReader;
        try {
            csvReader  = new BufferedReader(new FileReader(filePath));
            String line = csvReader.readLine();
            while (line != null) {
                res.add(line);
                line = csvReader.readLine();
            }
            csvReader.close();
        }
        catch(java.io.IOException e) {
            System.out.println("Log file could not be opened!");
        }
        return res;
    }

    public static <T extends CSVable<T>> void writeItem(T t) {
        var data = t.convertToString();
        var csv =  Stream.of(data).collect(Collectors.joining(","));
        FileWriter csvOutputFile;
        try {
            csvOutputFile = new FileWriter(filePath, true);
            csvOutputFile.write(csv);
            csvOutputFile.write('\n');
            csvOutputFile.close();
        }
        catch(java.io.IOException e) {
            System.out.println("Log file could not be opened!");

        }
    }

    public static <T extends CSVable<T>> void writeItems(ArrayList<T> lst) {
        FileWriter csvOutputFile;
        try {
            csvOutputFile = new FileWriter(filePath);
            for(var entry : lst) {
                var data = entry.convertToString();
                var csv =  Stream.of(data).collect(Collectors.joining(","));

                csvOutputFile.write(csv);
                csvOutputFile.write('\n');
            }
            csvOutputFile.close();
        }
        catch(java.io.IOException e) {
            System.out.println("Log file could not be opened!");
        }
    }

    public void logOperation(String log) {
        writeItem(new Log(ZonedDateTime.now(), log));
    }
}

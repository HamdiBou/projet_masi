package behavioral.strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileLogger implements LoggerStrategy {
    private String filename = "log.txt";

    @Override
    public void log(String message) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(LocalDateTime.now() + " [File] " + message + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Erreur d'écriture dans le fichier de log : " + e.getMessage());
        }
    }
}

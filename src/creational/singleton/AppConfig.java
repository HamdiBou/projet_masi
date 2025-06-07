package creational.singleton;

public class AppConfig {
    private static AppConfig instance;

    private String defaultDrawingFile = "drawing.txt";
    private String defaultLogFile = "log.txt";

    private AppConfig() {}

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getDefaultDrawingFile() {
        return defaultDrawingFile;
    }

    public void setDefaultDrawingFile(String file) {
        this.defaultDrawingFile = file;
    }

    public String getDefaultLogFile() {
        return defaultLogFile;
    }

    public void setDefaultLogFile(String file) {
        this.defaultLogFile = file;
    }
}

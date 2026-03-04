package config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    private static final String FILE = "config.properties";

    public static int getRowsPerPage() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(FILE)) {
            props.load(fis);
            return Integer.parseInt(props.getProperty("rowsPerPage", "5"));
        } catch (Exception e) {
            return 5;
        }
    }

    public static void saveRowsPerPage(int rows) {
        Properties props = new Properties();
        props.setProperty("rowsPerPage", String.valueOf(rows));
        try (FileOutputStream fos = new FileOutputStream(FILE)) {
            props.store(fos, null);
        } catch (IOException e) {
            System.err.println("Failed to save config.");
        }
    }
}

package censusanalyser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonIOService
{
    public static void writeJsonFile(String fileName, String list) throws IOException {
        try (Writer writer = Files.newBufferedWriter(Paths.get("C:\\Users\\PUNIT BUDANIA\\IdeaProjects\\CensusAnalyser\\src\\main\\java\\censusanalyser\\" + fileName + ".json"));)
        {
            writer.write(list);
        }
    }
}

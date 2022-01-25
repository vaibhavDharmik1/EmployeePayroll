
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;



public class NIOFileAPITest {

    private static String HOME = System.getProperty("user.home");
    private static String PLAY_WITH_NIO = "TempPlayGround";

    @Test
    public void givenPath_WhenChecked_ShouldConfirm() throws IOException {

        // Check File Exists
        Path homePath = Paths.get(HOME);
        Assertions.assertTrue(Files.exists(homePath));

        // Delete File and Check File Not Exists
        Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
        if (Files.exists(playPath))
            FileUtils.deleteFolder(playPath.toFile());
        Assertions.assertTrue(Files.notExists(playPath));

        // Create Directory
        Files.createDirectories(playPath);
        Assertions.assertTrue(Files.exists(playPath));

        // CreateFile
        IntStream.range(1, 10).forEach(counter -> {
            Path tempFile = Paths.get(playPath + "/temp" + counter);
            Assertions.assertTrue(Files.notExists(tempFile));
            try {
                Files.createFile(tempFile);
            } catch (IOException e) {

            }
            Assertions.assertTrue(Files.exists(tempFile));
        });

        // List Files, Directories as well as Files with Extension
        Files.list(playPath).filter(Files::isRegularFile)
                .forEach(System.out::println);

        Files.newDirectoryStream(playPath).forEach(System.out::println);

        Files.newDirectoryStream(playPath, path -> path.toFile().isFile() && path.toString().startsWith("temp"))
                .forEach(System.out::println);
    }

    @Test
    public void givenDirectory_WhenWatched_ShouldReturnAllActivities() throws IOException
    {
        Path dir = Paths.get(HOME+'/'+PLAY_WITH_NIO);
        Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
        new Java8WatchService(dir).processEvents();
    }
}
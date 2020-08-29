package shell;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RunShell {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> list = new ArrayList<>();
        list.add("./runme.sh");
        Process process = createProcess(list, "/test", "/test");
        long processId = getPidOfProcess(process);
        System.out.println(processId); // out of exec is saved in /test


        FileWriter fileWriter = new FileWriter(new File("/root/exceeddata/code/myjava/myutils/src/main/java/shell/tmp.txt"));

    }

    private static Process createProcess(
            final List<String> cmds,
            final String jobDirectory,
            final String logDirectory) throws IOException {
        final ProcessBuilder builder = new ProcessBuilder(cmds);
        builder.directory(new File(jobDirectory));
        builder.redirectError(new File(logDirectory + "/stderr.out"));
        builder.redirectOutput(new File(logDirectory + "/stdout.out"));

        final Process shell = builder.start();
        return shell;
    }

    private static long getPidOfProcess(final Process p) {
        long pid = -1;

        try {
            if (p.getClass().getName().equals("java.lang.UNIXProcess")) {
                final Field f = p.getClass().getDeclaredField("pid");
                f.setAccessible(true);
                pid = f.getLong(p);
                f.setAccessible(false);
            }
        } catch (Exception e) {
            pid = -1;
        }
        return pid;
    }
}

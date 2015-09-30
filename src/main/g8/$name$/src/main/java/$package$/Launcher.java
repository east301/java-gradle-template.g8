package $package$;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Launches $name$ services.
 */
public final class Launcher {

    private static final Map<String, Consumer<String[]>> ENTRY_POINTS = ImmutableMap.of(
        "cli", Cli::main,
        "desktop", Desktop::main,
        "server", Server::main
    );

    private Launcher() {
        // do nothing
    }

    /**
     * Entry point of the program.
     *
     * @param args  command line arguments
     */
    public static void main(String... args) {
        //
        if (args.length < 1 || !ENTRY_POINTS.containsKey(args[0])) {
            System.err.printf("usage: gprdb {%s} ...\n", Joiner.on(",").join(ENTRY_POINTS.keySet()));
            System.err.printf("gprdb: error: too few arguments, or invalid action name\n");
            return;
        }

        //
        ENTRY_POINTS.get(args[0]).accept(Arrays.stream(args).skip(1).toArray(String[]::new));
    }

}

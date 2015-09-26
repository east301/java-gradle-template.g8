package $package$;

import mockit.Deencapsulation;
import mockit.Delegate;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class LauncherTest {

    ///
    /// constructor
    ///

    @Test
    public void constructor_is_private() throws Exception {
        Constructor<Launcher> constructor = Launcher.class.getDeclaredConstructor();
        assertThat(constructor.isAccessible()).isFalse();

        constructor.setAccessible(true);
        assertThat(constructor.newInstance()).isNotNull();
    }

    ///
    /// main
    ///

    @Test
    public void main__error_shown_if_no_arguments_given(
        @Mocked Map<String, Consumer<String[]>> entryPoints) {

        Deencapsulation.setField(Launcher.class, "ENTRY_POINTS", entryPoints);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream stream = new PrintStream(buffer)) {
            System.setErr(stream);
            Launcher.main();
        }

        assertThat(buffer.toString())
            .contains("gprdb: error: too few arguments, or invalid action name");

        new Verifications() {{
            entryPoints.containsKey(any); times = 0;
            entryPoints.get(any); times = 0;
        }};
    }

    @Test
    public void main__error_shown_if_invalid_action_given(
        @Mocked Map<String, Consumer<String[]>> entryPoints) {

        new NonStrictExpectations() {{
            entryPoints.containsKey("foo"); result = false;
        }};

        Deencapsulation.setField(Launcher.class, "ENTRY_POINTS", entryPoints);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream stream = new PrintStream(buffer)) {
            System.setErr(stream);
            Launcher.main("foo");
        }

        assertThat(buffer.toString())
            .contains("gprdb: error: too few arguments, or invalid action name");

        new Verifications() {{
            entryPoints.containsKey("foo"); times = 1;
            entryPoints.get(any); times = 0;
        }};
    }

    @Test
    public void main_correctly_invokes_specified_action__001(
        @Mocked Map<String, Consumer<String[]>> entryPoints, @Mocked Consumer<String[]> action) {

        //
        AtomicReference<String[]> specifiedArguments = new AtomicReference<>();

        new NonStrictExpectations() {{
            entryPoints.containsKey("foo"); result = true;
            entryPoints.get("foo"); result = action;

            action.accept((String[]) any); result = new Delegate<Void>() {
                public void handle(String[] arg) {
                    specifiedArguments.set(arg);
                }
            };
        }};

        Deencapsulation.setField(Launcher.class, "ENTRY_POINTS", entryPoints);

        //
        Launcher.main("foo");

        assertThat(specifiedArguments.get()).isNotNull().isEmpty();

        new Verifications() {{
            entryPoints.containsKey("foo"); times = 1;
            entryPoints.get("foo"); times = 1;
            action.accept((String[]) any); times = 1;
        }};
    }

    @Test
    public void main_correctly_invokes_specified_action__002(
        @Mocked Map<String, Consumer<String[]>> entryPoints, @Mocked Consumer<String[]> action) {

        //
        AtomicReference<String[]> specifiedArguments = new AtomicReference<>();

        new NonStrictExpectations() {{
            entryPoints.containsKey("foo"); result = true;
            entryPoints.get("foo"); result = action;

            action.accept((String[])any); result = new Delegate<Void>() {
                public void handle(String[] arg) {
                    specifiedArguments.set(arg);
                }
            };
        }};

        Deencapsulation.setField(Launcher.class, "ENTRY_POINTS", entryPoints);

        //
        Launcher.main("foo", "bar", "baz");

        assertThat(specifiedArguments.get()).isNotNull().hasSize(2).containsSequence("bar", "baz");

        new Verifications() {{
            entryPoints.containsKey("foo"); times = 1;
            entryPoints.get("foo"); times = 1;
            action.accept((String[])any); times = 1;
        }};
    }

}

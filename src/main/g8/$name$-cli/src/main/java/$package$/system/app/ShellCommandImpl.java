package $package$.system.app;

import com.google.common.collect.ImmutableMap;
import groovy.lang.Binding;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparsers;
import org.codehaus.groovy.tools.shell.Groovysh;
import org.codehaus.groovy.tools.shell.IO;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

/**
 * An implementation of {@link CliCommand} to run groovy shell.
 */
@Component
class ShellCommandImpl implements CliCommand {

    private static final String COMMAND = "shell";

    @Override
    public Optional<String> register(@NotNull Subparsers subparsers) {
        subparsers.addParser(COMMAND);
        return Optional.of(COMMAND);
    }

    @Override
    public void execute(@NotNull Namespace arguments, @NotNull ApplicationContext applicationContext) {
        //
        ImmutableMap<String, Class<?>> beans = ImmutableMap.<String, Class<?>>builder()
            .build();

        //
        Binding binding = new Binding();
        for (Map.Entry<String, Class<?>> entry : beans.entrySet()) {
            binding.setProperty(entry.getKey(), applicationContext.getBean(entry.getValue()));
        }

        //
        Groovysh shell = newGroovysh(binding);
        shell.run("");
    }

    @NotNull
    private static Groovysh newGroovysh(@NotNull Binding binding) {
        // This method is for unit testing
        return new Groovysh(binding, new IO());
    }

}

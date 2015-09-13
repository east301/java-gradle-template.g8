package $package$;

import org.springframework.stereotype.Component;

/**
 * {@link GretingService} implementation.
 */
@Component
class GreetingServiceImpl implements GreetingService {

    @Override
    public String greet() {
        return "Hello World";
    }

}

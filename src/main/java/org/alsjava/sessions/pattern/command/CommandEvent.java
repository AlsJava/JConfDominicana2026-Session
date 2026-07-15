package org.alsjava.sessions.pattern.command;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandEvent {

    Class<? extends Command> command() default Command.class;
}

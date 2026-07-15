package org.alsjava.sessions.pattern.command;

import lombok.extern.slf4j.Slf4j;
import org.alsjava.sessions.model.exception.CommandNotInheritException;
import org.alsjava.sessions.model.exception.DuplicateCommandException;
import org.alsjava.sessions.model.exception.InvalidCommandEventException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "pattern.cqrs", name = "enabled", havingValue = "true")
public class CommandProvider implements BeanPostProcessor {

    @SuppressWarnings("rawtypes")
    Map<Class<? extends Command>, CommandHandler> registry = new HashMap<>();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public CommandHandler<?, Command<?>> get(Class<? extends Command> c) {
        return registry.get(c);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (clazz.isAnnotationPresent(CommandEvent.class)) {
            if (bean instanceof CommandHandler commandHandler) {
                log.info("Configuring command: {}", clazz);
                Class<? extends Command> command = clazz.getAnnotation(CommandEvent.class).command();
                if (registry.containsKey(command)) {
                    throw new DuplicateCommandException(bean);
                }
                if (command.equals(Command.class)) {
                    throw new InvalidCommandEventException(bean);
                }
                registry.put(command, commandHandler);
            } else {
                throw new CommandNotInheritException(bean);
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}

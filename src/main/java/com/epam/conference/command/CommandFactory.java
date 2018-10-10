package com.epam.conference.command;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {

    private static final Logger LOGGER = LogManager
	    .getLogger(CommandFactory.class);

    public static Optional<Command> defineCommand(String actionName) {
	Optional<Command> current = Optional.empty();
	if (actionName == null) {
	    return current;
	}
	try {
	    CommandType type = CommandType.valueOf(actionName.toUpperCase());
	    current = Optional.of(type.getCommand());
	} catch (IllegalArgumentException e) {
	    LOGGER.error(actionName + " not defined as CommandTypeF.", e);
	}
	return current;
    }
}

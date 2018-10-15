package com.epam.conference.command;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class represents a factory for creating concrete {@code Command} objects.
 * 
 * @author Alexander Shishonok
 *
 */
public class CommandFactory {

    private static final String ENUM_WORD_DELIMITER = "_";
    private static final String COMMAND_WORD_DELIMITER = "-";

    private static final Logger LOGGER = LogManager
	    .getLogger(CommandFactory.class);

    private CommandFactory() {
    }

    /**
     * Define the concrete {@code Command} object to process client request.
     * 
     * @param actionName
     *            command name.
     * @return {@code Optional} object contained instance of class implemented
     *         {@code Command}.
     */
    public static Optional<Command> defineCommand(String actionName) {
	Optional<Command> current = Optional.empty();
	if (actionName == null) {
	    return current;
	}
	try {
	    CommandType type = CommandType.valueOf(actionName
		    .replace(COMMAND_WORD_DELIMITER, ENUM_WORD_DELIMITER)
		    .toUpperCase());
	    current = Optional.of(type.getCommand());
	} catch (IllegalArgumentException e) {
	    LOGGER.error(actionName + " not defined as CommandType.", e);
	}
	return current;
    }
}
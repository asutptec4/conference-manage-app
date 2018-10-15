package com.epam.conference.command;

import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;

/**
 * Base interface for command that is responsible for handling client request.
 * 
 * @author Alexander Shishonok
 *
 */
public interface Command {

    /**
     * The method performs actions depending on the passed parameters
     * encapsulated in {@code RequestContent} object.
     * 
     * @param requestContent
     *            an {@code RequestContent} object contains data for handling
     *            user request.
     * @return an {@code PageRouter} object as result of executing command.
     */
    PageRouter execute(RequestContent requestContent);
}

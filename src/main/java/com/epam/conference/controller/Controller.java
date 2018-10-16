package com.epam.conference.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.conference.command.Command;
import com.epam.conference.command.CommandFactory;
import com.epam.conference.command.base.EmptyCommand;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.util.constant.UriPathConstant;

/**
 * Main controller class of web app. Process all request from clients.
 * 
 * @author Alexander Shishonok
 *
 */
@WebServlet(urlPatterns = UriPathConstant.PATH_CONTROLLER)
public class Controller extends HttpServlet {

    private static final String PARAM_COMMAND = "command";
    private static final long serialVersionUID = 4836424767511542704L;

    /**
     * Method handle GET request.
     * 
     * @param req
     *            an object that contains the request
     * @param resp
     *            an object that contains the response
     * @throws ServletException
     *             if servlet could not handle request
     * @throws IOException
     *             if an input/output error is occurred
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	processRequest(req, resp);
    }

    /**
     * Method handle POST request.
     * 
     * @param req
     *            an object that contains the request
     * @param resp
     *            an object that contains the response
     * @throws ServletException
     *             if servlet could not handle request
     * @throws IOException
     *             if an input/output error is occurred
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	processRequest(req, resp);
    }

    /**
     * Method handle clients request based on parameter "command" of request.
     * 
     * @param req
     *            an object that contains the request
     * @param resp
     *            an object that contains the response
     * @throws ServletException
     *             if servlet could not handle request
     * @throws IOException
     *             if an input/output error is occurred
     */
    private void processRequest(HttpServletRequest req,
	    HttpServletResponse resp) throws ServletException, IOException {
	Optional<Command> command = CommandFactory
		.defineCommand(req.getParameter(PARAM_COMMAND));
	RequestContent requestContent = new RequestContent();
	requestContent.readRequest(req);
	PageRouter router = command.orElse(new EmptyCommand())
		.execute(requestContent);
	requestContent.updateRequest(req);
	if (requestContent.isInvalidateSession()) {
	    req.getSession().invalidate();
	}
	if (router.getPagePath() != null) {
	    if (router.getRouterType() == PageRouterType.FORWARD) {
		RequestDispatcher disp = getServletContext()
			.getRequestDispatcher(router.getPagePath());
		disp.forward(req, resp);
	    } else {
		resp.sendRedirect(req.getContextPath() + router.getPagePath());
	    }
	} else {
	    resp.sendRedirect(
		    req.getContextPath() + UriPathConstant.PATH_INDEX);
	}
    }
}

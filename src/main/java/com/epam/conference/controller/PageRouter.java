package com.epam.conference.controller;

/**
 * The {@code PageRouter} class encapsulate result of {@code Command} execution.
 * Result contain two field: a path of resource for request handle and type of
 * method for {@code RequestDispatcher} instance.
 * 
 * @author Alexander Shishonok
 *
 */
public class PageRouter {

    /**
     * Enum define {@code RequestDispatcher} method.
     * 
     * @author Alexander Shishonok
     *
     */
    public enum PageRouterType {
	FORWARD, REDIRECT;
    }

    /**
     * A {@code String} specifying the pathname to the resource.
     */
    private String pagePath;

    /**
     * Field define type of method for {@code RequestDispatcher} instance. By
     * default will used forward method.
     */
    private PageRouterType routerType = PageRouterType.FORWARD;

    public String getPagePath() {
	return pagePath;
    }

    public void setPagePath(String pagePath) {
	this.pagePath = pagePath;
    }

    public PageRouterType getRouterType() {
	return routerType;
    }

    public void setRouterType(PageRouterType routerType) {
	if (routerType == null) {
	    this.routerType = PageRouterType.FORWARD;
	}
	this.routerType = routerType;
    }

}

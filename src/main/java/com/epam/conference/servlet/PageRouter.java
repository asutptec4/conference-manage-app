package com.epam.conference.servlet;

public class PageRouter {

    public enum PageRouterType {
	FORWARD, REDIRECT;
    }

    private String pagePath;
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

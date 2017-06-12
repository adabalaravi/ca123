package com.accenture.sdp.csmac.controllers;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Stack;

import com.accenture.sdp.csmac.common.beans.BreadCrumbItemBean;

public class BreadCrumbController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6090695838816067739L;

	private Stack<BreadCrumbItemBean> breadCrumb;

	public BreadCrumbController() {
		super();
		breadCrumb = new Stack<BreadCrumbItemBean>();
	}

	public Stack<BreadCrumbItemBean> getBreadCrumb() {
		return breadCrumb;
	}

	public void resetBreadCrumb() {
		breadCrumb.clear();
	}

	public void goToBreadCrumb(BreadCrumbItemBean item) {
		int clean = breadCrumb.search(item);
		for (int i = 0; i < clean; i++) {
			breadCrumb.pop();
		}
		breadCrumb.push(item);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("");
		Iterator<BreadCrumbItemBean> i = breadCrumb.iterator();
		while (i.hasNext()) {
			buffer.append(i.next().getDefaultTitle());
			if (i.hasNext()) {
				buffer.append("->");
			}
		}
		return buffer.toString();
	}
}

/*
Copyright 2004-2007 University Corporation for Advanced Internet Development, Inc.
Copyright 2004-2007 The University Of Bristol

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package edu.internet2.middleware.grouper.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.internet2.middleware.grouper.GrouperSession;

/**
 * The interface which allows sites to veto whether a particular Subject gets 
 * particular menu items 
 * <p />
 * 
 * @author Gary Brown.
 * @version $Id: MenuFilter.java,v 1.1 2007-10-30 11:07:30 isgwb Exp $
 */

public interface MenuFilter {
	/**
	 * If valid do not veto, however, if valid, another MenuFilter may veto
	 * @param s
	 * @param menuItem
	 * @param request
	 * @return whether menuItem is valid for the curretn session subject
	 */
	public boolean isValid(GrouperSession s, Map menuItem, HttpServletRequest request );
}

/*
Copyright 2004-2005 University Corporation for Advanced Internet Development, Inc.
Copyright 2004-2005 The University Of Bristol

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

package edu.internet2.middleware.grouper.ui.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.internet2.middleware.grouper.GrouperSession;

/**
 * Top level Strut's action which does any necessary setup for display of 
 * MyGroups (My memberships). 
 * <p/>
<table width="75%" border="1">
  <tr bgcolor="#CCCCCC"> 
    <td width="51%"><strong><font face="Arial, Helvetica, sans-serif">Request 
      Parameter</font></strong></td>
    <td width="12%"><strong><font face="Arial, Helvetica, sans-serif">Direction</font></strong></td>
    <td width="37%"><strong><font face="Arial, Helvetica, sans-serif">Description</font></strong></td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr bgcolor="#CCCCCC"> 
    <td><strong><font face="Arial, Helvetica, sans-serif">Request Attribute</font></strong></td>
    <td><strong><font face="Arial, Helvetica, sans-serif">Direction</font></strong></td>
    <td><strong><font face="Arial, Helvetica, sans-serif">Description</font></strong></td>
  </tr>
  <tr bgcolor="#FFFFFF"> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr bgcolor="#CCCCCC"> 
    <td><strong><font face="Arial, Helvetica, sans-serif">Session Attribute</font></strong></td>
    <td><strong><font face="Arial, Helvetica, sans-serif">Direction</font></strong></td>
    <td><strong><font face="Arial, Helvetica, sans-serif">Description</font></strong></td>
  </tr>
  <tr bgcolor="#FFFFFF"> 
    <td height="28"><font face="Arial, Helvetica, sans-serif">functionalArea=MyGroups</font></td>
    <td><font face="Arial, Helvetica, sans-serif">OUT</font></td>
    <td><font face="Arial, Helvetica, sans-serif">Used by menu code</font></td>
  </tr>
  <tr bgcolor="#FFFFFF"> 
    <td height="28"><font face="Arial, Helvetica, sans-serif">title=groups.my</font></td>
    <td><font face="Arial, Helvetica, sans-serif">OUT</font></td>
    <td><font face="Arial, Helvetica, sans-serif">Key resolved in nav ResourceBundle</font></td>
  </tr>
  <tr bgcolor="#FFFFFF"> 
    <td height="28"><font face="Arial, Helvetica, sans-serif">subtitle</font></td>
    <td><font face="Arial, Helvetica, sans-serif">OUT</font></td>
    <td><font face="Arial, Helvetica, sans-serif">Removed</font></td>
  </tr>
  <tr bgcolor="#FFFFFF"> 
    <td height="28"><font face="Arial, Helvetica, sans-serif">browseMode=</font></td>
    <td><font face="Arial, Helvetica, sans-serif">OUT</font></td>
    <td><font face="Arial, Helvetica, sans-serif">Empty=My internally</font></td>
  </tr>
  <tr bgcolor="#FFFFFF"> 
    <td height="28"><font face="Arial, Helvetica, sans-serif">findForNode</font></td>
    <td><font face="Arial, Helvetica, sans-serif">OUT</font></td>
    <td><font face="Arial, Helvetica, sans-serif">Removed - cancels any search 
      /browse for new members / privilegees</font></td>
  </tr>
  <tr bgcolor="#FFFFFF"> 
    <td height="28"><font face="Arial, Helvetica, sans-serif">findForPriv</font></td>
    <td><font face="Arial, Helvetica, sans-serif">OUT</font></td>
    <td><font face="Arial, Helvetica, sans-serif">Removed</font></td>
  </tr>
  <tr bgcolor="#CCCCCC"> 
    <td><strong><font face="Arial, Helvetica, sans-serif">Strut's Action Parameter</font></strong></td>
    <td><strong><font face="Arial, Helvetica, sans-serif">Direction</font></strong></td>
    <td><strong><font face="Arial, Helvetica, sans-serif">Description</font></strong></td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>

 * @author Gary Brown.
 * @version $Id: PopulateMyGroupsAction.java,v 1.3 2006-02-24 13:40:58 isgwb Exp $
 */
public class PopulateMyGroupsAction extends GrouperCapableAction {

	//------------------------------------------------------------ Local
	// Forwards
	static final private String FORWARD_MyGroups = "MyGroups";

	//------------------------------------------------------------ Action
	// Methods

	public ActionForward grouperExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, GrouperSession grouperSession)
			throws Exception {
		
		session.setAttribute("functionalArea", "MyGroups");
		session.setAttribute("title", "groups.my");
		session.removeAttribute("subtitle");
		
		setBrowseMode("",session);
		session.removeAttribute("findForNode");
		session.removeAttribute("findForPriv");
		session.removeAttribute("findForListField");
		saveAsCallerPage(request,null,"");

		return mapping.findForward(FORWARD_MyGroups);

	}

}
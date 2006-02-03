/*--
  $Id: StartAction.java,v 1.15 2006-02-03 00:01:52 acohen Exp $
  $Date: 2006-02-03 00:01:52 $
  
  Copyright 2004 Internet2 and Stanford University.  All Rights Reserved.
  Licensed under the Signet License, Version 1,
  see doc/license.txt in this distribution.
*/
package edu.internet2.middleware.signet.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import edu.internet2.middleware.signet.PrivilegedSubject;
import edu.internet2.middleware.signet.Signet;
import edu.internet2.middleware.signet.Subsystem;

/**
 * <p>
 * Confirm required resources are available. If a resource is missing,
 * forward to "failure". Otherwise, forward to "success", where
 * success is usually the "welcome" page.
 * </p>
 * <p>
 * Since "required resources" includes the application MessageResources
 * the failure page must not use the standard error or message tags.
 * Instead, it display the Strings stored in an ArrayList stored under
 * the request attribute "ERROR".
 * </p>
 *
 */
public final class StartAction extends BaseAction
{
  
  // ---------------------------------------------------- Public Methods
  // See superclass for Javadoc
  public ActionForward execute
  	(ActionMapping				mapping,
     ActionForm 					form,
     HttpServletRequest 	request,
     HttpServletResponse response)
  throws Exception
  {
    // Setup message array in case there are errors
    ArrayList messages = new ArrayList();

    // Confirm message resources loaded
    MessageResources resources = getResources(request);
    if (resources==null)
    {
      messages.add(Constants.ERROR_MESSAGES_NOT_LOADED);
    }

    // If there were errors, forward to our failure page
    if (messages.size()>0)
    {
      request.setAttribute(Constants.ERROR_KEY,messages);
      return findFailure(mapping);
    }
    
    HttpSession session = request.getSession();
    
    Signet signet = (Signet)(session.getAttribute("signet"));
    if (signet == null)
    {
      signet = new Signet();
      signet.setLogger(logger);
      session.setAttribute("signet", signet);
    }
    

    PrivilegedSubject loggedInUser
    	= (PrivilegedSubject)(session.getAttribute(Constants.LOGGEDINUSER_ATTRNAME));
    if (loggedInUser == null)
    {
      // This getRemoteUser() check exists only for Signet demo installations.
      // In the case of a normal production system, user authentication would
      // occur before this "Start" action is accessed.
      if (request.getRemoteUser() == null)
      {
        return findDemoLogin(mapping);
      }
      
      // Find the PrivilegedSubject associated with the logged-in
      // user, and stash it in the Session.
      Set userMatches
      	= signet.getPrivilegedSubjectsByDisplayId(
      			Signet.DEFAULT_SUBJECT_TYPE_ID, request.getRemoteUser());
      
      if (userMatches.size() != 1)
      {
      	messages.add
        ("Found " 
            + userMatches.size()
            + " matches for logged-in user with display-ID '"
            + request.getRemoteUser()
            + "'. We don't know what to do with any number other than one.");
        messages.add("All matches:");
        Iterator userMatchesIterator = userMatches.iterator();
        while (userMatchesIterator.hasNext())
        {
          messages.add
          (((PrivilegedSubject)(userMatchesIterator.next())).toString());
        }
        request.setAttribute(Constants.ERROR_KEY, messages);
        
        return findFailure(mapping);
      }
      
      loggedInUser = null;
      Iterator pSubjectsIterator = userMatches.iterator();
      while (pSubjectsIterator.hasNext())
      {
        loggedInUser = (PrivilegedSubject)(pSubjectsIterator.next());
      }
      
      session.setAttribute(Constants.LOGGEDINUSER_ATTRNAME, loggedInUser);
    }
    
    PrivilegedSubject currentPrivilegedSubject
      = Common.getAndSetPrivilegedSubject
          (signet,
           request,
           Constants.CURRENTPSUBJECT_HTTPPARAMNAME, // paramName
           Constants.CURRENTPSUBJECT_ATTRNAME,      // attrName
           loggedInUser.getEffectiveEditor());      // defaultValue
    
    session.setAttribute
      (Constants.PRIVDISPLAYTYPE_ATTRNAME, PrivDisplayType.CURRENT_GRANTED);
    
    Subsystem currentSubsystem
      = Common.getAndSetSubsystem
          (signet,
           request,
           Constants.SUBSYSTEM_HTTPPARAMNAME, // paramName
           Constants.SUBSYSTEM_ATTRNAME,      // attributeName
           Constants.WILDCARD_SUBSYSTEM);     // default value
    
    // Forward to our success page
    return findSuccess(mapping);
  }
}


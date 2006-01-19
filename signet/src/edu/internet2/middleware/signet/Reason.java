/*--
$Id: Reason.java,v 1.6 2006-01-19 20:38:56 acohen Exp $
$Date: 2006-01-19 20:38:56 $

Copyright 2004 Internet2 and Stanford University.  All Rights Reserved.
Licensed under the Signet License, Version 1,
see doc/license.txt in this distribution.
*/
package edu.internet2.middleware.signet;

/**
 * This is a typesafe enumeration that identifies the various reasons a
 * Signet operation may be disallowed.
 *  
 */
public class Reason extends TypeSafeEnumeration
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor is private to prevent instantiation except during
   * class loading.
   * 
   * @param name
   *          the external name of the Reason value.
   * @param description
   *          the human readable description of the Reason value, by
   *          which it may be presented in a log file or a debug console.
   */
  private Reason(String name, String description)
  {
    super(name, description);
  }

  /**
   * The instance that describes an attempt to modify an INACTIVE
   * {@link Assignment} or {@link Proxy}.
   */
  public static final Reason STATUS
    = new Reason
        ("status",
         "It is illegal to grant, modify or revoke an assignment or proxy which"
         + " has a Status value of INACTIVE. Only PENDING or ACTIVE assignments"
         + " and proxies may be edited.");

  /**
   * The instance that describes an attempt to modify one's own
   * {@link Assignment} or {@link Proxy}.
   */
  public static final Reason SELF
    = new Reason
        ("self",
         "It is illegal to grant an assignment or proxy to oneself, or to"
         + " modify or revoke an assignment or proxy which is granted to"
         + " oneself.");

  /**
   * The instance that describes an attempt to modify an {@link Assignment}
   * beyond the reach of one's own {@link Limit}s.
   */
  public static final Reason LIMIT
    = new Reason
        ("limit",
         "This assignment includes a Limit-value which this PrivilegedSubject"
         + " does not have sufficient privileges to work with.");

  /**
   * The instance that describes an attempt to modify an {@link Assignment}
   * which involves a {@link Function} that one has no grantable
   * <code>Assignment</code>s for.
   */
  public static final Reason FUNCTION
    = new Reason
        ("function",
         "The PrivilegedSubject has no grantable privileges in regard to this"
         + " function.");

  /**
   * The instance that describes an attempt to modify an {@link Assignment}
   * which involves a scope that one has no grantable <code>Assignment</code>s
   * over for the specified {@link Function}.
   */
  public static final Reason SCOPE
    = new Reason
        ("scope",
         "The scope of this Assignment lies outside the scope within which"
         + " this PrivilegedSubject has the ability to grant the specified"
         + " Function.");
  
  /**
   * The instance that describes an attempt to extend a {@link Proxy} which
   * cannot be extended to a third party.
   */
  public static final Reason CANNOT_EXTEND
    = new Reason
        ("cannot extend",
         "This Proxy cannot be extended to a third party.");
  
  /**
   * The instance that describes an attempt to exercise a {@link Proxy}
   * which cannot be used.
   */
  public static final Reason CANNOT_USE
    = new Reason
        ("cannot use",
         "This Proxy cannot be used directly. It may only be extended to a"
         + " third party.");
  
  /**
   * The instance that describes an attempt to exercise a {@link Proxy}
   * which does not exist.
   */
  public static final Reason NO_PROXY
    = new Reason
        ("no proxy",
         "This PrivilegedSubject does not hold a Proxy that encompasses this"
         + " operation.");
}

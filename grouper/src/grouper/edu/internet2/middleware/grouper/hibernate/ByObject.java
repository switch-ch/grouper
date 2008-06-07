package edu.internet2.middleware.grouper.hibernate;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import edu.internet2.middleware.grouper.internal.dao.GrouperDAOException;
import edu.internet2.middleware.grouper.util.GrouperUtil;



/**
 * @version $Id: ByObject.java,v 1.1.2.1 2008-06-07 19:28:22 mchyzer Exp $
 * @author harveycg
 */
public class ByObject extends HibernateDelegate {

  /** logger */
  private static final Log LOG = LogFactory.getLog(ByObject.class);

  /**
   * @param theHibernateSession
   */
  ByObject(HibernateSession theHibernateSession) {
    super(theHibernateSession);
  }

  /**
   * <pre>
   * call hibernate method "delete" on a list of objects
   * 
   * HibernateSession.byObjectStatic().delete(collection);
   * 
   * </pre>
   * @param collection is collection of objects to delete in one transaction.  If null or empty just ignore
   * @throws GrouperDAOException
   */
  public void delete(final Collection<?> collection) throws GrouperDAOException {
    if (collection == null) {
      return;
    }
    try {
      Session session  = this.getHibernateSession().getSession();
      
      for (Object object : collection) {
        session.delete(object);
      }
    } catch (GrouperDAOException e) {
      LOG.error("Exception in delete: " + GrouperUtil.classNameCollection(collection) + ", " + this, e);
      throw e;
    } catch (RuntimeException e) {
      LOG.error("Exception in delete: " + GrouperUtil.classNameCollection(collection) + ", " + this, e);
      throw e;
    }
    
  }

  /**
   * <pre>
   * call hibernate method "delete" on a list of objects
   * 
   * HibernateSession.byObjectStatic().delete(Rosetta.getDAO(_f));
   * 
   * </pre>
   * @param object is an object (if collection will still work), if null, will probably throw exception
   * @throws GrouperDAOException
   */
  public void delete(final Object object) throws GrouperDAOException {
    //dont fail if collection in there
    if (object instanceof Collection) {
      delete((Collection)object);
      return;
    }
    try {
      Session session  = this.getHibernateSession().getSession();
      
      session.delete(object);
    } catch (GrouperDAOException e) {
      LOG.error("Exception in delete: " + GrouperUtil.classNameCollection(object) + ", " + this, e);
      throw e;
    } catch (RuntimeException e) {
      LOG.error("Exception in delete: " + GrouperUtil.classNameCollection(object) + ", " + this, e);
      throw e;
    }
    
  }

  /**
   * <pre>
   * call hibernate method "save" on an object
   * 
   * HibernateSession.byObjectStatic().save(dao);
   * 
   * </pre>
   * @param object to save
   * @return the id
   * @throws GrouperDAOException
   */
  public Serializable save(final Object object) throws GrouperDAOException {
    try {
      HibernateSession hibernateSession = this.getHibernateSession();
      Session session  = hibernateSession.getSession();
      
      Serializable id = session.save(object);
      if (object instanceof HibGrouperLifecycle) {
        ((HibGrouperLifecycle)object).onPostSave(hibernateSession);
      }
      return id;
    } catch (GrouperDAOException e) {
      LOG.error("Exception in save: " + GrouperUtil.className(object) + ", " + this, e);
      throw e;
    } catch (RuntimeException e) {
      LOG.error("Exception in save: " + GrouperUtil.className(object) + ", " + this, e);
      throw e;
    }
    
  }

  /**
   * call hibernate "update" method on an object
   * @param object to update
   * @throws GrouperDAOException
   */
  public void update(final Object object) throws GrouperDAOException {
    try {

      HibernateSession hibernateSession = this.getHibernateSession();
      Session session  = hibernateSession.getSession();
      
      session.update(object);
      
      if (object instanceof HibGrouperLifecycle) {
        ((HibGrouperLifecycle)object).onPostUpdate(hibernateSession);
      }
      
    } catch (GrouperDAOException e) {
      LOG.error("Exception in update: " + GrouperUtil.className(object) + ", " + this, e);
      throw e;
    } catch (RuntimeException e) {
      LOG.error("Exception in update: " + GrouperUtil.className(object) + ", " + this, e);
      throw e;
    }
    
  }
}

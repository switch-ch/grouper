/**
 * @author Kate
 * $Id: SubjectSortWrapper.java,v 1.1 2009-10-11 22:04:17 mchyzer Exp $
 */
package edu.internet2.middleware.grouper.ui.util;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import edu.internet2.middleware.subject.Source;
import edu.internet2.middleware.subject.Subject;
import edu.internet2.middleware.subject.SubjectType;


/**
 *
 */
public class SubjectSortWrapper implements Subject, Comparable {

  /** wrapped subject */
  private Subject wrappedSubject;
  
  /**
   * screen label (sort by this)
   */
  private String screenLabel;

  /**
   * screen label (sort by this)
   * @return the screen label
   */
  public String getScreenLabel() {
    
    if (this.screenLabel == null) {
      
      this.screenLabel = GrouperUiUtils.convertSubjectToLabelConfigured(this.wrappedSubject);
      this.screenLabel = StringUtils.defaultString(this.screenLabel);
    }
    
    return this.screenLabel;
  }
  
  /**
   * wrapped subject
   * @param subject
   */
  public SubjectSortWrapper(Subject subject) {
    this.wrappedSubject = subject;
  }
  
  /**
   * return the wrapped subject
   * @return the wrapped subject
   */
  public Subject getWrappedSubject() {
    return this.wrappedSubject;
  }
  
  /**
   * @see edu.internet2.middleware.subject.Subject#getAttributeValue(java.lang.String)
   */
  public String getAttributeValue(String name) {
    return this.wrappedSubject.getAttributeValue(name);
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getAttributeValues(java.lang.String)
   */
  public Set getAttributeValues(String name) {
    return this.wrappedSubject.getAttributeValues(name);
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getAttributes()
   */
  public Map getAttributes() {
    return this.wrappedSubject.getAttributes();
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getDescription()
   */
  public String getDescription() {
    return this.wrappedSubject.getDescription();
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getId()
   */
  public String getId() {
    return this.wrappedSubject.getId();
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getName()
   */
  public String getName() {
    return this.wrappedSubject.getName();
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getSource()
   */
  public Source getSource() {
    return this.wrappedSubject.getSource();
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getType()
   */
  public SubjectType getType() {
    return this.wrappedSubject.getType();
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object o) {
    if (!(o instanceof SubjectSortWrapper)) {
      return -1;
    }
    SubjectSortWrapper subjectSortWrapper = (SubjectSortWrapper)o;
    return this.getScreenLabel().compareToIgnoreCase(subjectSortWrapper.getScreenLabel());
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getAttributeValueOrCommaSeparated(java.lang.String)
   */
  public String getAttributeValueOrCommaSeparated(String attributeName) {
    return this.wrappedSubject.getAttributeValueOrCommaSeparated(attributeName);
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getAttributeValueSingleValued(java.lang.String)
   */
  public String getAttributeValueSingleValued(String attributeName) {
    return this.wrappedSubject.getAttributeValueSingleValued(attributeName);
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getSourceId()
   */
  public String getSourceId() {
    return this.wrappedSubject.getSourceId();
  }

  /**
   * @see edu.internet2.middleware.subject.Subject#getTypeName()
   */
  public String getTypeName() {
    return this.wrappedSubject.getTypeName();
  }

}
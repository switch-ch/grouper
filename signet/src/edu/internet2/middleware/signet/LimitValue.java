/*--
$Id: LimitValue.java,v 1.4 2005-02-25 20:16:20 acohen Exp $
$Date: 2005-02-25 20:16:20 $

Copyright 2004 Internet2 and Stanford University.  All Rights Reserved.
Licensed under the Signet License, Version 1,
see doc/license.txt in this distribution.
*/
package edu.internet2.middleware.signet;

import java.util.HashSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import edu.internet2.middleware.signet.choice.ChoiceNotFoundException;
import edu.internet2.middleware.signet.choice.ChoiceSetNotFoundException;

/**
 * Encapsulates the many-to-many relationship between Limits and their selected
 * values.
 */
public class LimitValue
{
  private Limit		limit;
  private String	value;
  
  /**
   * Hibernate requires the presence of a default constructor.
   */
  public LimitValue()
  {
    super();
  }
  
  /**
   * @param limit
   * @param value
   */
  public LimitValue(Limit limit, String value)
  {
    super();
    this.limit = limit;
    this.value = value;
  }
  
  /**
   * @return Returns the limit.
   */
  public Limit getLimit()
  {
    return this.limit;
  }
  
  /**
   * @param limit The limit to set.
   */
  void setLimit(Limit limit)
  {
    this.limit = limit;
  }
  
  /**
   * @return Returns the value.
   */
  public String getValue()
  {
    return this.value;
  }
  
  /**
   * @param value The value to set.
   */
  void setValue(String value)
  {
    this.value = value;
  }
  
  /**
   * Gets the display-value for this limit-value, if it's available in the
   * database. Otherwise, it returns the internal value.
   */
  public String getDisplayValue()
  {
    String displayValue;
    
    try
    {
      displayValue
      	= this
      			.limit
      				.getChoiceSet()
      					.getChoiceByValue(this.value)
      						.getDisplayValue();
    }
    catch (ChoiceNotFoundException e)
    {
      displayValue = this.value + " (display-value not available)";
    }
    catch (ObjectNotFoundException e)
    {
      displayValue = this.value + " (display-value not available)";
    }
    
    return displayValue;
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof SubjectTypeImpl))
    {
      return false;
    }

    LimitValue rhs = (LimitValue) obj;
    return new EqualsBuilder()
    	.append(this.getLimit(), rhs.getLimit())
    	.append(this.getValue(), rhs.getValue())
    	.isEquals();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode()
  {
    // you pick a hard-coded, randomly chosen, non-zero, odd number
    // ideally different for each class
    return new HashCodeBuilder(17, 37)
    	.append(this.getLimit())
    	.append(this.getValue())
    	.toHashCode();
  }
}

/**
 * Copyright 2014 Internet2
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * @author mchyzer
 * $Id: HooksStemBean.java,v 1.3 2008-07-11 05:11:28 mchyzer Exp $
 */
package edu.internet2.middleware.grouper.hooks.beans;

import java.util.Set;

import edu.internet2.middleware.grouper.Stem;
import edu.internet2.middleware.grouper.annotations.GrouperIgnoreDbVersion;
import edu.internet2.middleware.grouper.util.GrouperUtil;


/**
 * bean to hold objects for stem low level hooks
 */
@GrouperIgnoreDbVersion
public class HooksStemBean extends HooksBean {
  
  //*****  START GENERATED WITH GenerateFieldConstants.java *****//

  /** constant for field name for: stem */
  public static final String FIELD_STEM = "stem";

  /**
   * fields which are included in clone method
   */
  private static final Set<String> CLONE_FIELDS = GrouperUtil.toSet(
      FIELD_STEM);

  //*****  END GENERATED WITH GenerateFieldConstants.java *****//

  /** object being affected */
  private Stem stem = null;
  
  /**
   * 
   */
  public HooksStemBean() {
    super();
  }

  /**
   * @param theStem
   */
  public HooksStemBean(Stem theStem) {
    this.stem = theStem;
  }
  
  /**
   * object being inserted
   * @return the Stem
   */
  public Stem getStem() {
    return this.stem;
  }

  /**
   * deep clone the fields in this object
   */
  @Override
  public HooksStemBean clone() {
    return GrouperUtil.clone(this, CLONE_FIELDS);
  }
}

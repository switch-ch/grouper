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
 * $Id: StemAddAlreadyExistsException.java,v 1.1 2009-03-15 20:20:46 mchyzer Exp $
 */
package edu.internet2.middleware.grouper.exception;


/**
 *
 */
public class StemAddAlreadyExistsException extends StemAddException {

  /**
   * 
   */
  public StemAddAlreadyExistsException() {
    super();
  }

  /**
   * @param msg
   * @param cause
   */
  public StemAddAlreadyExistsException(String msg, Throwable cause) {
    super(msg, cause);
  }

  /**
   * @param msg
   */
  public StemAddAlreadyExistsException(String msg) {
    super(msg);
  }

  /**
   * @param cause
   */
  public StemAddAlreadyExistsException(Throwable cause) {
    super(cause);
  }

}

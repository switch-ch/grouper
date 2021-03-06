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
/**
 * @author mchyzer
 * $Id$
 */
package edu.internet2.middleware.grouperWsSourceAdapter;


/**
 *
 */
public class WsSourceAdapterNotFound extends RuntimeException {

  /**
   * 
   */
  public WsSourceAdapterNotFound() {
  }

  /**
   * @param message
   */
  public WsSourceAdapterNotFound(String message) {
    super(message);

  }

  /**
   * @param cause
   */
  public WsSourceAdapterNotFound(Throwable cause) {
    super(cause);

  }

  /**
   * @param message
   * @param cause
   */
  public WsSourceAdapterNotFound(String message, Throwable cause) {
    super(message, cause);

  }

}

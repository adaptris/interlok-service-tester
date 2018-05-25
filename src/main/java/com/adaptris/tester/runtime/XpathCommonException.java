/*
    Copyright 2018 Adaptris Ltd.

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

package com.adaptris.tester.runtime;

/**
 * Standard Exception in the XpathCommon class.
 *
 * <p>Deliberately separate to allow different classes to rethrow there own exception.</p>
 */
public class XpathCommonException extends Exception {

  private static final long serialVersionUID = 5672347902807738427L;

  /**
   * Creates a new instance with a description of the Exception.
   * @param message description of the Exception
   */
  public XpathCommonException(String message) {
    super(message);
  }

  /**
   * Creates a new instance with a reference to a previous Exception and a description of the Exception.
   * @param message description of the Exception
   * @param e previous Exception
   */
  public XpathCommonException(String message, Exception e) {
    super(message, e);
  }
}

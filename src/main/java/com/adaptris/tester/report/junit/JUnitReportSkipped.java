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

package com.adaptris.tester.report.junit;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Skipped implementation of {@link JUnitReportTestIssue} used in {@link JUnitReportTestCase} for storing results.
 *
 * The intention of class and classes in the hierarchy is to produce JUnit style XML.
 *
 * <p>When tests are executed via {@link com.adaptris.tester.runtime.TestCase#execute(String, com.adaptris.tester.runtime.clients.TestClient, com.adaptris.tester.runtime.services.ServiceToTest)}
 * this class is used to set the test issue if applicable using: {@link JUnitReportTestCase#setTestIssue(JUnitReportTestIssue)}.</p>
 *
 * @junit-config skipped
 */
@XStreamAlias("skipped")
public class JUnitReportSkipped implements JUnitReportTestIssue {

}

/*
  Copyright (C) 2004-2007 University Corporation for Advanced Internet Development, Inc.
  Copyright (C) 2004-2007 The University Of Chicago

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

package edu.internet2.middleware.grouper;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.logging.Log;

import edu.internet2.middleware.grouper.privs.AccessPrivilege;
import edu.internet2.middleware.grouper.registry.RegistryReset;
import edu.internet2.middleware.grouper.util.GrouperUtil;
import edu.internet2.middleware.grouper.xml.XmlExporter;
import edu.internet2.middleware.grouper.xml.XmlImporter;
import edu.internet2.middleware.grouper.xml.XmlReader;

/**
 * @author  blair christensen.
 * @version $Id: TestXml26.java,v 1.6 2008-09-29 03:38:27 mchyzer Exp $
 * @since   1.1.0
 */
public class TestXml26 extends GrouperTest {

  private static final Log LOG = GrouperUtil.getLog(TestXml26.class);

  public TestXml26(String name) {
    super(name);
  }

  protected void setUp () {
    LOG.debug("setUp");
    RegistryReset.reset();
  }

  protected void tearDown () {
    LOG.debug("tearDown");
  }

  public void testUpdateOkNamingPrivsInReplaceMode() {
    LOG.info("testUpdateOkNamingPrivsInReplaceMode");
    try {
      // Populate Registry And Verify
      R       r     = R.populateRegistry(1, 2, 0);
      Group   gA    = r.getGroup("a", "a");
      Group   gB    = r.getGroup("a", "b");
      String  nameA = gA.getName();
      gA.grantPriv( SubjectFinder.findAllSubject(), AccessPrivilege.UPDATE );
      // Make sure no exception is thrown due to gB not existing when updating
      gB.grantPriv( SubjectFinder.findAllSubject(), AccessPrivilege.ADMIN );
      r.rs.stop();

      // Export
      GrouperSession  s         = GrouperSession.start( SubjectFinder.findRootSubject() );
      Writer          w         = new StringWriter();
      XmlExporter     exporter  = new XmlExporter(s, new Properties());
      exporter.export(w);
      String          xml       = w.toString();
      s.stop();

      // Reset 
      RegistryReset.reset();

      // Install Subjects and partial registry
      r = R.populateRegistry(1, 1, 0);
      gA = assertFindGroupByName(r.rs, nameA, "recreate");
      // Now grant an added priv
      gA.grantPriv( SubjectFinder.findAllSubject(), AccessPrivilege.ADMIN );
      r.rs.stop();

      // Import 
      s                   = GrouperSession.start( SubjectFinder.findRootSubject() );
      Properties  custom  = new Properties();
      custom.setProperty("import.data.privileges", "replace");
      XmlImporter importer = new XmlImporter(s, custom);
      importer.update( XmlReader.getDocumentFromString(xml) );
      s.stop();

      // Verify
      s   = GrouperSession.start( SubjectFinder.findRootSubject() );
      gA = assertFindGroupByName(s, nameA);
      // Should have
      assertGroupHasUpdate( gA, SubjectFinder.findAllSubject(), true );
      // Should no longer have
      assertGroupHasAdmin( gA, SubjectFinder.findAllSubject(), false );
      s.stop();
    }
    catch (Exception e) {
      unexpectedException(e);
    }
  } // public void testUpdateOkNamingPrivsInReplaceMode()

} // public class TestXml26

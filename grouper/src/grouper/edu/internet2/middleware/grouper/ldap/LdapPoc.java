/**
 * @author mchyzer
 * $Id$
 */
package edu.internet2.middleware.grouper.ldap;

import java.util.Iterator;
import java.util.List;

import javax.naming.directory.SearchResult;

import edu.internet2.middleware.grouper.Group;
import edu.internet2.middleware.grouper.GroupSave;
import edu.internet2.middleware.grouper.GrouperSession;
import edu.internet2.middleware.grouper.app.loader.ldap.LoaderLdapUtils;
import edu.internet2.middleware.grouper.attr.assign.AttributeAssign;
import edu.vt.middleware.ldap.Ldap;
import edu.vt.middleware.ldap.LdapConfig;
import edu.vt.middleware.ldap.SearchFilter;
import edu.vt.middleware.ldap.pool.BlockingLdapPool;
import edu.vt.middleware.ldap.pool.DefaultLdapFactory;
import edu.vt.middleware.ldap.pool.LdapPoolConfig;


/**
 *
 */
public class LdapPoc {

  /**
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception {
    //manyObjectsOneAttribute();
    
    for (int i=0;i<100;i++) {
      manyObjectsOneAttributeFramework();
    }
    

  }

  /**
   * 
   * @throws Exception
   */
  public static void manyObjectsOneAttribute() throws Exception {
    LdapConfig ldapConfig = new LdapConfig("ldaps://ldap.school.edu", "dc=school,dc=edu");
    
    ldapConfig.setBindDn("uid=user,ou=people,dc=school,dc=edu");
    ldapConfig.setBindCredential("xxxxxx");

    DefaultLdapFactory factory = new DefaultLdapFactory(ldapConfig);
    
    LdapPoolConfig ldapPoolConfig = null;
    
    BlockingLdapPool pool = new BlockingLdapPool(factory);
    
    Ldap ldap = pool.checkOut();
    

    Iterator<SearchResult> results = ldap.search("ou=people",
        new SearchFilter("(personid=12345678)"), new String[]{"personid"});
    
    while (results.hasNext()) {
      
      SearchResult searchResult = results.next();
      searchResult.getAttributes().get("whatever").size();
      System.out.println(searchResult.getAttributes().get("personid"));
    }
    

    
    pool.checkIn(ldap);
    
      //pool.getLdapPoolConfig().se

  }
  
  /**
   * 
   */
  public static void manyObjectsOneAttributeFramework() {
    
    List<String> results = LdapSession.list(String.class, "personLdap", "ou=pennnames", 
        null, "(|(pennname=mchyzer)(pennname=choate))", "pennid");
    
    for (String result : results) {
      System.out.println(result);
    }
    
    results = LdapSession.list(String.class, "personLdap", "ou=groups", 
        null, "(|(cn=test:testGroup)(cn=test:ldaptesting:test1))", "hasMember");
    
    for (String result : results) {
      System.out.println(result);
    }
    
    results = LdapSession.list(String.class, "personLdap", "ou=groups", 
        null, "(|(cn=test:testGroup)(cn=test:ldaptesting:test1))", "dn");

    for (String result : results) {
      System.out.println(result);
    }

  }
  
  public static void assignLoaderLdapAttributes() {
    GrouperSession grouperSession = GrouperSession.startRootSession();
    Group group = new GroupSave(grouperSession).assignName("someStem:myLdapGroup").assignCreateParentStemsIfNotExist(true).save();
    AttributeAssign attributeAssign = group.getAttributeDelegate().assignAttribute(LoaderLdapUtils.grouperLoaderLdapAttributeDefName()).getAttributeAssign();
    attributeAssign.getAttributeValueDelegate().assignValue(LoaderLdapUtils.grouperLoaderLdapTypeName(), "LDAP_SIMPLE");
    attributeAssign.getAttributeValueDelegate().assignValue(LoaderLdapUtils.grouperLoaderLdapFilterName(), "(|(cn=test:testGroup)(cn=test:ldaptesting:test1))");
    attributeAssign.getAttributeValueDelegate().assignValue(LoaderLdapUtils.grouperLoaderLdapQuartzCronName(), "* * * * * ?");
    
    attributeAssign.getAttributeValueDelegate().assignValue(LoaderLdapUtils.grouperLoaderLdapSearchDnName(), "ou=groups");
    attributeAssign.getAttributeValueDelegate().assignValue(LoaderLdapUtils.grouperLoaderLdapServerIdName(), "personLdap");
    attributeAssign.getAttributeValueDelegate().assignValue(LoaderLdapUtils.grouperLoaderLdapSourceIdName(), "pennperson");
    attributeAssign.getAttributeValueDelegate().assignValue(LoaderLdapUtils.grouperLoaderLdapSubjectAttributeName(), "hasMember");
    attributeAssign.getAttributeValueDelegate().assignValue(LoaderLdapUtils.grouperLoaderLdapSubjectIdTypeName(), "subjectIdentifier");


  }
  
}
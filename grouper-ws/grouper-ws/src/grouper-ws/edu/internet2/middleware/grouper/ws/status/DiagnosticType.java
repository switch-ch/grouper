package edu.internet2.middleware.grouper.ws.status;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import edu.internet2.middleware.grouper.Group;
import edu.internet2.middleware.grouper.GroupFinder;
import edu.internet2.middleware.grouper.GroupType;
import edu.internet2.middleware.grouper.GroupTypeFinder;
import edu.internet2.middleware.grouper.GrouperSession;
import edu.internet2.middleware.grouper.app.loader.GrouperLoader;
import edu.internet2.middleware.grouper.app.loader.GrouperLoaderType;
import edu.internet2.middleware.grouper.attr.AttributeDef;
import edu.internet2.middleware.grouper.attr.AttributeDefName;
import edu.internet2.middleware.grouper.attr.finder.AttributeDefNameFinder;
import edu.internet2.middleware.grouper.cache.GrouperCache;
import edu.internet2.middleware.grouper.cfg.GrouperConfig;
import edu.internet2.middleware.grouper.misc.GrouperCheckConfig;
import edu.internet2.middleware.grouper.misc.GrouperDAOFactory;
import edu.internet2.middleware.grouper.ws.GrouperWsConfig;
import edu.internet2.middleware.grouper.ws.rest.GrouperRestInvalidRequest;
import edu.internet2.middleware.grouper.ws.util.GrouperServiceUtils;
import edu.internet2.middleware.subject.Source;
import edu.internet2.middleware.subject.provider.SourceManager;

/**
 * type of diagnostics to run (trivial, deep, etc)
 * 
 * @author mchyzer
 */
public enum DiagnosticType {

  /**
   * just do a trivial memory only test
   */
  TRIVIAL {

    /**
     * @see DiagnosticType#appendDiagnostics(List)
     */
    @Override
    public void appendDiagnostics(List<DiagnosticTask> diagnosticsTasks) {
      diagnosticsTasks.add(new DiagnosticMemoryTest());
    }
  },
  
  /**
   * just do the trivial plus the database check
   */
  DB {

    /**
     * @see DiagnosticType#appendDiagnostics(List)
     */
    @Override
    public void appendDiagnostics(List<DiagnosticTask> diagnosticsTasks) {
      TRIVIAL.appendDiagnostics(diagnosticsTasks);
      diagnosticsTasks.add(new DiagnosticDbTest());
    }
  },
  
  /**
   * do the DB test plus check the sources
   */
  SOURCES {

    /**
     * @see DiagnosticType#appendDiagnostics(List)
     */
    @Override
    public void appendDiagnostics(List<DiagnosticTask> diagnosticsTasks) {
      DB.appendDiagnostics(diagnosticsTasks);

      Collection<Source> sources = SourceManager.getInstance().getSources();
      
      for (Source source : sources) {
        
        diagnosticsTasks.add(new DiagnosticSourceTest(source.getId()));
        
      }
      
    }
  },
  
  /**
   * do the sources test plus the jobs
   */
  ALL {

    /**
     * @see DiagnosticType#appendDiagnostics(List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void appendDiagnostics(List<DiagnosticTask> diagnosticsTasks) {
      SOURCES.appendDiagnostics(diagnosticsTasks);
      
      diagnosticsTasks.add(new DiagnosticLoaderJobTest("CHANGE_LOG_changeLogTempToChangeLog", GrouperLoaderType.CHANGE_LOG));

      diagnosticsTasks.add(new DiagnosticLoaderJobTest("MAINTENANCE__grouperReport", GrouperLoaderType.MAINTENANCE));
      diagnosticsTasks.add(new DiagnosticLoaderJobTest("MAINTENANCE_cleanLogs", GrouperLoaderType.MAINTENANCE));

      {
        //expand these out
        Map<String, String> consumerMap = GrouperCheckConfig.retrievePropertiesKeys("grouper-loader.properties", 
            GrouperCheckConfig.grouperLoaderConsumerPattern);
        
        for (String consumerKey : consumerMap.keySet()) {

          //get the name
          Matcher matcher = GrouperCheckConfig.grouperLoaderConsumerPattern.matcher(consumerKey);
          matcher.matches();
          String consumerName = matcher.group(1);

          //diagnosticsTasks.add(new DiagnosticLoaderJobTest("CHANGE_LOG_consumer_xmpp", GrouperLoaderType.CHANGE_LOG));
          diagnosticsTasks.add(new DiagnosticLoaderJobTest("CHANGE_LOG_consumer_" + consumerName, GrouperLoaderType.CHANGE_LOG));

        }
      }

      {
        GroupType groupType = GroupTypeFinder.find("grouperLoader", false);
        if (groupType != null) {
          Set<Group> groupSet = sourceCache.get(GrouperLoaderType.SQL_SIMPLE);
          
          if (groupSet == null) {

            groupSet = GroupFinder.findAllByType(GrouperSession.staticGrouperSession(), groupType);

            sourceCache.put(GrouperLoaderType.SQL_SIMPLE, groupSet);
            
          }
          
          for (Group group : groupSet) {
            
            String grouperLoaderType = group.getAttributeValue(GrouperLoader.GROUPER_LOADER_TYPE, false, false);
            
            GrouperLoaderType grouperLoaderTypeEnum = GrouperLoaderType.valueOfIgnoreCase(grouperLoaderType, true);
    
            String jobName = grouperLoaderTypeEnum.name() + "__" + group.getName() + "__" + group.getUuid();
            
            //diagnosticsTasks.add(new DiagnosticLoaderJobTest("SQL_SIMPLE__penn:sas:query:faculty:adjunct_faculty__707d94fa3aaa4ef88051a144b74bac77", GrouperLoaderType.SQL_SIMPLE));
            //diagnosticsTasks.add(new DiagnosticLoaderJobTest("SQL_GROUP_LIST__penn:community:student:loader:studentPrimaryGroups__93750690c3474b41b349bbd196167d3e", GrouperLoaderType.SQL_GROUP_LIST));
            
            diagnosticsTasks.add(new DiagnosticLoaderJobTest(jobName, grouperLoaderTypeEnum));
            
          }
          
        }
        
      }
      
      {
        String attrRootStem = GrouperConfig.getProperty("grouper.attribute.rootStem");
        if (!StringUtils.isBlank(attrRootStem)) {
          AttributeDefName attributeDefName = AttributeDefNameFinder.findByName(
              GrouperCheckConfig.attributeLoaderStemName() + ":attributeLoader", false);
          
          //see if attributeDef
          if (attributeDefName != null) {
            
            //lets get the attributeDefs which have this type
            Set<AttributeDef> attributeDefs = sourceCache.get(GrouperLoaderType.ATTR_SQL_SIMPLE);
            
            if (attributeDefs == null) {
    
              attributeDefs = GrouperDAOFactory.getFactory().getAttributeAssign()
                .findAttributeDefsByAttributeDefNameId(attributeDefName.getId());
    
              sourceCache.put(GrouperLoaderType.ATTR_SQL_SIMPLE, attributeDefs);
              
            }
    
            for (AttributeDef attributeDef : attributeDefs) {
              
               //lets get all attribute values
               String grouperLoaderType = attributeDef.getAttributeValueDelegate().retrieveValueString(GrouperCheckConfig.attributeLoaderStemName() + ":" + GrouperLoader.ATTRIBUTE_LOADER_TYPE);
                
               GrouperLoaderType grouperLoaderTypeEnum = GrouperLoaderType.valueOfIgnoreCase(grouperLoaderType, true);
        
               String jobName = grouperLoaderTypeEnum.name() + "__" + attributeDef.getName() + "__" + attributeDef.getUuid();
    
               //diagnosticsTasks.add(new DiagnosticLoaderJobTest("ATTR_SQL_SIMPLE__penn:community:employee:orgPermissions:orgs__092bd6259d814b5db665f2f0f4ca7dc6", GrouperLoaderType.ATTR_SQL_SIMPLE));
               diagnosticsTasks.add(new DiagnosticLoaderJobTest(jobName, grouperLoaderTypeEnum));
            }
          }
        }
      }
      
      {
        //do min size groups
        Pattern groupNamePattern = Pattern.compile("^ws\\.diagnostic\\.checkGroupSize\\.(.+)\\.groupName$");
        
        Properties properties = GrouperWsConfig.getProperties();
        for (String key : (Set<String>)(Object)properties.keySet()) {
          Matcher groupNameMatcher = groupNamePattern.matcher(key);
          if (groupNameMatcher.matches()) {
            String configName = groupNameMatcher.group(1);
            int minSize = Integer.parseInt(GrouperWsConfig.getPropertyString(
                "ws.diagnostic.checkGroupSize." + configName + ".minSize"));
            String groupName = properties.getProperty(key);
            diagnosticsTasks.add(new DiagnosticMinGroupSize(groupName, minSize));
          }
        }
      }
      
    }
  };
  
  /**
   * cache the results of which groups or attributes are loadable
   */
  @SuppressWarnings("unchecked")
  private static GrouperCache<GrouperLoaderType, Set> sourceCache = new GrouperCache<GrouperLoaderType, Set>("loaderSets", 100, false, 1200, 1200, false);

  /**
   * append the diagnostics for this tasks
   * @param diagnosticsTasks
   */
  public abstract void appendDiagnostics(List<DiagnosticTask> diagnosticsTasks);
  
  /**
   * do a case-insensitive matching
   * 
   * @param string
   * @param exceptionOnNotFound true to throw exception if method not found
   * @return the enum or null or exception if not found
   * @throws GrouperRestInvalidRequest if there is a problem
   */
  public static DiagnosticType valueOfIgnoreCase(String string,
      boolean exceptionOnNotFound) throws GrouperRestInvalidRequest {
    return GrouperServiceUtils.enumValueOfIgnoreCase(DiagnosticType.class, 
        string, exceptionOnNotFound);
  }

}
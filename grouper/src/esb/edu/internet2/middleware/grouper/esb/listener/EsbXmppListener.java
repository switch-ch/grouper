/*
 * @author Rob Hebron
 */

package edu.internet2.middleware.grouper.esb.listener;

import org.apache.commons.logging.Log;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import edu.internet2.middleware.grouper.util.GrouperUtil;

/**
 * 
 * Class to start a persistent XMMP client that will remain connected to XMPP
 * server using an instance of {@link EsbXmmpPacketListener} to receive events 
 * sent to it to process as changes in the Grouper registry. Messages are 
 * filtered on sender name. All configuration in grouper-loader.properties
 *
 */
public class EsbXmppListener implements Job {

  private static final Log LOG = GrouperUtil.getLog(EsbXmppListener.class);

  /**
   * method to start the client
   * @param jobDataMap
   */
  private void startListenerClient(JobDataMap jobDataMap) {
    String server = jobDataMap.getString("server");
    int port = Integer.parseInt(jobDataMap.getString("port"));
    String username = jobDataMap.getString("username");
    String password = jobDataMap.getString("password");
    String sendername = jobDataMap.getString("sendername");

    ConnectionConfiguration config = new ConnectionConfiguration(server, port);

    XMPPConnection connection = new XMPPConnection(config);
    // Connect to the server
    try {
      connection.connect();

      // Log into the server
      connection.login(username, password, "GrouperListener");
    } catch (XMPPException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    LOG.info("XMPP listener connected to " + server + " on port " + port);

    ChatManager chatmanager = connection.getChatManager();
    Chat newChat = chatmanager.createChat(sendername, null);
    if (LOG.isDebugEnabled()) {
      try {
        newChat.sendMessage("Grouper listener online");
      } catch (XMPPException e) {
        LOG.debug("Error Delivering block " + e.getMessage());
      }
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug(".. creating packet listener with sender filter " + sendername);
    }
    PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class),
            new FromContainsFilter(sendername));

    // Next, create a packet listener
    PacketListener listener = new EsbXmppPacketListener();
    // Register the listener.
    connection.addPacketListener(listener, filter);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Packet listener created and added to connection");
    }
  }

  /**
   * Method invoked by Quartz to start the client
   */
  public void execute(JobExecutionContext context)
      throws JobExecutionException {
    // TODO Auto-generated method stub
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    this.startListenerClient(jobDataMap);
  }

}
/*
 * To change this license header, choose License Headers in Project Properties.
 */
package demo.java.nb.asteriskami.callmethods;

import org.asteriskjava.live.AsteriskChannel;
import org.asteriskjava.live.AsteriskQueue;
import org.asteriskjava.live.AsteriskQueueEntry;
import org.asteriskjava.live.internal.AsteriskAgentImpl;
import org.asteriskjava.live.AsteriskServer;
import org.asteriskjava.live.AsteriskServerListener;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.live.ManagerCommunicationException;
import org.asteriskjava.live.MeetMeRoom;
import org.asteriskjava.live.MeetMeUser;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import org.asteriskjava.live.AsteriskAgent;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;

public class TestOriginate {
    private ManagerConnection managerConnection;

    public TestOriginate() throws IOException
    {
        ManagerConnectionFactory factory = new ManagerConnectionFactory(
                "192.168.254.32", "admin", "amipassword");
        this.managerConnection = factory.createManagerConnection();
    }

    public void run() throws IOException, AuthenticationFailedException, TimeoutException
    {
        OriginateAction originateAction;
        ManagerResponse originateResponse;

        originateAction = new OriginateAction();
        originateAction.setChannel("SIP/5012");
        originateAction.setContext("default");
        originateAction.setExten("5011");
        originateAction.setPriority(new Integer(1));
        originateAction.setTimeout(new Integer(30000));

        // connect to Asterisk and log in
        managerConnection.login();

        // send the originate action and wait for a maximum of 30 seconds for Asterisk
        // to send a reply
        originateResponse = managerConnection.sendAction(originateAction, 30000);

        // print out whether the originate succeeded or not
        System.out.println(originateResponse.getResponse());

        // and finally log off and disconnect
        managerConnection.logoff();
    }

    public static void main(String[] args) throws Exception
    {
        TestOriginate helloManager;

        helloManager = new TestOriginate();
        helloManager.run();
    }
}
/*
 * Suorce from https://asterisk-java.org/tutorial/
 */
package demo.java.nb.asteriskami.lestenevents;


import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.StatusAction;
import org.asteriskjava.manager.event.ManagerEvent;

public class TestListener implements ManagerEventListener {
    private ManagerConnection managerConnection;

    public TestListener() throws IOException
    {
        ManagerConnectionFactory factory = new ManagerConnectionFactory(
                 "192.168.254.32", "admin", "amipassword");

        this.managerConnection = factory.createManagerConnection();
    }

    public void run() throws IOException, AuthenticationFailedException,
            TimeoutException, InterruptedException
    {
        // register for events
        managerConnection.addEventListener(this);

        // connect to Asterisk and log in
        managerConnection.login();

        // request channel state
        managerConnection.sendAction(new StatusAction());

        // wait 10 seconds for events to come in
        Thread.sleep(10000);

        // and finally log off and disconnect
        managerConnection.logoff();
    }

    public void onManagerEvent(ManagerEvent event)
    {
        // just print received events
        System.out.println(event);
    }

    public static void main(String[] args) throws Exception
    {
        TestListener helloEvents;

        helloEvents = new TestListener();
        helloEvents.run();
    }
}
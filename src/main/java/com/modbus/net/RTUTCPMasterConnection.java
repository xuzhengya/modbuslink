/**
 * 
 */
package com.modbus.net;



import com.modbus.common.Modbus;
import com.modbus.io.ModbusRTUTCPTransport;
import com.modbus.io.ModbusTransport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author bonino
 * 
 */
public class RTUTCPMasterConnection implements MasterConnection
{
    // the log identifier
    public static final String logId = "[RTUTCPMasterConnection]: ";

    // the socket upon which sending/receiveing Modbus RTU data
    private Socket socket;

    // the timeout for the socket
    private int socketTimeout = Modbus.DEFAULT_TIMEOUT;

    // a flag for detecting if the connection is up or not
    private boolean connected;

    // the ip address of the remote slave
    private InetAddress slaveIPAddress;

    // the port to which connect on the remote slave
    private int slaveIPPort;

    // private int retries = Modbus.DEFAULT_RETRIES;

    // the RTU over TCP transport
    private ModbusRTUTCPTransport modbusRTUTCPTransport;

    /**
     * Constructs an {@link RTUTCPMasterConnection} instance with a given
     * destination address and port. It permits to handle Modbus RTU over TCP
     * connections in a way similar to standard Modbus/TCP connections
     * 
     * @param adr
     *            the destination IP addres as an {@link InetAddress} instance.
     * @param port
     *            the port to which connect on the destination address.
     */
    public RTUTCPMasterConnection(InetAddress adr, int port)
    {
        // store the IP address of the destination
        this.slaveIPAddress = adr;

        // store the port of the destination
        this.slaveIPPort = port;
    }

    /**
     * Opens the RTU over TCP connection represented by this object.
     * 
     * @throws Exception
     *             if the connection cannot be open (e.g., due to a network
     *             failure).
     */
    @Override
    public synchronized void connect() throws Exception
    {
        this.connect(Modbus.DEFAULT_TIMEOUT);
    }

    /**
     * Opens the RTU over TCP connection represented by this object.
     * 
     * @throws Exception
     *             if the connection cannot be open (e.g., due to a network
     *             failure).
     */
    @Override
    public synchronized void connect(int timeout) throws Exception
    {
        // if not connected, try to connect
        if (!this.connected)
        {
            // handle debug...(TODO: logging?)
            if (Modbus.debug)
                System.out.println(
                        RTUTCPMasterConnection.logId + "connecting...)");

            // create a socket towards the remote slave
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(this.slaveIPAddress,
                    this.slaveIPPort), timeout);

            // set the socket timeout
            setTimeout(this.socketTimeout);

            // prepare the RTU over TCP transport to handle communications
            prepareTransport();

            // set the connected flag at true
            connected = true;

            // handle debug...(TODO: logging?)
            if (Modbus.debug)
                System.out.println(RTUTCPMasterConnection.logId
                        + "successfully connected)");
        }
    }// connect

    /**
     * Closes the RTU over TCP connection represented by this object.
     */
    @Override
    public void close()
    {
        // if connected... disconnect, otherwise do nothing
        if (this.connected)
        {
            // try closing the transport...
            try
            {
                this.modbusRTUTCPTransport.close();
            }
            catch (IOException e)
            {
                // handle debug...(TODO: logging?)
                if (Modbus.debug)
                    System.out.println(RTUTCPMasterConnection.logId
                            + " error while closing the connection, cause:"
                            + e);
            }

            // if everything is fine, set the connected flag at false
            this.connected = false;
        }
    }// close

    /**
     * Returns the <tt>ModbusTransport</tt> associated with this
     * <tt>TCPMasterConnection</tt>.
     * 
     * @return the connection's <tt>ModbusTransport</tt>.
     */
    public ModbusTransport getModbusTransport()
    {
        return this.modbusRTUTCPTransport;
    }// getModbusTransport

    /**
     * Prepares the associated {@link ModbusTransport} of this
     * {@link RTUTCPMasterConnection} for use.
     * 
     * @throws IOException
     *             if an I/O related error occurs.
     */
    private void prepareTransport() throws IOException
    {
        // if the modbus transport is not available, create it
        if (this.modbusRTUTCPTransport == null)
        {
            // create the transport
            this.modbusRTUTCPTransport = new ModbusRTUTCPTransport(socket);
        }
        else
        {
            // just update the transport socket
            this.modbusRTUTCPTransport.setSocket(socket);
        }
    }// prepareIO

    /**
     * Returns the timeout for this {@link RTUTCPMasterConnection}.
     * 
     * @return the timeout as an {@link int} value.
     */
    public int getTimeout()
    {
        return this.socketTimeout;
    }// getReceiveTimeout

    /**
     * Sets the timeout for this {@link RTUTCPMasterConnection}.
     * 
     * @param timeout
     *            the timeout as an <tt>int</tt>.
     */
    public void setTimeout(int timeout)
    {
        // store the current socket timeout
        this.socketTimeout = timeout;

        // set the timeout on the socket, if available
        if (this.socket != null)
        {
            try
            {
                this.socket.setSoTimeout(socketTimeout);
            }
            catch (IOException ex)
            {
                // TODO: handle?
            }
        }
    }// setReceiveTimeout

    /**
     * Returns the destination port of this {@link RTUTCPMasterConnection}.
     * 
     * @return the port number as an <tt>int</tt>.
     */
    public int getPort()
    {
        return this.slaveIPPort;
    }// getPort

    /**
     * Sets the destination port of this {@link RTUTCPMasterConnection}.
     * 
     * @param port
     *            the port number as <tt>int</tt>.
     */
    public void setPort(int port)
    {
        this.slaveIPPort = port;
    }// setPort

    /**
     * Returns the destination {@link InetAddress} of this
     * {@link RTUTCPMasterConnection}</tt>.
     * 
     * @return the destination address as <tt>InetAddress</tt>.
     */
    public InetAddress getAddress()
    {
        return this.slaveIPAddress;
    }// getAddress

    /**
     * Sets the destination {@link InetAddress} of this
     * {@link RTUTCPMasterConnection}.
     * 
     * @param adr
     *            the destination address as {@link InetAddress}.
     */
    public void setAddress(InetAddress adr)
    {
        this.slaveIPAddress = adr;
    }// setAddress

    /**
     * Tests if this {@link RTUTCPMasterConnection} is active or not.
     * 
     * @return <tt>true</tt> if connected, <tt>false</tt> otherwise.
     */
    @Override
    public boolean isConnected()
    {
        return connected;
    }// isConnected

}

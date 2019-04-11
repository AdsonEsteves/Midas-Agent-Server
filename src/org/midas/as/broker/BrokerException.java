package org.midas.as.broker;

import org.midas.as.AgentServerException;

/**
 * Esta exce��o � disparada caso hajam problemas de comunica��o com
 * o servidor. As causas podem ser problemas na rede ou requisi��es
 * mal constru�das.
 */
public class BrokerException extends AgentServerException 
{
	public BrokerException(){}
	public BrokerException(Throwable arg0){super(arg0);}
	public BrokerException(String message){super(message);}
	public BrokerException(String arg0, Throwable arg1){super(arg0, arg1);}
}


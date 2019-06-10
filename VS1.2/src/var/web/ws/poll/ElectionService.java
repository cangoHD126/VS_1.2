package var.web.ws.poll;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/election")
public class ElectionService {

	private Session session;
	
	
	public void notify(BallotBox ballotBox) throws IOException, EncodeException{
		sendHandler();
	}
	
	private void sendHandler() {
		if (session.isOpen()) {
	    	
			//deswegen Nullpointer
			DataEncoder dataEncoder = new DataEncoder();
			BallotBox ballotBox = BallotBox.getInstance();
	    	
			try {
				session.getBasicRemote().sendText(dataEncoder.encode(ballotBox));
			}catch(EncodeException|IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("gschlossen");
		}
	}

	@OnOpen
	public void init(Session s) throws IOException{
		BallotBox ballotBox = BallotBox.getInstance();
        ballotBox.addObserver(this);
		this.session = session;
    	
		sendHandler();
	}
	
	
	@OnClose()
	public void destroy(Session s) throws IOException{
		//BallotBox list = BallotBox.getInstance();
		//l�sche diese ElectionService INstanz aus BallotBox
		BallotBox.getInstance().removeObserver(this);
	}
	/*
	 * @onClose() wird ausgef�hrt sobald Channel beendet wird
	 * @onError() wird ausgef�hrt wenn Exception ausgef�hrt wird
	 * 
	 * */

//	=================== Getter & Setter ================================
	
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}


}

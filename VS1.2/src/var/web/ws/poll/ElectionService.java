package var.web.ws.poll;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/election", encoders = DataEncoder.class)
public class ElectionService {

	private Session session;
	
	
	public void notify(BallotBox zwischenstand) throws IOException, EncodeException{
		
		if(session.isOpen()) {
				session.getBasicRemote().sendObject(zwischenstand);
		}
	}

	@OnOpen
	public void init(Session session) throws IOException{
		this.session = session;
		BallotBox ballotBox = BallotBox.getInstance();
        ballotBox.addObserver(this);
    	
		try {
			notify(ballotBox);
		} catch (EncodeException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@OnClose()
	public void destroy(Session s) throws IOException{
		//lösche diese ElectionService Instanz aus BallotBox
		BallotBox.getInstance().removeObserver(this);
	}
	/*
	 * @onClose() wird ausgeführt sobald Channel beendet wird
	 * @onError() wird ausgeführt wenn Exception ausgeführt wird
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

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
	
	
	public void notify() throws IOException, EncodeException{
		
		if(session.isOpen()) {
			BallotBox zwischenstand=BallotBox.getInstance();
			//DataEncoder response = new DataEncoder();		
			//encodet zwischenStand
			//String responseAsString =  new DataEncoder().encode(zwischenStand);
			
			try {
				RemoteEndpoint.Basic client = session.getBasicRemote();
				DataEncoder data= new DataEncoder();
				data.encode(zwischenstand);
				//versendet die BallotBox-Nachricht an Client und decodiert davor
				client.sendObject(data);
			}catch(EncodeException|IOException e) {
				e.printStackTrace();
			}	
		}
	}

	@OnOpen
	public void init(Session s, @PathParam("user") String nickName) throws IOException{
		
		//hole die Instanz aus BallotBox
		//BallotBox response = BallotBox.getInstance();
		
		//sende die BallotBox-Instanz asynchron 
		s.getAsyncRemote().sendObject(BallotBox.getInstance());
		
		//Session in Instanz = instance, speichern
		
		
		System.out.println("TEST");
		
		//wenn Nachricht über SockeSession kommt dann wird diese Nachricht versendet
		s.getBasicRemote().sendText("geschaft");
		//füge BallotBox den ElectionSevice als Observer hinzu
		BallotBox.getInstance().addObserver(this);
	}
	
	
	@OnClose()
	public void destroy(Session s) throws IOException{
		//BallotBox list = BallotBox.getInstance();
		//lösche diese ElectionService INstanz aus BallotBox
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

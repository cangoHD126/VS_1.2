package var.web.ws.poll;


import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

//brauchen kein JSON Lib da wir den String selber in JSON-Format konstruieren
//import org.json.JSONException;
//import org.json.JSONObject;



public class DataEncoder implements Encoder.Text<BallotBox> {

	
	@Override
	public void destroy() {
		
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	@Override
	public String encode(BallotBox arg0) throws EncodeException {
		// eigenen JSON String bauen
		String jsonString = "{\"votes\": " + arg0.countVotes() + "}";
		return jsonString;

	}

	


}

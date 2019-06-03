package var.web.ws.poll;

import java.util.HashMap;

import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONException;
import org.json.JSONObject;



public class DataEncoder implements Encoder.Text<BallotBox> {

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public String encode(BallotBox arg0) throws EncodeException {
		String jsonString = "";
		try {
			jsonString = new JSONObject().append("CountOfVotes", arg0.countVotes()).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
		
	}

	


}

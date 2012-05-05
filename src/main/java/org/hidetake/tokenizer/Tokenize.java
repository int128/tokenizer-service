package org.hidetake.tokenizer;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

@Path("/tokenize")
public class Tokenize {

	private Tokenizer tokenizer = Tokenizer.builder().build();

	@POST
	@Produces("application/json")
	public Response post(@FormParam("text") String text) {
		List<Token> tokens = this.tokenizer.tokenize(text);
		return Response.ok().entity(tokens).build();
	}

}

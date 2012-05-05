package org.hidetake.tokenizer;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.form.Form;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class TokenizeIT {
	private static String endpointUrl;

	@BeforeClass
	public static void beforeClass() {
		endpointUrl = System.getProperty("service.url");
	}

	@Test
	public void testPost() throws Exception {
		WebClient client = WebClient.create(endpointUrl + "/api/tokenize");
		String input = "愛";
		Response r = client.accept("application/json").form(new Form().set("text", input));
		assertThat(r.getStatus(), is(Response.Status.OK.getStatusCode()));

		JsonFactory factory = new MappingJsonFactory();
		JsonParser parser = factory.createJsonParser((InputStream) r.getEntity());
		JsonNode node = parser.readValueAsTree();
		assertThat(node.isArray(), is(true));
		assertThat(node.size(), is(1));

		JsonNode token = node.get(0);
		assertThat(token.findValue("surfaceForm").asText(), is("愛"));
		assertThat(token.findValue("reading").asText(), is("アイ"));
		assertThat(token.findValue("position").asInt(), is(0));
	}
}

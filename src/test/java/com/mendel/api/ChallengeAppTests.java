package com.mendel.api;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ChallengeAppTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void smokeTest() throws Exception {

		RequestBuilder requestAdd1 = createRequestAddTransaction("999","160","car", null);
		RequestBuilder requestAdd2 = createRequestAddTransaction("1","100","car 2", "999");
		RequestBuilder requestAdd3 = createRequestAddTransaction("2","40","car 2", "1");

		//PUT
		mvc.perform(requestAdd1)
				.andExpect(status().isOk())
				.andReturn();

		mvc.perform(requestAdd2)
				.andExpect(status().isOk())
				.andReturn();

		mvc.perform(requestAdd3)
				.andExpect(status().isOk())
				.andReturn();

		//GET
		RequestBuilder requestType = MockMvcRequestBuilders
				.get("/transactions/types/car")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestType)
				.andExpect(status().isOk())
				.andExpect(content().json("[{\"id\": 999,\"amount\": 160.0,\"type\": \"car\",\"parent\": null}]"))
				.andReturn();

		RequestBuilder requestSum = MockMvcRequestBuilders
				.get("/transactions/sum/999")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestSum)
				.andExpect(status().isOk())
				.andExpect(content().json("300.0"))
				.andReturn();
	}

	private MockHttpServletRequestBuilder createRequestAddTransaction(String id, String amount, String type, String parentId) {

		String json = "{ \"amount\":\""+amount+"\",\"type\":\""+type+"\"}";
		if(parentId!=null)
			json = "{ \"amount\":\""+amount+"\",\"type\":\""+type+"\",\"parent_id\": \""+parentId+"\"}";

		return MockMvcRequestBuilders
				.put("/transactions/"+id)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
	}

}

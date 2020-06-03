package com.example.networkofgiving;

import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.CharityCreationDTO;
import com.example.networkofgiving.models.CharityResponseDTO;
import com.example.networkofgiving.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NetworkOfGivingApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JwtUtil jwtUtil;

	private User user = new User(
			1L,
			"username",
			null,
			null,
			null,
			null,
			null
	);

	@Test
	void contextLoads() {

	}

	@Test
	void getCharities_WithoutAuthentication_ReturnsOK() throws Exception {
		MvcResult mvcResult = mvc.perform(get("/charities"))
				.andExpect(status().isOk()).andReturn();
		String stringContent = mvcResult.getResponse().getContentAsString();
		List content = objectMapper.readValue(stringContent, List.class);
		List<CharityResponseDTO> result = (List<CharityResponseDTO>) content;
		assertTrue(result.size() >= 0);
	}

	@Test
	void createCharity_WithoutAuthentication_ReturnsForbidden() throws Exception {
		mvc.perform(post("/charities"))
				.andExpect(status().isForbidden());
	}

	@Test
	void createCharity_WithAuthentication_CreatesCharity() throws Exception {
		String jwt = this.jwtUtil.createTokenAuthenticationResponse(this.user).getAccessToken();
		CharityCreationDTO dto = new CharityCreationDTO();
		dto.setTitle("dto title");
		dto.setDescription("dto description");
		dto.setVolunteersRequired(5);
		dto.setAmountRequired(new BigDecimal(10.0));
		mvc.perform(post("/charities")
				.content(objectMapper.writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + jwt))
				.andExpect(status().isCreated());
		MvcResult mvcResult = mvc.perform(get("/charities").param("titleFilter", dto.getTitle()))
				.andExpect(status().isOk()).andReturn();
		String stringContent = mvcResult.getResponse().getContentAsString();
		List content = objectMapper.readValue(stringContent, List.class);
		List<CharityResponseDTO> result = (List<CharityResponseDTO>) content;
		assertTrue(result.size() > 0);
		CharityResponseDTO charityResponseDTO = objectMapper.convertValue(result.get(0), CharityResponseDTO.class);
		assertEquals(dto.getTitle(), charityResponseDTO.getTitle());
		assertEquals(dto.getDescription(), charityResponseDTO.getDescription());
		assertTrue(dto.getAmountRequired().compareTo(charityResponseDTO.getAmountRequired()) == 0);
		assertEquals(dto.getVolunteersRequired(), charityResponseDTO.getVolunteersRequired());
	}

}

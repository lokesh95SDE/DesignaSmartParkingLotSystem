package org.ParkingLotSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ParkingLotSystem.dto.EntryRequestDto;
import org.ParkingLotSystem.enums.VehicleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ParkingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCheckInAndCheckOutVehicle() throws Exception {
        String entryResponse = mockMvc.perform(post("/api/v1/parking/entry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new EntryRequestDto("ka01ab1234", VehicleType.CAR))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").isNumber())
                .andExpect(jsonPath("$.parkingSpotNumber").value("F1-M1"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long ticketId = objectMapper.readTree(entryResponse).get("ticketId").asLong();

        mockMvc.perform(post("/api/v1/parking/exit/{ticketId}", ticketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value(ticketId))
                .andExpect(jsonPath("$.amount").value(20.00));
    }

    @Test
    void shouldReturnAvailableSpots() throws Exception {
        mockMvc.perform(get("/api/v1/parking/availability"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].spotNumber").exists());
    }
}

package com.hotel.marryat.controller;

import com.hotel.marryat.config.FlywayConfiguration;
import com.hotel.marryat.entity.Booking;
import com.hotel.marryat.repository.BookingRepository;
import com.hotel.marryat.repository.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:marryat_test",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=pass"})
@AutoConfigureMockMvc
class ReservationControllerIntegrationTest {

    @MockBean
    FlywayConfiguration flywayConfiguration;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @AfterEach
    public void clearDatabase() {
        roomRepository.deleteAll();
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            config = @SqlConfig(transactionMode = ISOLATED),
            scripts = {"classpath:sql/init_data_for_get_reservations_test.sql"})
    void getAllReservations() throws Exception {
        String expectedJson = "[{\"reservationId\":1,\"arrivalDate\":\"2020-01-01\",\"departureDate\":\"2020-01-05\",\"roomNumber\":43}," +
                "{\"reservationId\":3,\"arrivalDate\":\"2020-01-01\",\"departureDate\":\"2020-01-05\",\"roomNumber\":47}]";
        mockMvc.perform(get("/management/reservation/list")
                .queryParam("startDateEquals", "2020-01-01")
                .queryParam("endDateEquals", "2020-01-05"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            config = @SqlConfig(transactionMode = ISOLATED),
            scripts = {"classpath:sql/init_data_for_add_new_reservation_test.sql"})
    void addNewReservationWithSuccess() throws Exception {
        assertTrue(bookingRepository.findAll().isEmpty());
        mockMvc.perform(post("/management/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"arrivalDate\": \"2019-07-02\", \"departureDate\" :  \"2019-12-07\", \"roomId\" : 30}"))
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(1, bookingRepository.findAll().size());
        Booking addedBooking = bookingRepository.findAll().get(0);
        assertEquals(30, addedBooking.getRoom().getId());
    }
}
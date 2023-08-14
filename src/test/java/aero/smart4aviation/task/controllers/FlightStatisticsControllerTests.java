package aero.smart4aviation.task.controllers;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightStatisticsControllerTests {
    @Autowired
    private MockMvc mvc;

    @Nested
    public class GetWeightByFlightNumberAndDate {
        @Test
        public void whenDateDoesNotExistThenReturn404() throws Exception {
            mvc.perform(get("/statistics/flight?flightNumber=2948&date=2014-04-29T12:14:35-04:00")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().json("""
                            {
                                "message": "Flight with number 2948 at date: 2014-04-29T16:14:35Z doesn't exist."
                            }""", true));
        }

        @Test
        public void whenFlightWithFlightNumberAndDateDoesNotExistThenReturn404() throws Exception {
            mvc.perform(get("/statistics/flight?flightNumber=1111&date=2014-04-22T12:14:35-04:00")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().json("""
                            {
                                "message": "Flight with number 1111 at date: 2014-04-22T16:14:35Z doesn't exist."
                            }""", true));
        }

        @Test
        public void whenFlightNumberOutOfAllowedBoundsThenReturn400() throws Exception {
            mvc.perform(get("/statistics/flight?flightNumber=1&date=2014-04-22T12:14:35-04:00")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json("""
                            {
                                "message": "Flight number have to be in the following boundaries [1000; 9999]."
                            }""", true));
        }

        @Test
        public void whenFlightExistsWithoutFreightsThenReturnEmptyStatistic() throws Exception {
            mvc.perform(get("/statistics/flight?flightNumber=8366&date=2021-03-05T08:34:59-04:00")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                            {
                                "cargoWeight": [
                                    {
                                        "weight": 0.0,
                                        "unit": "kg"
                                    },
                                    {
                                        "weight": 0.0,
                                        "unit": "lb"
                                    }
                                ],
                                "baggageWeight": [
                                    {
                                        "weight": 0.0,
                                        "unit": "kg"
                                    },
                                    {
                                        "weight": 0.0,
                                        "unit": "lb"
                                    }
                                ],
                                "totalWeight": [
                                    {
                                        "weight": 0.0,
                                        "unit": "kg"
                                    },
                                    {
                                        "weight": 0.0,
                                        "unit": "lb"
                                    }
                                ]
                            }""", true));
        }

        @Test
        public void whenFlightExistsWithFreightsReturnNotEmptyStatistic() throws Exception {
            mvc.perform(get("/statistics/flight?flightNumber=2948&date=2014-04-22T12:14:35-04:00")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                            {
                                 "cargoWeight": [
                                     {
                                         "weight": 1004.7515671635022,
                                         "unit": "kg"
                                     },
                                     {
                                         "weight": 2215.0954,
                                         "unit": "lb"
                                     }
                                 ],
                                 "baggageWeight": [
                                     {
                                         "weight": 1816.1649263818708,
                                         "unit": "kg"
                                     },
                                     {
                                         "weight": 4003.95352,
                                         "unit": "lb"
                                     }
                                 ],
                                 "totalWeight": [
                                     {
                                         "weight": 2820.9164935453728,
                                         "unit": "kg"
                                     },
                                     {
                                         "weight": 6219.04892,
                                         "unit": "lb"
                                     }
                                 ]
                            }""", true));
        }
    }

    @Nested
    public class GetFlightsStatisticsByIATACodeAndDate {
        @Test
        public void whenDateDoesNotExistThenReturnEmptyStatistic() throws Exception {
            mvc.perform(get("/statistics/IATACode?code=SEA&date=2021-03-05T08:34:59-04:00")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                            {
                                 "departedFlightsNumber": 0,
                                 "arrivedFlightsNumber": 0,
                                 "totalBaggagePiecesArrivedTo": 0,
                                 "totalBaggagePiecesDepartedFrom": 0
                            }""", true));
        }

        @Test
        public void whenDoesNotExistIATACodeByDateThenReturn404() throws Exception {
            mvc.perform(get("/statistics/IATACode?code=III&date=2014-04-22T12:14:35-04:00")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().json("""
                            {
                                "message": "IATA code: III doesn't supported."
                            }""", true));
        }

        @Test
        public void whenIATACodeExistsAtDateWithoutFreightsThenReturnEmptyStatistic() throws Exception {
            mvc.perform(get("/statistics/IATACode?code=MIT&date=2014-04-22T12:14:35-04:00")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                            {
                                 "departedFlightsNumber": 0,
                                 "arrivedFlightsNumber": 0,
                                 "totalBaggagePiecesArrivedTo": 0,
                                 "totalBaggagePiecesDepartedFrom": 0
                            }""", true));
        }

        @Test
        public void whenIATACodeExistsAtDateWithFreightsThenReturnNotEmptyStatistic() throws Exception {
            mvc.perform(get("/statistics/IATACode?code=YYT&date=2014-04-22T12:14:35-04:00")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                            {
                                 "departedFlightsNumber": 1,
                                 "arrivedFlightsNumber": 0,
                                 "totalBaggagePiecesArrivedTo": 0,
                                 "totalBaggagePiecesDepartedFrom": 3590
                            }""", true));
        }
    }
}

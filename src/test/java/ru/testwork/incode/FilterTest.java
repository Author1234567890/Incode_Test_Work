package ru.testwork.incode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilterTest {

    private final static String URL = "/filter";

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldReturnErrorOnInvalidRequest() throws Exception {
        testBadRequest(
            """
                {
                    "elements":[]
                }
                """,
            """
                {"error":"Field elements must not be empty"}
                """
        );
    }

    @Test
    void shouldReturnErrorOnUnknownFilters() throws Exception {
        testBadRequest(
            """
                {
                    "elements":[
                        {
                            "value":"Element without filters",
                            "filters":[]
                        },
                        {
                            "value":"Element with unknown filter",
                            "filters":[
                                {"filterGroupId":"unknown","filterId":"unknown","parameters":[]}
                            ]
                        }
                    ]
                }
                """,
            """
                {"error":"Filter not found: FilterId(group=unknown, id=unknown)"}
                """
        );
    }

    @Test
    void shouldReturnErrorOnFilterInvalidParams() throws Exception {
        testBadRequest(
            """
                {
                    "elements":[
                        {
                            "value":"Element without filters",
                            "filters":[]
                        },
                        {
                            "value":"Element with filter without mandatory params",
                            "filters":[
                                {"filterGroupId":"regex","filterId":"remove","parameters":[]}
                            ]
                        }
                    ]
                }
                """,
            """
                {"error":"Need more params: expected=1, provided=0"}
                """
        );
    }

    @Test
    void shouldReturnOriginalValueIfFiltersAbsent() throws Exception {
        testSuccess(
            """
                {
                    "elements":[
                        {
                            "value":"Element without filters",
                            "filters":[]
                        },
                        {
                            "value":"Element with null filters",
                            "filters":null
                        }
                    ]
                }
                """,
            """
                {
                    "elements":[
                        {
                            "result":"Element without filters",
                            "value":"Element without filters",
                            "filters":[]
                        },
                        {
                            "result":"Element with null filters",
                            "value":"Element with null filters",
                            "filters":null
                        }
                    ]
                }
                """
        );
    }

    @Test
    void shouldProcessAllFiltersInRequest() throws Exception {
        testSuccess(
            """
                {
                    "elements":[
                        {
                            "value":"Ω/ω is omega (омега)",
                            "filters":[
                                {"filterGroupId":"regex","filterId":"remove","parameters":["[()]"]},
                                {"filterGroupId":"convert","filterId":"cyrillicAndGreek","parameters":[]},
                                {"filterGroupId":"regex","filterId":"replace","parameters":["[a-c]","A"]}
                            ]
                        }
                    ]
                }
                """,
            """
                {
                    "elements":[
                        {
                            "result":"W/w is omegA omiegA",
                            "value":"Ω/ω is omega (омега)",
                            "filters":[
                                {"filterGroupId":"regex","filterId":"remove","parameters":["[()]"]},
                                {"filterGroupId":"convert","filterId":"cyrillicAndGreek","parameters":[]},
                                {"filterGroupId":"regex","filterId":"replace","parameters":["[a-c]","A"]}
                            ]
                        }
                    ]
                }
                """
        );
    }

    private void testSuccess(String request, String response) throws Exception {
        mvc.perform(postJson(request))
            .andExpect(status().isOk())
            .andExpect(content().json(response));
    }

    private void testBadRequest(String request, String response) throws Exception {
        mvc.perform(postJson(request))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(response));
    }

    private RequestBuilder postJson(String body) {
        return post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body);
    }
}

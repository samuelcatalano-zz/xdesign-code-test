package co.uk.xdesigntest;

import co.uk.xdesigntest.controller.MunroController;
import co.uk.xdesigntest.enums.CategoryEnum;
import co.uk.xdesigntest.enums.SortTypeEnum;
import co.uk.xdesigntest.exception.ValidationException;
import co.uk.xdesigntest.service.MunroService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class XDesignTestApplicationTests {

    @Autowired
    private MunroController controller;

    @Autowired
    private MunroService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void contextLoads() {
        assertThat(this.controller, notNullValue());
    }

    @Test
    @Order(2)
    public void testGetAllMunros() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/xdesign/munros")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        var munros = this.service.getMunros(null, null, null, null, null, null);
        assertThat((int) munros.count(), is(509));
    }

    @Test
    @Order(3)
    public void testGetAllMunrosCategoryEqualsMun() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/xdesign/munros?category=MUN")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        var munros = this.service.getMunros(null, null, CategoryEnum.MUN.getName(), null, null, null);
        assertThat((int) munros.count(), is(282));
    }

    @Test
    @Order(4)
    public void testGetAllMunrosCategoryEqualsTop() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/xdesign/munros?category=TOP")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        var munros = this.service.getMunros(null, null, CategoryEnum.TOP.getName(), null, null, null);
        assertThat((int) munros.count(), is(227));
    }

    @Test
    @Order(5)
    public void testGetAllMunrosHeightsBetween950And1095() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/xdesign/munros?minHeight=950&maxHeight=1095")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        var munros = this.service.getMunros(950d, 1095d, null, null, null, null);
        assertThat((int) munros.count(), is(291));
    }

    @Test
    @Order(6)
    public void testGetAllMunrosNegativeHeights() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/xdesign/munros?minHeight=1000&maxHeight=800")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        var exception = assertThrows(ValidationException.class, () -> this.service.getMunros(1000d, 800d, null, null, null, null));
        assertTrue(exception.getMessage().contains("Maximum height could not be less than minimum height"));
    }

    @Test
    @Order(7)
    public void testGetAllMunrosOrderNameByAsc() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/xdesign/munros?orderNameBy=ASC")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        var munrosAsc = this.service.getMunros(950d, 1095d, null, null, SortTypeEnum.ASC.getName(), null);
        var munrosDesc = this.service.getMunros(950d, 1095d, null, null, SortTypeEnum.DESC.getName(), null);

        assertThat(munrosAsc.collect(Collectors.toList()).get(0).getName()).isNotEqualTo(munrosDesc.collect(Collectors.toList()).get(0).getName());
    }

    @Test
    @Order(8)
    public void testGetAllMunrosOrderHeightByDesc() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/xdesign/munros?orderHeightBy=Desc")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        var munrosAsc = this.service.getMunros(950d, 1095d, null, SortTypeEnum.ASC.getName(), null, null);
        var munrosDesc = this.service.getMunros(950d, 1095d, null, SortTypeEnum.DESC.getName(), null, null);

        assertThat(munrosAsc.collect(Collectors.toList()).get(0).getHeightInMetre()).isNotEqualTo(munrosDesc.collect(Collectors.toList()).get(0).getHeightInMetre());
    }

    @Test
    @Order(9)
    public void testGetMunroByRunningNo() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/xdesign/munros/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        var munro = this.service.findByRunningNumber(1);
        assertNotNull(munro);
    }

    @Test
    @Order(10)
    public void testGetMunroByNonExistedRunningNo() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/xdesign/munros/999")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        var response = this.mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        assertThat(response.getResponse().getContentAsString(), containsString("There is no Munro with this runningNo"));
    }
}
/*
 *    Copyright 2019 Mi&Jack
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mijack.zero.web.controller;

import com.mijack.zero.common.exceptions.BizCode;
import com.mijack.zero.common.web.bo.ApiResult;
import com.mijack.zero.common.web.mvc.view.ApiJsonView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Nonnull;

/**
 * @author Mi&amp;Jack
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
public class TestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testHello() throws Exception {
        mvc.perform(get("/api/test/hello")
                .accept(MediaType.ALL_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute(ApiJsonView.API_RESULT, new TypeSafeMatcher<ApiResult<String>>() {
                    @Override
                    public void describeTo(Description description) {

                    }

                    @Override
                    protected boolean matchesSafely(ApiResult<String> item) {
                        return item != null && BizCode.SUCCESS.getCode() == item.getCode()
                                && BizCode.SUCCESS.getMessage().equals(item.getMessage())
                                && "Hello world".equals(item.getData());
                    }
                }))
        ;
    }

    @Test
    public void testError() throws Exception {
        mvc.perform(get("/api/test/error")
                .accept(MediaType.ALL_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute(ApiJsonView.API_RESULT, new TypeSafeMatcher<ApiResult<String>>() {
                    @Override
                    public void describeTo(Description description) {

                    }

                    @Override
                    protected boolean matchesSafely(ApiResult<String> item) {
                        return item != null && BizCode.SYSTEM_ERROR.getCode() == item.getCode()
                                && BizCode.SYSTEM_ERROR.getMessage().equals(item.getMessage());
                    }
                }))
        ;
//        ApiResult<?> apiResult = this.restTemplate.getForObject("http://localhost:" + port + "/api/test/error", ApiResult.class);
//        assertNotNull(apiResult);
//        assertEquals(apiResult.getCode(), BizCode.SYSTEM_ERROR.getCode());
//        assertEquals(apiResult.getMessage(), BizCode.SYSTEM_ERROR.getMessage());
//        assertNull(apiResult.getData());
    }
}
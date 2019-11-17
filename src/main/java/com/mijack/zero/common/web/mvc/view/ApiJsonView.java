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

package com.mijack.zero.common.web.mvc.view;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mijack.zero.common.exceptions.BizCode;
import com.mijack.zero.common.web.bo.ApiResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author Mi&Jack
 */
@Component(ApiJsonView.VIEW_NAME)
public class ApiJsonView extends AbstractView implements InitializingBean {
    public static final String VIEW_NAME = "zero.api-json";
    public static final String API_RESULT = "zero.api-result";
    private static final ApiResult<String> DEFAULT_API_RESULT = ApiResult.fail(BizCode.SYSTEM_ERROR);
    private HttpMessageConverter<Object> messageConverter ;
    public static final MediaType MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON, Collections.singletonMap("charset", "UTF-8"));

    @Override
    public String getContentType() {
        return MediaType.ALL_VALUE;
    }

    @Override
    protected void renderMergedOutputModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws Exception {
        Object o = model.get(API_RESULT);
        ServletServerHttpResponse serverHttpResponse = new ServletServerHttpResponse(response);
        if (!(o instanceof ApiResult)) {
            o = DEFAULT_API_RESULT;
        }
        messageConverter.write(o, MEDIA_TYPE, serverHttpResponse);
    }

    @Override
    public void afterPropertiesSet() {
        ObjectMapper objectMapper = new ObjectMapper();
        messageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
    }
}

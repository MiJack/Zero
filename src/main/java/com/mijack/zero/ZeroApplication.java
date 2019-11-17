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
package com.mijack.zero;

import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Mi&Jack
 */
@SpringBootApplication
public class ZeroApplication {
    public static final Logger logger = LoggerFactory.getLogger(ZeroApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ZeroApplication.class, args);
        UserService userService = context.getBean(UserService.class);
        User user = userService.registerUser("mijack", "mijack@email.com");
        logger.info("user: {}", user);
        userService.listUser().forEach(u -> logger.info("{}", u));
    }

}

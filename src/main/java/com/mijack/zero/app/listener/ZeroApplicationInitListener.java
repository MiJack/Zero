/*
 *     Copyright 2020 Mi&Jack
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.mijack.zero.app.listener;

import javax.annotation.Nonnull;

import com.mijack.zero.app.config.AppConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Mi&amp;Jack
 */
@Component
public class ZeroApplicationInitListener implements ApplicationListener<ContextRefreshedEvent> {
    public static final Logger logger = LoggerFactory.getLogger(ZeroApplicationInitListener.class);

    @Override
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        logger.info("onApplicationEvent info: event = {}", event);
        ApplicationContext applicationContext = event.getApplicationContext();
        AppConfigManager configManager = applicationContext.getBean(AppConfigManager.class);
        try {
            configManager.initManager();
        } catch (Exception e) {
            logger.error("onApplicationEvent error: event = {}", event, e);
        }
    }
}

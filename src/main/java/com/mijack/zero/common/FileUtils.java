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

package com.mijack.zero.common;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;

/**
 * @author Mi&amp;Jack
 */
public class FileUtils {
    public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String md5(File file) {
        try {
            return Files.asByteSource(file).hash(Hashing.md5()).toString();
        } catch (IOException e) {
            logger.error("md5 error: file = {}", file, e);
            return null;
        }

    }

    public static byte[] fileBytes(File file) {
        try {
            return Files.asByteSource(file).read();
        } catch (IOException e) {
            logger.error("fileBytes error: file = {}", file, e);
            return null;
        }
    }
}

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

package com.mijack.zero.app.command;

import com.mijack.zero.framework.web.mvc.Command;
import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class UserLoginCommand implements Command {
    private static final long serialVersionUID = 8736922105638475687L;
    private int loginType;
    private String identifiableValue;
    private String password;

}

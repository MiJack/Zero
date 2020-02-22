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

package com.mijack.zero.framework.context;


import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class UserContext {
    private Long userId;
    private static final ThreadLocal<UserContext> USER_CONTEXT_LOCAL = ThreadLocal.withInitial(UserContext::new);

    public static Long getCurrentUserId() {
        return USER_CONTEXT_LOCAL.get().getUserId();
    }

    public static void clear() {
        USER_CONTEXT_LOCAL.remove();
    }

    public static UserContext getCurrent() {
        return USER_CONTEXT_LOCAL.get();
    }
}

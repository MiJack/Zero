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

package com.mijack.zero.app.meta;

import com.mijack.zero.app.meta.constant.Currency;
import lombok.Data;

/**
 * 用于表示货币单位
 *
 * @author Mi&amp;Jack
 */
@Data
public class Money {
    /**
     * 货币单位
     */
    private Currency currency;
    /**
     * 具体数值
     */
    private Float money;

}

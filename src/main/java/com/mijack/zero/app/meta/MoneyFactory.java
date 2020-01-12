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

import java.util.Collection;

import com.mijack.zero.app.meta.constant.Currency;
import com.mijack.zero.app.exception.BaseBizException;
import com.mijack.zero.framework.ddd.Factory;
import com.mijack.zero.app.utils.EnumUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Mi&amp;Jack
 */
@Factory
public class MoneyFactory {
    public Money create(Float money, Integer currency) {
        Money result = new Money();
        result.setMoney(money);
        result.setCurrency(EnumUtils.idOf(currency, Currency.class));
        return result;
    }

    public Money parse(String money) {
        if (StringUtils.isNotEmpty(money)) {
            Collection<Currency> currencies = EnumUtils.listEnums(Currency.class);
            for (Currency currency : currencies) {
                if (money.startsWith(currency.getShortName())) {
                    String value = money.substring(currency.getShortName().length());
                    Money result = new Money();
                    result.setMoney(Float.valueOf(value));
                    result.setCurrency(currency);
                    return result;
                }
            }
        }
        throw new BaseBizException(500, "货币格式存在问题");
    }
}

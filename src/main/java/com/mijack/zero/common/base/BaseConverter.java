/*
 *     Copyright 2019 Mi&Jack
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

package com.mijack.zero.common.base;

import javax.annotation.Nonnull;

import com.mijack.zero.framework.dao.idata.Data;
import com.mijack.zero.framework.dao.idata.DataHolder;

import com.google.common.base.Converter;

/**
 * @author Mi&amp;Jack
 */
public class BaseConverter<DO extends Data<DO> & DataHolder<DO>, DOMAIN> extends Converter<DO, DOMAIN> {
    @Override
    protected DOMAIN doForward(@Nonnull DO input) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected DO doBackward(@Nonnull DOMAIN input) {
        throw new UnsupportedOperationException();
    }
}

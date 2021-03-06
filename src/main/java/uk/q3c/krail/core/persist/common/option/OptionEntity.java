/*
 *
 *  * Copyright (c) 2016. David Sowerby
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *  * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *  * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  * specific language governing permissions and limitations under the License.
 *
 */

package uk.q3c.krail.core.persist.common.option;

import uk.q3c.krail.core.option.Option;
import uk.q3c.krail.core.persist.cache.option.OptionCacheKey;
import uk.q3c.krail.core.persist.inmemory.option.OptionId;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An entity representing an {@link Option}
 * <p>
 * Created by David Sowerby on 30/06/15.
 */
@Immutable
public class OptionEntity {

    private final OptionId optionId;
    private final String value;

    public OptionEntity(@Nonnull OptionCacheKey optionCacheKey, @Nonnull String value) {
        checkNotNull(optionCacheKey);
        checkNotNull(value);
        optionId = new OptionId(optionCacheKey);
        this.value = value;

    }

    public OptionEntity(@Nonnull OptionId optionId, @Nonnull String value) {
        checkNotNull(optionId);
        checkNotNull(value);
        this.optionId = optionId;
        this.value = value;

    }

    public OptionId getOptionId() {
        return optionId;
    }

    @Nonnull
    public String getContext() {
        return optionId.getContext();
    }

    @Nonnull
    public String getUserHierarchyName() {
        return optionId.getUserHierarchyName();
    }

    public String getRankName() {
        return optionId.getRankName();
    }

    @Nonnull
    public String getOptionKey() {
        return optionId.getOptionKey();
    }

    @Nonnull
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionEntity that = (OptionEntity) o;

        if (!optionId.equals(that.optionId)) return false;
        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = optionId.hashCode();
        return 31 * result + value.hashCode();
    }
}

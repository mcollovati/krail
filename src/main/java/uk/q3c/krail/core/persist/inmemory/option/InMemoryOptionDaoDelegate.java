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

package uk.q3c.krail.core.persist.inmemory.option;

import com.google.inject.Inject;
import uk.q3c.krail.core.option.Option;
import uk.q3c.krail.core.persist.cache.option.DefaultOptionCacheLoader;
import uk.q3c.krail.core.persist.cache.option.OptionCache;
import uk.q3c.krail.core.persist.cache.option.OptionCacheKey;
import uk.q3c.krail.core.persist.common.option.OptionDaoDelegate;
import uk.q3c.krail.core.user.profile.RankOption;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data Access Object for {@link DefaultInMemoryOptionStore}
 * <br>
 * <b>NOTE:</b> All values to and from {@link Option} are natively typed.  All values to and from {@link OptionCache}, {@link DefaultOptionCacheLoader} and
 * {@link OptionDaoDelegate} are wrapped in Optional.
 * <br>
 * Created by David Sowerby on 20/02/15.
 */
public class InMemoryOptionDaoDelegate implements OptionDaoDelegate {

    private InMemoryOptionStore optionStore;

    @Inject
    public InMemoryOptionDaoDelegate(InMemoryOptionStore optionStore) {
        this.optionStore = optionStore;
    }

    /**
     * Write the key value pair
     *
     * @param cacheKey specifies the hierarchy, rank and OptionKey to write to
     * @param value    the value to write
     * @param <V>      the value type
     */
    @Override
    public <V> void write(@Nonnull OptionCacheKey<V> cacheKey, @Nonnull String value) {
        checkRankOption(cacheKey, RankOption.SPECIFIC_RANK);
        checkNotNull(value);
        optionStore.add(new OptionId(cacheKey), value);
    }

    @Nonnull
    @Override
    public <V> Optional<String> deleteValue(@Nonnull OptionCacheKey<V> cacheKey) {
        checkRankOption(cacheKey, RankOption.SPECIFIC_RANK);
        return optionStore.delete(new OptionId(cacheKey));
    }

    @Nonnull
    @Override
    public <V> Optional<String> getValue(@Nonnull OptionCacheKey<V> cacheKey) {
        return optionStore.getValue(new OptionId(cacheKey));
    }




    protected Optional<String> getStringValue(@Nonnull OptionCacheKey<?> cacheKey) {
        return optionStore.getValue(new OptionId(cacheKey));
    }


    @Override
    public String connectionUrl() {
        return "In Memory Store";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long clear() {
        long count = optionStore.size();
        optionStore.clear();
        return count - optionStore.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        return optionStore.size();
    }
}

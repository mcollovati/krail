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

package uk.q3c.krail.core.data;

import com.vaadin.data.util.converter.Converter;
import uk.q3c.krail.core.option.OptionKey;

import javax.annotation.Nonnull;

/**
 * Utility to convert Option values to and from String - usually used in persisting Option values where the persistence provider needs a single data type (for
 * example a single column in an RDBMS)
 * <p>
 * Created by David Sowerby on 27/06/15.
 */
public interface OptionElementConverter {



    /**
     * Converts the supplied {@code value} to String
     *
     * @param value the value to be converted
     * @param <V>   the value type
     * @return String for persistence, null if value is null
     * @throws ConverterException            if no converter is available for the type of {@link OptionKey#getDefaultValue()}
     * @throws Converter.ConversionException if the conversion itself fails
     */
    <V> String convertValueToString(V value);

    /**
     * Returns a value converted from the String.
     *
     * @param elementClass the class of the element to be converted
     * @param valueString  the String representation of the value
     * @throws ConverterException            if no converter is available for the type of {@link OptionKey#getDefaultValue()}
     * @throws Converter.ConversionException if the conversion itself fails
     */
    @Nonnull
    <V> V convertStringToValue(@Nonnull Class<V> elementClass, @Nonnull String valueString);
}

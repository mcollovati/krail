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

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.inject.Inject;
import com.vaadin.data.util.converter.ConverterFactory;
import uk.q3c.krail.core.i18n.I18NKey;
import uk.q3c.krail.core.option.AnnotationOptionList;
import uk.q3c.krail.core.option.OptionList;
import uk.q3c.util.MessageFormat;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Default implementation for {@link OptionElementConverter}.  Uses {@link ConverterFactory} to supply the converters
 * <p>
 * Created by David Sowerby on 27/06/15.
 */
public class DefaultOptionElementConverter implements OptionElementConverter {


    @Inject
    public DefaultOptionElementConverter() {

    }


    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    @Nonnull
    public String convertValueToString(@Nonnull Object value) {
        Class<?> modelType = value.getClass();
        if (modelType == String.class) {
            return ((String) value);
        } else if (modelType == Integer.class) {
            return Ints.stringConverter()
                       .reverse()
                       .convert((Integer) value);
        } else if (modelType == Long.class) {
            return Longs.stringConverter()
                        .reverse()
                        .convert((Long) value);

        } else if (modelType == Boolean.class) {
            return value.toString();
        } else if (modelType == Locale.class) {
            return ((Locale) value).toLanguageTag();
        } else if (modelType == LocalDateTime.class) {
            return ((LocalDateTime) value).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else if (I18NKey.class.isAssignableFrom(modelType)) {
            return new I18NKeyConverter().convertToString((I18NKey) value);
        } else if (modelType.isEnum()) {
            return new EnumConverter().convertToString((Enum) value);
        } else if (modelType == BigDecimal.class) {
            return value.toString();
        } else if (OptionList.class.isAssignableFrom(modelType)) {
            return new OptionListConverter(this).convertToString((OptionList) value);
        } else if (AnnotationOptionList.class.isAssignableFrom(modelType)) {
            return new AnnotationOptionListConverter().convertToString((AnnotationOptionList) value);
        }
        String msg = MessageFormat.format("Data type of {0} is not supported in Option", value.getClass());
        throw new ConverterException(msg);
    }


    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    @Nonnull
    public <V> V convertStringToValue(@Nonnull Class<V> elementClass, @Nonnull String valueString) {
        if (elementClass == String.class) {
            return ((V) valueString);
        } else if (elementClass == Integer.class) {
            return (V) Ints.stringConverter()
                           .convert(valueString);
        } else if (elementClass == Long.class) {
            return (V) Longs.stringConverter()
                            .convert(valueString);
        } else if (elementClass == Boolean.class) {
            return (V) Boolean.valueOf(valueString);
        } else if (elementClass == Locale.class) {
            return (V) Locale.forLanguageTag(valueString);
        } else if (elementClass == LocalDateTime.class) {
            return (V) LocalDateTime.parse(valueString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else if (elementClass == I18NKey.class) {
            return (V) new I18NKeyConverter().convertToModel(valueString);
        } else if (Enum.class.isAssignableFrom(elementClass)) {
            return (V) new EnumConverter().convertToModel(valueString);
        } else if (elementClass == BigDecimal.class) {
            return (V) new BigDecimal(valueString);
        } else if (elementClass == AnnotationOptionList.class) {
            return (V) new AnnotationOptionListConverter().convertToModel(valueString);
        }
        String msg = MessageFormat.format("Data type of {0} is not supported in Option", elementClass);
        throw new ConverterException(msg);
    }


}

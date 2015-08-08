/*
 * Copyright (c) 2015. David Sowerby
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package uk.q3c.krail.core.data;

import com.google.inject.AbstractModule;
import com.vaadin.data.util.converter.ConverterFactory;
import com.vaadin.data.util.converter.DefaultConverterFactory;

/**
 * Provides data related configuration
 *
 * Created by David Sowerby on 18/03/15.
 */
public class DataModule extends AbstractModule {



    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        define();
        bindConverterFactory();
        bindStringPersistenceConverter();


    }

    protected void bindStringPersistenceConverter() {
        bind(OptionStringConverter.class).to(DefaultOptionStringConverter.class);
    }

    /**
     * Override this method to directly define configuration required
     */
    protected void define() {

    }



    /**
     * Provides a factory for converting data types for display by Vaadin.  Override this method to provide your own implementation
     */
    protected void bindConverterFactory() {
        bind(ConverterFactory.class).to(DefaultConverterFactory.class);
    }


}

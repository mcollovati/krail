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

package uk.q3c.krail.core.data

import org.apache.commons.collections15.ListUtils
import spock.lang.Specification
import uk.q3c.krail.core.eventbus.SessionBus
import uk.q3c.krail.core.i18n.VaadinCurrentLocale
import uk.q3c.krail.i18n.clazz.ClassPatternSource
import uk.q3c.krail.util.data.AnnotationListConverter
import uk.q3c.krail.util.data.ConversionException
import uk.q3c.util.collection.AnnotationList

/**
 *
 * Created by David Sowerby on 08/08/15.
 */
class AnnotationOptionListConverterTest extends Specification {

    AnnotationListConverter converter

    def setup() {
        converter = new AnnotationListConverter()
    }

    def "round trip empty list"() {
        given:
        AnnotationList testList = new AnnotationList()

        when:

        String string = converter.convertToString(testList)
        AnnotationList returnedList = converter.convertToModel(string)

        then:

        string.isEmpty()
        returnedList.isEmpty()
    }

    def "round trip list has elements"() {
        given:
        AnnotationList testList = new AnnotationList(ClassPatternSource, SessionBus)

        when:

        String string = converter.convertToString(testList)
        AnnotationList returnedList = converter.convertToModel(string)

        then:

        string.equals("uk.q3c.krail.i18n.clazz.ClassPatternSource~~uk.q3c.krail.core.eventbus.SessionBus")
        ListUtils.isEqualList(testList.getList(), returnedList.getList())
    }

    def "Convert non-class value input throws ConversionException"() {
        when:
        converter.convertToModel("rubbish")

        then:
        thrown(ConversionException)
    }

    def "Convert non-annotation class value throws ConversionException"() {
        when:
        converter.convertToModel(VaadinCurrentLocale.class.getName())

        then:
        thrown(ConversionException)
    }

}

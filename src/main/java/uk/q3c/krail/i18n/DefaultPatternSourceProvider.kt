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

package uk.q3c.krail.i18n

import com.google.common.base.Preconditions.checkNotNull
import com.google.common.collect.ImmutableSet
import com.google.common.collect.Lists
import com.google.inject.Inject
import com.google.inject.Provider
import uk.q3c.krail.core.option.AnnotationOptionList
import uk.q3c.krail.core.option.Option
import uk.q3c.krail.i18n.api.*
import java.util.*

/**
 * Default implementation for [PatternSourceProvider]
 *
 *
 * Created by David Sowerby on 01/08/15.
 */
class DefaultPatternSourceProvider @Inject constructor(

        @param:PatternSources private val sources: MutableMap<Class<out Annotation>, Provider<PatternDao>>,
        @param:PatternTargets private val targets: MutableMap<Class<out Annotation>, Provider<PatternDao>>,
        @param:PatternSourceOrderByBundle private val sourceOrderByBundle: MutableMap<Class<out I18NKey>, LinkedHashSet<Class<out Annotation>>>,
        @PatternSourceOrderDefault sourceOrderDefault: MutableSet<Class<out Annotation>>,
        val config: PatternSourceProviderConfig)

    : PatternSourceProvider, PatternSourceProviderConfig by config {

    override var sourceOrderDefault: AnnotationOptionList = AnnotationOptionList(sourceOrderDefault)

    init {
        config.patternSourceProvider = this
    }

    override fun sourceFor(sourceAnnotation: Class<out Annotation>): Optional<PatternDao> {
        val provider = sources[sourceAnnotation]
        return if (provider == null) Optional.empty<PatternDao>() else Optional.of(provider.get())
    }

    override fun targetFor(targetAnnotation: Class<out Annotation>): Optional<PatternDao> {
        val provider = targets[targetAnnotation]
        return if (provider == null) Optional.empty<PatternDao>() else Optional.of(provider.get())
    }


    /**
     * Returns the order in which sources are processed.  The first non-null of the following is used:
     *
     *  1. the order returned by[getSourceOrderFromOption] (a value from [Option]
     *  1. the order returned by [getSourceOrderDefaultFromOption]  (a value from [Option]
     *  1. [sourceOrderByBundle], which is defined by [I18NModule.sourcesOrderByBundle]
     *  1. [sourceOrderDefault], which is defined by [I18NModule.sourcesDefaultOrder]
     *  1. the keys from [sources] - note that the order for this will be unreliable if PatternDaos have
     * been defined by multiple Guice modules
     *
     *
     *
     * If the source order contains less elements than the number of sources, missing elements are added in the order declared in [sources]<br></br>
     * If the source order contains more elements than the number of sources, any elements not in [sources] are removed and a warning logged
     *

     * @param key
     * *         used to identify the bundle, from [I18NKey.bundleName]
     * *
     * *
     * @return a list containing the sources to be processed, in the order that they should be processed
     */
    override fun orderedSources(key: I18NKey): ImmutableSet<Class<out Annotation>> {
        checkNotNull(key)
        var sourceOrder = getSourceOrderFromOption(key.bundleName())
        if (!sourceOrder.isEmpty) {
            return verifySourceOrder(sourceOrder.list)
        }

        sourceOrder = sourceOrderDefaultFromOption
        if (!sourceOrder.isEmpty) {
            return verifySourceOrder(sourceOrder.list)
        }

        val order = this.sourceOrderByBundle[key.javaClass]
        if (order != null) {
            return verifySourceOrder(order)
        }

        if (!sourceOrderDefault.isEmpty) {
            return verifySourceOrder(sourceOrderDefault.list)
        }

        // just return as defined in sources
        return ImmutableSet.copyOf(sources.keys)

    }

    /**
     * Checks that source order has the correct number of elements as described in javadoc for [orderedSources]

     * @param sourceOrder
     * *         the order as retrieved from configuration
     * *
     * *
     * @return the final source order, adjusted if necessary, as described in javadoc for [orderedSources]
     */
    private fun verifySourceOrder(sourceOrder: Collection<Class<out Annotation>>): ImmutableSet<Class<out Annotation>> {
        // if all and only sources contained, just return
        if (sourceOrder.size == sources.size && sourceOrder.containsAll(sources.keys)) {
            return ImmutableSet.copyOf(sourceOrder)
        }
        val newOrder = LinkedHashSet<Class<out Annotation>>()
        // iterate source order and add to new order only if in sources
        sourceOrder.forEach { a ->
            if (sources.containsKey(a)) {
                newOrder.add(a)
            }
        }

        // if sizes the same we must have all source elements
        if (newOrder.size == sources.size) {
            return ImmutableSet.copyOf(newOrder)
        }

        //we have missing sources, so add them to new order
        sources.keys
                .forEach { a ->
                    if (!newOrder.contains(a)) {
                        newOrder.add(a)
                    }
                }

        return ImmutableSet.copyOf(newOrder)
    }

    private fun getSourceOrderFromOption(bundleName: String): AnnotationOptionList {
        checkNotNull(bundleName)
        return config.sourceOrder
    }

    private val sourceOrderDefaultFromOption: AnnotationOptionList
        get() = config.sourceOrderDefault

    override fun selectedTargets(): AnnotationOptionList {
        val optionTargets = config.selectedTargets
        //use a copy to iterate over, otherwise removing from iterated set
        if (!optionTargets.isEmpty) {
            val copyTargets = ArrayList(optionTargets.list)
            optionTargets.list
                    .forEach { t ->
                        if (!targets.containsKey(t)) {
                            copyTargets.remove(t)
                        }
                    }
            return AnnotationOptionList(copyTargets)
        }
        return AnnotationOptionList(Lists.newArrayList(targets.keys))
    }


}

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

package uk.q3c.krail.i18n;

import com.google.common.collect.ImmutableSet;
import uk.q3c.krail.core.user.opt.Option;
import uk.q3c.krail.core.user.opt.OptionContext;
import uk.q3c.krail.core.user.opt.OptionKey;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Optional;

/**
 * Implementations provide a common access point to the various elements of I18N configuration, such as the defines sources, targets and source order.
 * <p>
 * Sources are accessed in a prescribed order by {@link PatternSource}.  The order in which they are accessed is
 * determined by a combination of {@link Option}, {#sourceOrderByBundle} and {#sourceOrderDefault}.
 * <p>
 * <p>
 * Returns the order in which sources are processed.  The first non-null of the following is used:
 * <ol>
 * <li>the order returned by{@link #optionKeySourceOrder)} from {@link Option}</li>
 * <li>the order returned by {@link #optionKeySourceOrderDefault}  from {@link Option}</li>
 * <li> {@link I18NModule#sourcesOrderByBundle}</li>
 * <li> {@link I18NModule#sourcesDefaultOrder} </li>
 * <li> the keys from {@link I18NModule#sources} - note that the order for this will be unreliable if sources have been defined by multiple Guice modules</li>
 * <p>
 * <p>If the source order contains less elements than the number of sources, missing elements are added in the order declared in {@link I18NModule#sources}<br>
 * If the source order contains more elements than the number of sources, any elements not in {@link I18NModule#sources} are removed and a warning logged
 * </ol>
 * <p>
 * <p>
 * <p>
 * Created by David Sowerby on 01/08/15.
 */
public interface PatternSourceProvider extends OptionContext {

    OptionKey<LinkedHashSet<Class<? extends Annotation>>> optionKeySourceOrder = new OptionKey<>(new LinkedHashSet<>(), PatternSourceProvider.class, LabelKey
            .Source_Order, DescriptionKey.Source_Order);

    OptionKey<LinkedHashSet<Class<? extends Annotation>>> optionKeySourceOrderDefault = new OptionKey<>(new LinkedHashSet<>(), PatternSourceProvider.class,
            LabelKey.Source_Order_Default, DescriptionKey.Source_Order_Default);

    OptionKey<LinkedHashSet<Class<? extends Annotation>>> optionKeySelectedTargets = new OptionKey<>(new LinkedHashSet<>(), PatternSourceProvider.class,
            LabelKey.Selected_Pattern_Targets, DescriptionKey.Selected_Pattern_Targets);


    Optional<PatternDao> sourceFor(Class<? extends Annotation> sourceAnnotation);

    Optional<PatternDao> targetFor(Class<? extends Annotation> targetAnnotation);

    ImmutableSet<Class<? extends Annotation>> orderedSources(I18NKey i18NKey);


    LinkedHashSet<Class<? extends Annotation>> selectedTargets();
}
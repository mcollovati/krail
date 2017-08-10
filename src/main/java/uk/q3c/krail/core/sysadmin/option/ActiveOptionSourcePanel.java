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

package uk.q3c.krail.core.sysadmin.option;

import com.google.inject.Inject;
import uk.q3c.krail.core.option.OptionContainerSource;
import uk.q3c.krail.core.option.OptionPopup;
import uk.q3c.krail.i18n.api.I18N;
import uk.q3c.krail.i18n.api.Translate;
import uk.q3c.krail.option.api.Option;
import uk.q3c.krail.option.api.OptionSource;

import java.lang.annotation.Annotation;

/**
 * Displays information for the active option source
 * <p>
 * Created by David Sowerby on 07/07/15.
 */
@I18N
public class ActiveOptionSourcePanel extends SourcePanel {


    private final OptionSource optionSource;

    @Inject
    protected ActiveOptionSourcePanel(Translate translate, OptionSource optionSource, OptionContainerSource optionContainerSource, Option option, OptionPopup optionPopup) {
        super(translate, optionContainerSource, option, optionPopup);

        this.optionSource = optionSource;
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return optionSource.getActiveSource();
    }

    protected void doSetPersistenceInfo() {
        persistenceInfo = optionSource.getActivePersistenceInfo();
    }
}

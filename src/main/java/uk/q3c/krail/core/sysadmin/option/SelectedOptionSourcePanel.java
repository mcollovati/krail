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
import uk.q3c.krail.persist.PersistenceInfo;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.*;

/**
 * Displays the {@link PersistenceInfo} and stored data for the {@link Option} source selected by {@link #selectedSource}
 * <p>
 * Created by David Sowerby on 07/07/15.
 */
@I18N
public class SelectedOptionSourcePanel extends SourcePanel {

    private final OptionSource optionSource;
    private Class<? extends Annotation> selectedSource;

    @Inject
    protected SelectedOptionSourcePanel(Translate translate, OptionContainerSource optionContainerSource, OptionSource optionSource, Option option, OptionPopup optionPopup) {
        super(translate, optionContainerSource, option, optionPopup);
        this.optionSource = optionSource;
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return getSelectedSource();
    }

    public Class<? extends Annotation> getSelectedSource() {
        if (selectedSource == null) {
            selectedSource = optionSource.getActiveSource();
        }
        return selectedSource;
    }

    public void setSelectedSource(@Nonnull Class<? extends Annotation> selectedSource) {
        checkNotNull(selectedSource);
        this.selectedSource = selectedSource;
        displayInfo();
    }

    @Override
    protected void doSetPersistenceInfo() {
        persistenceInfo = optionSource.getPersistenceInfo(getSelectedSource());
    }
}

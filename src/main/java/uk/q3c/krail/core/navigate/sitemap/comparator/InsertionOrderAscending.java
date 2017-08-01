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
package uk.q3c.krail.core.navigate.sitemap.comparator;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import uk.q3c.krail.core.i18n.LabelKey;
import uk.q3c.krail.core.navigate.sitemap.MasterSitemapNode;
import uk.q3c.krail.core.navigate.sitemap.UserSitemapNode;
import uk.q3c.krail.i18n.api.I18NKey;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator which can be used to sort SitemapNode by insertion order, based on {@link MasterSitemapNode#getId()}
 */
@SuppressFBWarnings("OCP_OVERLY_CONCRETE_PARAMETER")
public class InsertionOrderAscending implements Comparator<UserSitemapNode>, UserSitemapSorter, Serializable {

    @Override
    public int compare(UserSitemapNode o1, UserSitemapNode o2) {
        return o1.getId() - o2.getId();
    }

    @Override
    public I18NKey nameKey() {
        return LabelKey.Insertion_Order_Ascending;
    }
}

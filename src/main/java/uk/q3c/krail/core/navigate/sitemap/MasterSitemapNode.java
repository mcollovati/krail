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
package uk.q3c.krail.core.navigate.sitemap;

import com.google.common.collect.ImmutableList;
import uk.q3c.krail.core.i18n.I18NKey;
import uk.q3c.krail.core.navigate.Navigator;
import uk.q3c.krail.core.shiro.PageAccessControl;
import uk.q3c.krail.core.view.KrailView;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.*;

/**
 * Represents a node in the site map (equivalent to a web site 'page'). At a minimum, it contains a URI segment and an id generated by the {@link
 * MasterSitemap}.
 * <p/>
 * The uri segment this is just one part of the URI, so the node for the page at /private/account/open would contain just 'open'). To obtain the full URI,
 * use {@link MasterSitemap#uri(SitemapNode)}.
 * <p/>
 * {@link #viewClass} is the class of {@link KrailView} to be used in displaying the page, and the {@link #getLabelKey()} is an {@link I18NKey} key used to
 * provide a localised label for the page
 * <p/>
 * The {@link #id} is required because the URI segment alone may not be unique, and the other elements are optional.  For the node to be used in a graph, it
 * needs a unique identifier. The id is provided by {@link MasterSitemap#addChild(SitemapNode, SitemapNode)}  and {@link MasterSitemap#addNode(SitemapNode)}.
 * This field has an additional purpose in providing a record of insertion order, so that nodes can be sorted by insertion order if required - although this
 * is not a particularly reliable method of determining order, so it is recommended to use the positionIndex if you want to determine position by index
 * rather than by alphabetic sorting
 * <p/>
 * The type of user access control applied to the page is determined by {@link #pageAccessControl}. Note that permissions and roles are mutually exclusive,
 * so a page cannot require both roles and permissions. This control is applied by the {@link Navigator} during page changes, thereby disallowing access to
 * an unauthorised page.
 *
 * @author David Sowerby 6 May 2013
 */
@Immutable
public class MasterSitemapNode implements SitemapNode {
    private final int id;
    private final String uriSegment;
    private final I18NKey labelKey;
    private final PageAccessControl pageAccessControl;
    private final int positionIndex;
    /**
     * Contains roles required to access this page, but is not used unless {@link #pageAccessControl} is
     * {@link PageAccessControl#ROLES}
     */
    private final ImmutableList<String> roles;
    private final Class<? extends KrailView> viewClass;

    public MasterSitemapNode(int id, String uriSegment) {
        checkNotNull(uriSegment);
        this.id = id;
        this.uriSegment = uriSegment;
        labelKey = null;
        positionIndex = -1;
        pageAccessControl = PageAccessControl.PERMISSION;
        this.roles = ImmutableList.copyOf(new ArrayList<>());
        this.viewClass = null;
    }

    public MasterSitemapNode(int id, NodeRecord nodeRecord) {
        this(id, nodeRecord.getUriSegment(), nodeRecord.getViewClass(), nodeRecord.getLabelKey(), nodeRecord.getPositionIndex(), nodeRecord
                .getPageAccessControl(), nodeRecord.getRoles());
    }

    public MasterSitemapNode(int id, String uriSegment, Class<? extends KrailView> viewClass, I18NKey labelKey, int
            positionIndex, PageAccessControl pageAccessControl, List<String> roles) {
        super();
        checkNotNull(uriSegment);
        if (roles == null) {
            this.roles = ImmutableList.copyOf(new ArrayList<>());
        } else {
            //remove stray empty strings
            while (roles.contains("")) {
                roles.remove("");
            }
            this.roles = ImmutableList.copyOf(roles);
        }

        this.pageAccessControl = pageAccessControl == null ? PageAccessControl.PERMISSION : pageAccessControl;

        this.labelKey = labelKey;
        this.positionIndex = positionIndex;

        this.uriSegment = uriSegment;
        this.viewClass = viewClass;
        this.id = id;
    }

    @Override
    public String getUriSegment() {
        return uriSegment;
    }


    @Override
    public I18NKey getLabelKey() {
        return labelKey;
    }



    @Override
    public Class<? extends KrailView> getViewClass() {
        return viewClass;
    }


    public String toStringAsMapEntry() {
        StringBuilder buf = new StringBuilder();
        buf.append((uriSegment == null) ? "no segment given" : uriSegment);
        buf.append((viewClass == null) ? "" : "\t\t:  " + viewClass.getSimpleName());
        buf.append((labelKey == null) ? "" : "\t~  " + ((Enum<?>) labelKey).name());
        return buf.toString();

    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("id=");
        buf.append(Integer.toString(id));
        buf.append(", segment=");
        buf.append((uriSegment == null) ? "null" : uriSegment);
        buf.append(", viewClass=");
        buf.append((viewClass == null) ? "null" : viewClass.getName());
        buf.append(", labelKey=");
        buf.append((labelKey == null) ? "null" : ((Enum<?>) labelKey).name());
        buf.append(", roles=");
        if (roles.isEmpty()) {
            buf.append("none");
        } else {
            boolean first = true;
            for (String role : roles) {
                if (!first) {
                    buf.append(';');
                }
                buf.append('[');
                buf.append(role);
                buf.append(']');
                first = false;
            }
        }
        return buf.toString();
    }

    public int getId() {
        return id;
    }


    public boolean isPublicPage() {
        return pageAccessControl == PageAccessControl.PUBLIC;
    }



    public boolean hasRoles() {
        return !roles.isEmpty();
    }

    @Override
    public PageAccessControl getPageAccessControl() {
        return pageAccessControl;
    }



    @Override
    public ImmutableList<String> getRoles() {
        return roles;
    }


    public int getPositionIndex() {
        return positionIndex;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MasterSitemapNode)) {
            return false;
        }

        MasterSitemapNode that = (MasterSitemapNode) o;

        return id == that.id;

    }

    /**
     * Returns a copy of this node, with the {@code pageAccessControl modified}
     *
     * @param pageAccessControl
     *
     * @return modified copy of this node
     */
    public MasterSitemapNode modifyPageAccessControl(PageAccessControl pageAccessControl) {
        checkNotNull(pageAccessControl);
        return new MasterSitemapNode(id, uriSegment, viewClass, labelKey, positionIndex, pageAccessControl, roles);
    }

    /**
     * Returns a copy of this node with the {@code viewClass} modified
     *
     * @param viewClass
     *
     * @return modified copy of this node
     */
    public MasterSitemapNode modifyView(Class<? extends KrailView> viewClass) {
        checkNotNull(viewClass);
        return new MasterSitemapNode(id, uriSegment, viewClass, labelKey, positionIndex, pageAccessControl, roles);
    }

    /**
     * Returns a copy of this node with the {@code labelKey} modified
     *
     * @param labelKey
     *
     * @return modified copy of this node
     */
    public MasterSitemapNode modifyLabelKey(I18NKey labelKey) {
        checkNotNull(labelKey);
        return new MasterSitemapNode(id, uriSegment, viewClass, labelKey, positionIndex, pageAccessControl, roles);
    }

    @Override
    public int hashCode() {
        return id;
    }
}

package uk.q3c.krail.option.api;

import uk.q3c.krail.core.option.OptionPermission;

/**
 * Created by David Sowerby on 09 Aug 2017
 */
public interface OptionPermissionVerifier {

    <T> boolean userHasPermission(OptionPermission.Action action, UserHierarchy hierarchy, int hierarchyRank, OptionKey<T> optionKey);
}

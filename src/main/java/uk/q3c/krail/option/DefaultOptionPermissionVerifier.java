package uk.q3c.krail.option;

import uk.q3c.krail.core.option.OptionPermission;
import uk.q3c.krail.option.api.OptionKey;
import uk.q3c.krail.option.api.OptionPermissionVerifier;
import uk.q3c.krail.option.api.UserHierarchy;

/**
 * Always gives permission - you might want to bind something else in its place!
 * <p>
 * Created by David Sowerby on 09 Aug 2017
 */
public class DefaultOptionPermissionVerifier implements OptionPermissionVerifier {
    @Override
    public <T> boolean userHasPermission(OptionPermission.Action action, UserHierarchy hierarchy, int hierarchyRank, OptionKey<T> optionKey) {
        return true;
    }
}

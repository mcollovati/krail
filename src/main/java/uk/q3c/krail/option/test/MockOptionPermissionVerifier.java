package uk.q3c.krail.option.test;

import org.apache.shiro.authz.AuthorizationException;
import uk.q3c.krail.core.option.OptionPermission;
import uk.q3c.krail.option.api.OptionKey;
import uk.q3c.krail.option.api.OptionPermissionVerifier;
import uk.q3c.krail.option.api.UserHierarchy;

/**
 * Created by David Sowerby on 09 Aug 2017
 */
public class MockOptionPermissionVerifier implements OptionPermissionVerifier {

    private boolean throwException = false;

    @Override
    public <T> boolean userHasPermission(OptionPermission.Action action, UserHierarchy hierarchy, int hierarchyRank, OptionKey<T> optionKey) {
        if (throwException) {
            throw new AuthorizationException("fake exception");
        }
        return true;
    }

    public MockOptionPermissionVerifier throwException(boolean value) {
        this.throwException = value;
        return this;
    }

    public boolean isThrowException() {
        return throwException;
    }
}

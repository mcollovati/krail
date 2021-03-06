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

package uk.q3c.krail.core.shiro.aop;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.authz.aop.UserAnnotationHandler;
import uk.q3c.krail.core.shiro.SubjectProvider;

/**
 * An AOP MethodInterceptor to detect whether a user is a User or not.  Typical error message might be:
 * <p>
 * "Attempting to perform a user-only operation.  The current Subject is not a user (they haven't been authenticated or remembered from a previous login).
 * Access denied."
 * <p>
 * Detection logic is a copy of the native Shiro version in {@link UserAnnotationHandler}
 * <p>
 * Created by David Sowerby on 10/06/15.
 */
public class UserMethodInterceptor extends ShiroMethodInterceptor<RequiresUser> {

    @Inject
    public UserMethodInterceptor(Provider<SubjectProvider> subjectProviderProvider, Provider<AnnotationResolver> annotationResolverProvider) {
        super(RequiresUser.class, subjectProviderProvider, annotationResolverProvider);
    }


    /**
     * Ensures that the calling <code>Subject</code> is a <em>user</em>, that is, they are <em>either</code>
     * {@link org.apache.shiro.subject.Subject#isAuthenticated() authenticated} <b><em>or</em></b> remembered via remember
     * me services before allowing access
     *
     * @param a
     *         the RequiresUser annotation to check
     *         @throws NotAUserException if user is not logged in or remembered
     */
    public void assertAuthorized(RequiresUser a) {
        if (getSubject().getPrincipal() == null) {
            throw new NotAUserException();
        }
    }
}

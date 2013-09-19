/*
 * Copyright (C) 2013 David Sowerby
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package uk.co.q3c.v7.base.shiro;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A DI wrapper for {@link SecurityUtils#getSubject()}
 * 
 * @author David Sowerby 15 Jul 2013
 * 
 */
@Singleton
public class SubjectProvider implements Provider<Subject> {
	private static final Logger LOG = LoggerFactory.getLogger(SubjectProvider.class);
	
	private final V7SecurityManager securityManager;

	@Inject
	protected SubjectProvider(V7SecurityManager securityManager) {
		super();
		this.securityManager = securityManager;
	}

	/**
	 * @see V7SecurityManager#getSubject()
	 */
	public Subject getSubject() {
		return securityManager.getSubject();
	}
	
	@Override
	public Subject get() {
		return this.securityManager.getSubject();
	}
}
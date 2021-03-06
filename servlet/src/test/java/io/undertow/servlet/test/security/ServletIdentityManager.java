/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.undertow.servlet.test.security;

import io.undertow.security.idm.Account;
import io.undertow.security.idm.Credential;
import io.undertow.security.idm.IdentityManager;
import io.undertow.security.idm.PasswordCredential;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A mock {@link IdentityManager} implementation for servlet security testing.
 *
 * @author <a href="mailto:darran.lofthouse@jboss.com">Darran Lofthouse</a>
 */
public class ServletIdentityManager implements IdentityManager {

    private final Map<String, UserAccount> users = new HashMap<String, UserAccount>();

    public void addUser(final String name, final String password, final String... roles) {
        UserAccount user = new UserAccount();
        user.name = name;
        user.password = password.toCharArray();
        user.roles = new HashSet<String>(Arrays.asList(roles));
        users.put(name, user);
    }

    @Override
    public Account verify(Account account) {
        // Just re-use the exising account.
        return account;
    }

    @Override
    public Account verify(String id, Credential credential) {
        Account account = getAccount(id);
        if (account != null && verifyCredential(account, credential)) {
            return account;
        }

        return null;
    }

    @Override
    public Account verify(Credential credential) {
        return null;
    }

    private boolean verifyCredential(Account account, Credential credential) {
        // This approach should never be copied in a realm IdentityManager.
        if (account instanceof UserAccount && credential instanceof PasswordCredential) {
            char[] expectedPassword = ((UserAccount) account).password;
            char[] suppliedPassword = ((PasswordCredential) credential).getPassword();

            return Arrays.equals(expectedPassword, suppliedPassword);
        }
        return false;
    }

    @Override
    public Account getAccount(String id) {
        return users.get(id);
    }

    @Override
    public char[] getPassword(final Account account) {
        return null;
    }

    private static class UserAccount implements Account {
        // In no way whatsoever should a class like this be considered a good idea for a real IdentityManager implementation,
        // this is for testing only.

        String name;
        char[] password;
        Set<String> roles;

        private final Principal principal = new Principal() {

            @Override
            public String getName() {
                return name;
            }
        };

        @Override
        public Principal getPrincipal() {
            return principal;
        }

        @Override
        public boolean isUserInGroup(String group) {
            return roles.contains(group);
        }
    }

}

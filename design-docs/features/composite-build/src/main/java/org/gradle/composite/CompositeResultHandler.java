/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.composite;

import org.gradle.tooling.GradleConnectionException;

/**
 * A handler for an asynchronous operation which returns an object of type T.
 *
 * @param <T> The result type.
 * @since 1.0-milestone-3
 */
public interface CompositeResultHandler<T> {

    /**
     * Handles successful completion of the operation.
     *
     * @param result the result
     * @since 1.0-milestone-3
     */
    void onComplete(ProjectIdentity id, T result);

    /**
     * Handles a failed operation. This method is invoked once only for a given operation.
     *
     * @param failure the failure
     * @since 1.0-milestone-3
     */
    void onFailure(ProjectIdentity id, GradleConnectionException failure);
}

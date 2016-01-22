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

package org.gradle.model.internal.inspect;

import org.gradle.model.Each;
import org.gradle.model.Transitive;

public enum ChildTraversalType {
    /**
     * Rule should be applied to the scope element only.
     */
    SELF,

    /**
     * Rule should be applied to the all matching direct child elements of the scope.
     */
    LINKS,

    /**
     * Rule should be applied to the all matching descendant elements of the scope.
     */
    DESCENDANTS;

    public static ChildTraversalType of(MethodRuleDefinition<?, ?> ruleDefinition) {
        boolean each = ruleDefinition.isAnnotationPresent(Each.class);
        boolean transitive = ruleDefinition.isAnnotationPresent(Transitive.class);
        if (each) {
            if (transitive) {
                return DESCENDANTS;
            } else {
                return LINKS;
            }
        } else {
            if (transitive) {
                throw new IllegalArgumentException("Cannot use @Transitive without @Each");
            } else {
                return SELF;
            }
        }
    }
}

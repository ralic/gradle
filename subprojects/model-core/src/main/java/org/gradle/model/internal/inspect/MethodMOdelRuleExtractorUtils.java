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

import org.gradle.model.internal.core.ModelAction;
import org.gradle.model.internal.core.ModelActionRole;
import org.gradle.model.internal.core.NodePredicate;
import org.gradle.model.internal.registry.ModelRegistry;

import static org.gradle.model.internal.core.NodePredicate.allDescendants;
import static org.gradle.model.internal.core.NodePredicate.allLinks;

public class MethodModelRuleExtractorUtils {
    public static void configureRuleAction(MethodModelRuleApplicationContext context, ChildTraversalType childTraversal, ModelActionRole role, MethodRuleAction ruleAction) {
        ModelAction action = context.contextualize(ruleAction);
        ModelRegistry registry = context.getRegistry();
        switch (childTraversal) {
            case SELF:
                registry.configure(role, action);
                break;
            case LINKS:
                configureMatching(registry, allLinks(), role, action);
                break;
            case DESCENDANTS:
                configureMatching(registry, allDescendants(), role, action);
                break;
            default:
                throw new AssertionError();
        }
    }

    private static void configureMatching(ModelRegistry registry, NodePredicate predicate, ModelActionRole role, ModelAction action) {
        registry.configureMatching(predicate.scope(action.getSubject().getScope()), role, action);
    }
}

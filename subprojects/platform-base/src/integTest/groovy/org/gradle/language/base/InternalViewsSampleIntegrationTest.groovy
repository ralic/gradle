/*
 * Copyright 2015 the original author or authors.
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

package org.gradle.language.base

import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.integtests.fixtures.Sample
import org.gradle.util.Requires
import org.gradle.util.TestPrecondition
import org.junit.Rule

@Requires(TestPrecondition.ONLINE)
class InternalViewsSampleIntegrationTest extends AbstractIntegrationSpec {
    @Rule
    Sample internalViewsSample = new Sample(temporaryFolder, "customModel/internalViews")

    // NOTE If you change this, you'll also need to change subprojects/docs/src/doc/samples/userguideOutput/softwareModelExtend-iv-model.out
    def "show mutated public view data but no internal view data in model report"() {
        given:
        sample internalViewsSample
        when:
        succeeds "model"
        then:
        println output
        output.contains """
            + components
                  | Type:   	org.gradle.platform.base.ComponentSpecContainer
                  | Creator: 	ComponentModelBasePlugin.Rules#components
                  | Rules:
                     ⤷ components { ... } @ build.gradle line 42, column 5
                     ⤷ MyPlugin#mutateMyComponents
                + my
                      | Type:   	MyComponent
                      | Creator: 	components { ... } @ build.gradle line 42, column 5 > create(my)
                      | Rules:
                         ⤷ ComponentModelBasePlugin#addSourcesSetsToProjectSourceSet
                         ⤷ ComponentModelBasePlugin#applyDefaultSourceConventions
                         ⤷ ComponentModelBasePlugin#initializeSourceSets
                         ⤷ ComponentModelBasePlugin#inputRules
                         ⤷ MyPlugin#mutateMyComponents > all()
                    + binaries
                          | Type:   	org.gradle.model.ModelMap<org.gradle.platform.base.BinarySpec>
                          | Creator: 	components { ... } @ build.gradle line 42, column 5 > create(my)
                          | Rules:
                             ⤷ ComponentModelBasePlugin.AttachInputs#initializeBinarySourceSets
                    + publicData
                          | Type:   	java.lang.String
                          | Value:  	Some PUBLIC data
                          | Creator: 	components { ... } @ build.gradle line 42, column 5 > create(my)
                    + sources
                          | Type:   	org.gradle.model.ModelMap<org.gradle.language.base.LanguageSourceSet>
                          | Creator: 	components { ... } @ build.gradle line 42, column 5 > create(my)
        """.stripIndent().trim()
    }
}

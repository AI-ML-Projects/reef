/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.reef.bridge.driver.launch.local;

import org.apache.commons.lang.StringUtils;
import org.apache.reef.annotations.audience.Private;
import org.apache.reef.bridge.driver.launch.RuntimeConfigurationProvider;
import org.apache.reef.bridge.proto.ClientProtocol;
import org.apache.reef.runtime.local.client.LocalRuntimeConfiguration;
import org.apache.reef.tang.Configuration;
import org.apache.reef.tang.formats.ConfigurationModule;

import javax.inject.Inject;

/**
 * Local runtime configuration provider.
 */
@Private
public final class LocalConfigurationProvider implements RuntimeConfigurationProvider {

  @Inject
  private LocalConfigurationProvider() {
  }

  @Override
  public Configuration getRuntimeConfiguration(
      final ClientProtocol.DriverClientConfiguration driverClientConfiguration) {
    ConfigurationModule localRuntimeCM = LocalRuntimeConfiguration.CONF;
    if (driverClientConfiguration.getLocalRuntime().getMaxNumberOfEvaluators() > 0) {
      localRuntimeCM = localRuntimeCM.set(LocalRuntimeConfiguration.MAX_NUMBER_OF_EVALUATORS,
          driverClientConfiguration.getLocalRuntime().getMaxNumberOfEvaluators());
    }
    if (StringUtils.isNotEmpty(driverClientConfiguration.getLocalRuntime().getRuntimeRootFolder())) {
      localRuntimeCM = localRuntimeCM.set(LocalRuntimeConfiguration.RUNTIME_ROOT_FOLDER,
          driverClientConfiguration.getLocalRuntime().getRuntimeRootFolder());
    }
    if (driverClientConfiguration.getLocalRuntime().getJvmHeapSlack() > 0.0) {
      localRuntimeCM = localRuntimeCM.set(LocalRuntimeConfiguration.JVM_HEAP_SLACK,
          driverClientConfiguration.getLocalRuntime().getJvmHeapSlack());
    }
    if (StringUtils.isNotEmpty(driverClientConfiguration.getDriverJobSubmissionDirectory())) {
      localRuntimeCM = localRuntimeCM.set(LocalRuntimeConfiguration.RUNTIME_ROOT_FOLDER,
          driverClientConfiguration.getDriverJobSubmissionDirectory());
    }
    return localRuntimeCM.build();
  }
}

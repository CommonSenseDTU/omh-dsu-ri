/*
 * Copyright 2016 Open mHealth
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

package org.openmhealth.dsu.service;

import org.openmhealth.dsu.domain.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Anders Borch on 05/12/16.
 */
@Service
@EnableConfigurationProperties
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "info")
public class InfoServiceImpl implements InfoService {

    @Value("${info.version}")
    private String version;

    public Version getVersion() {

        List<Integer> components = Arrays.stream(version.split("[.]"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return new Version(components.get(0), components.get(1), components.get(2));
    }

}


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

package org.openmhealth.dsu.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.domain.omh.SchemaId;
import org.openmhealth.schema.domain.omh.SchemaSupport;
import org.openmhealth.schema.serializer.SerializationConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Created by Anders Borch on 05/12/16.
 */
@JsonInclude(NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Version implements SchemaSupport {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "version", "1.0");

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    private int majorVersion;
    private int minorVersion;
    private int bugfixVersion;

    @SerializationConstructor
    protected Version() {}

    @JsonCreator
    public Version(int major, int minor, int bugfix) {

        this.majorVersion = major;
        this.minorVersion = minor;
        this.bugfixVersion = bugfix;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public int getBugfixVersion() {
        return bugfixVersion;
    }

    public void setBugfixVersion(int bugfixVersion) {
        this.bugfixVersion = bugfixVersion;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Version that = (Version) object;

        return majorVersion == that.majorVersion && minorVersion == that.minorVersion && bugfixVersion == that.bugfixVersion;
    }

    @Override
    public int hashCode() {

        Object[] data = { majorVersion, minorVersion, bugfixVersion };
        return String.format("%d.%d.%d", data).hashCode();
    }
}

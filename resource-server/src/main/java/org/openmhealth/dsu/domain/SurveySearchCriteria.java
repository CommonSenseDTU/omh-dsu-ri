package org.openmhealth.dsu.domain;

import com.google.common.collect.Range;
import org.openmhealth.schema.domain.omh.SchemaVersion;

import java.time.OffsetDateTime;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.openmhealth.schema.domain.omh.SchemaVersion.isValidVersion;

/**
 * @author Anders Borch
 */
public class SurveySearchCriteria {

    private String userId;
    private SchemaVersion schemaVersion;
    private Range<OffsetDateTime> creationTimestampRange;
    private String id;

    public SurveySearchCriteria(String schemaVersion) {

        checkNotNull(schemaVersion);

        checkArgument(isValidVersion(schemaVersion));

        this.schemaVersion = new SchemaVersion(schemaVersion);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public SchemaVersion getSchemaVersion() {
        return schemaVersion;
    }

    public Optional<Range<OffsetDateTime>> getCreationTimestampRange() {
        return Optional.ofNullable(creationTimestampRange);
    }

    public void setCreationTimestampRange(Range<OffsetDateTime> creationTimestampRange) {
        this.creationTimestampRange = creationTimestampRange;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

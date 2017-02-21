package org.openmhealth.dsu.repository;

import org.openmhealth.dsu.domain.PrivateSearchCriteria;
import org.openmhealth.schema.domain.ork.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Anders Borch
 */
public class MongoPrivateRepositoryImpl implements CustomPrivateRepository {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Iterable<Signature> findBySearchCriteria(PrivateSearchCriteria searchCriteria, @Nullable Integer offset, @Nullable Integer limit) {
        checkNotNull(searchCriteria);
        checkArgument(offset == null || offset >= 0);
        checkArgument(limit == null || limit >= 0);

        Query query = newQuery(searchCriteria);

        if (offset != null) {
            query.skip(offset);
        }

        if (limit != null) {
            query.limit(limit);
        }

        return mongoOperations.find(query, Signature.class);

    }

    private Query newQuery(PrivateSearchCriteria searchCriteria) {

        Query query = new Query();

        return query;
    }

}

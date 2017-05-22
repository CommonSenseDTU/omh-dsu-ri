package org.openmhealth.dsu.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * @author Anders Borch
 */
public class MongoMetaDataRepositoryImpl implements CustomMetaDataRepository {

    @Autowired
    private MongoOperations mongoOperations;

}

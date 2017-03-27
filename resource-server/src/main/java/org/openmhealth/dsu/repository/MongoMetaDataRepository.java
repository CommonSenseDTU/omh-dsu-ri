package org.openmhealth.dsu.repository;

/**
 * A survey repository interface for MongoDB. This interface is necessary to get Spring Data to link up its
 * generated {@link MetaDataRepository} implementation with {@link MongoMetaDataRepositoryImpl}.
 *
 * @author Anders Borch
 */
public interface MongoMetaDataRepository extends MetaDataRepository {
}


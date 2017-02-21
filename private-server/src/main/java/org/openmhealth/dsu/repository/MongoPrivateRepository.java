package org.openmhealth.dsu.repository;

/**
 * A survey repository interface for MongoDB. This interface is necessary to get Spring Data to link up its
 * generated {@link PrivateRepository} implementation with {@link MongoPrivateRepositoryImpl}.
 *
 * @author Anders Borch
 */
public interface MongoPrivateRepository extends PrivateRepository {

}

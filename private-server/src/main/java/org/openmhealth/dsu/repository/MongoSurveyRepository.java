package org.openmhealth.dsu.repository;

/**
 * A survey repository interface for MongoDB. This interface is necessary to get Spring Data to link up its
 * generated {@link SurveyRepository} implementation with {@link MongoSurveyRepositoryImpl}.
 *
 * @author Anders Borch
 */
public interface MongoSurveyRepository extends SurveyRepository {

}

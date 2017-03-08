package org.openmhealth.dsu.domain;

/**
 * @author Anders Borch
 */
public class StepSearchCriteria {

    private String id;
    private String surveyId;

    public StepSearchCriteria() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }
}

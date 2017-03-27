package org.openmhealth.dsu.controller;

import org.openmhealth.dsu.domain.EndUserUserDetails;
import org.openmhealth.dsu.domain.SurveySearchCriteria;
import org.openmhealth.dsu.domain.StepSearchCriteria;
import org.openmhealth.dsu.service.SurveyService;
import org.openmhealth.schema.domain.omh.ParticipationMetaData;
import org.openmhealth.schema.domain.ork.ConsentDocument;
import org.openmhealth.schema.domain.ork.Step;
import org.openmhealth.schema.domain.ork.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.openmhealth.dsu.configuration.OAuth2Properties.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Anders Borch
 */
@ApiController
public class SurveyController {

    public static final String SCHEMA_VERSION_PARAMETER = "schema_version";
    public static final String RESULT_OFFSET_PARAMETER = "skip";
    public static final String RESULT_LIMIT_PARAMETER = "limit";
    public static final String DEFAULT_RESULT_LIMIT = "100";

    @Autowired
    private SurveyService surveyService;

    /**
     * Reads surveys created by current user.
     *
     * @param schemaVersion the version of the schema the surveys conform to
     * @param offset the number of surveys to skip
     * @param limit the number of surveys to return
     * @return a list of matching surveys
     */
    @PreAuthorize("#oauth2.clientHasRole('" + CLIENT_ROLE + "') and #oauth2.hasScope('" + SURVEY_READ_SCOPE + "')")
    // TODO look into any meaningful @PostAuthorize filtering
    @RequestMapping(value = "/surveys/my", method = {HEAD, GET}, produces = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Iterable<Survey>> readMySurveys(
            @RequestParam(value = SCHEMA_VERSION_PARAMETER) final String schemaVersion,
            @RequestParam(value = RESULT_OFFSET_PARAMETER, defaultValue = "0") final Integer offset,
            @RequestParam(value = RESULT_LIMIT_PARAMETER, defaultValue = DEFAULT_RESULT_LIMIT) final Integer limit,
            Authentication authentication) {

        String endUserId = getEndUserId(authentication);

        SurveySearchCriteria searchCriteria = new SurveySearchCriteria(schemaVersion);
        searchCriteria.setUserId(endUserId);

        Iterable<Survey> surveys = surveyService.findBySearchCriteria(searchCriteria, offset, limit);

        HttpHeaders headers = new HttpHeaders();

        // FIXME add pagination headers
        // headers.set("Next");
        // headers.set("Previous");

        return new ResponseEntity<>(surveys, headers, OK);
    }

    /**
     * Reads surveys which current user has participated in.
     *
     * @param schemaVersion the version of the schema the surveys conform to
     * @param offset the number of surveys to skip
     * @param limit the number of surveys to return
     * @return a list of matching surveys
     */
    @PreAuthorize("#oauth2.clientHasRole('" + CLIENT_ROLE + "') and #oauth2.hasScope('" + SURVEY_READ_SCOPE + "')")
    // TODO look into any meaningful @PostAuthorize filtering
    @RequestMapping(value = "/surveys/participated", method = {HEAD, GET}, produces = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Iterable<Survey>> readParticipatedSurveys(
            @RequestParam(value = SCHEMA_VERSION_PARAMETER) final String schemaVersion,
            @RequestParam(value = RESULT_OFFSET_PARAMETER, defaultValue = "0") final Integer offset,
            @RequestParam(value = RESULT_LIMIT_PARAMETER, defaultValue = DEFAULT_RESULT_LIMIT) final Integer limit,
            Authentication authentication) {

        SurveySearchCriteria searchCriteria = new SurveySearchCriteria(schemaVersion);

        Iterable<Survey> surveys = surveyService.findBySearchCriteria(searchCriteria, offset, limit);

        HttpHeaders headers = new HttpHeaders();

        // FIXME add pagination headers
        // headers.set("Next");
        // headers.set("Previous");

        return new ResponseEntity<>(surveys, headers, OK);
    }

    /**
     * Updates metadata for a user in a survey.
     * The authenticated client must either be the owner of the survey or the participant named by the user id.
     *
     * @param metaData the meta data to store
     */
    @PreAuthorize("#oauth2.clientHasRole('" + CLIENT_ROLE + "')")
    @RequestMapping(value = "/surveys/{id}/metadata", method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> writeMetaData(@RequestBody @Valid ParticipationMetaData metaData, Authentication authentication) {

        String endUserId = getEndUserId(authentication);
        surveyService.updateMetaData(metaData, endUserId);

        return new ResponseEntity<>(OK);
    }

    /**
     * Reads all surveys
     *
     * @param schemaVersion the version of the schema the surveys conform to
     * @param offset the number of surveys to skip
     * @param limit the number of surveys to return
     * @return a list of matching surveys
     */
    @PreAuthorize("#oauth2.clientHasRole('" + CLIENT_ROLE + "') and #oauth2.hasScope('" + SURVEY_READ_SCOPE + "')")
    // TODO look into any meaningful @PostAuthorize filtering
    @RequestMapping(value = "/surveys", method = {HEAD, GET}, produces = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Iterable<Survey>> readSurveys(
            @RequestParam(value = SCHEMA_VERSION_PARAMETER) final String schemaVersion,
            @RequestParam(value = RESULT_OFFSET_PARAMETER, defaultValue = "0") final Integer offset,
            @RequestParam(value = RESULT_LIMIT_PARAMETER, defaultValue = DEFAULT_RESULT_LIMIT) final Integer limit,
            Authentication authentication) {

        SurveySearchCriteria searchCriteria = new SurveySearchCriteria(schemaVersion);

        Iterable<Survey> surveys = surveyService.findBySearchCriteria(searchCriteria, offset, limit);

        HttpHeaders headers = new HttpHeaders();

        // FIXME add pagination headers
        // headers.set("Next");
        // headers.set("Previous");

        return new ResponseEntity<>(surveys, headers, OK);
    }


    public String getEndUserId(Authentication authentication) {

        return ((EndUserUserDetails) authentication.getPrincipal()).getUsername();
    }

    /**
     * Reads a survey without authorization.
     *
     * @param id the identifier of the survey to read
     * @return a matching survey, if found
     */
    @RequestMapping(value = "/consentflow/surveys/{id}", method = {HEAD, GET}, produces = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Survey> readPublicSurvey(@PathVariable String id) {

        Optional<Survey> survey = surveyService.findOne(id);

        if (!survey.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        // FIXME test @PostAuthorize
        return new ResponseEntity<>(survey.get(), OK);
    }

    /**
     * Reads a task step.
     *
     * @param surveyId the identifier of the survey where the task is located
     * @param taskId the identifier of the task to read
     * @return a matching step, if found
     */
    @PreAuthorize("#oauth2.clientHasRole('" + CLIENT_ROLE + "') and #oauth2.hasScope('" + SURVEY_READ_SCOPE + "')")
    @RequestMapping(value = "/surveys/{surveyId}/tasks/{taskId}", method = {HEAD, GET}, produces = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Step> readStep(@PathVariable String surveyId, @PathVariable String taskId) {

        StepSearchCriteria searchCriteria = new StepSearchCriteria();
        searchCriteria.setId(taskId);
        searchCriteria.setSurveyId(surveyId);

        Optional<Step> step = surveyService.findBySearchCriteria(searchCriteria);

        if (!step.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        // FIXME test @PostAuthorize
        return new ResponseEntity<Step>(step.get(), OK);
    }

    /**
     * Reads a survey.
     *
     * @param id the identifier of the survey to read
     * @return a matching survey, if found
     */
    @PreAuthorize("#oauth2.clientHasRole('" + CLIENT_ROLE + "') and #oauth2.hasScope('" + SURVEY_READ_SCOPE + "')")
    @RequestMapping(value = "/surveys/{id}", method = {HEAD, GET}, produces = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Survey> readSurvey(@PathVariable String id) {

        Optional<Survey> survey = surveyService.findOne(id);

        if (!survey.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        // FIXME test @PostAuthorize
        return new ResponseEntity<>(survey.get(), OK);
    }

    /**
     * Writes a survey.
     *
     * @param survey the survey to write
     */
    // only allow clients with write scope to write surveys
    @PreAuthorize("#oauth2.clientHasRole('" + CLIENT_ROLE + "') and #oauth2.hasScope('" + SURVEY_WRITE_SCOPE + "')")
    @RequestMapping(value = "/surveys", method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> writeSurvey(@RequestBody @Valid Survey survey, Authentication authentication) {

        if (survey.getConsentDocument() == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        String endUserId = getEndUserId(authentication);

        Optional<Survey> existing = surveyService.findOne(survey.getId());
        if (existing.isPresent()) {
            // return conflict if another user owns this survey
            if (!existing.get().getUserId().equals(endUserId)) {
                return new ResponseEntity<>(CONFLICT);
            }
            // return conflict if the consent document id doesn't match the existing document id
            ConsentDocument consentDocument = existing.get().getConsentDocument();
            if (!consentDocument.getId().equals(survey.getConsentDocument().getId())) {
                return new ResponseEntity<>(CONFLICT);
            }
            // keep creation data time, set modification date time to now if the consent document a updated
            if (survey.getConsentDocument() != null && !consentDocument.equals(survey.getConsentDocument())) {
                survey.getConsentDocument().setCreationDateTime(consentDocument.getCreationDateTime());
                survey.getConsentDocument().setModificationDateTime(OffsetDateTime.now());
            }
        }
        else {
            survey.getConsentDocument().setCreationDateTime(OffsetDateTime.now());
            survey.getConsentDocument().setModificationDateTime(OffsetDateTime.now());
        }

        // set the owner of the survey to be the user associated with the access token
        survey.setUserId(endUserId);

        surveyService.save(survey);

        return new ResponseEntity<>(CREATED);
    }

}

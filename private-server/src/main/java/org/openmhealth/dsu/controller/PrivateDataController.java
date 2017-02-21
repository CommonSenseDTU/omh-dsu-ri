package org.openmhealth.dsu.controller;

import org.openmhealth.dsu.domain.EndUserUserDetails;
import org.openmhealth.dsu.domain.PrivateSearchCriteria;
import org.openmhealth.dsu.service.PrivateService;
import org.openmhealth.schema.domain.ork.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.openmhealth.dsu.configuration.OAuth2Properties.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Anders Borch
 */
@ApiController
public class PrivateDataController {

    /*
     * These filtering parameters are temporary. They will likely change when a more generic filtering approach is
     * implemented.
     */
    public static final String SCHEMA_VERSION_PARAMETER = "schema_version";

    public static final String RESULT_OFFSET_PARAMETER = "skip";
    public static final String RESULT_LIMIT_PARAMETER = "limit";
    public static final String DEFAULT_RESULT_LIMIT = "100";

    @Autowired
    private PrivateService privateService;

    /**
     * Reads private signatures data created by current user.
     *
     * @param schemaVersion the version of the schema the surveys conform to
     * @param offset the number of data point to skip
     * @param limit the number of data points to return
     * @return a list of matching data points
     */
    @PreAuthorize("#oauth2.clientHasRole('" + CLIENT_ROLE + "') and #oauth2.hasScope('" + DATA_POINT_READ_SCOPE + "')")
    // TODO look into any meaningful @PostAuthorize filtering
    @RequestMapping(value = "/private/signatures/my", method = {HEAD, GET}, produces = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Iterable<Signature>> readMySignatures(
            @RequestParam(value = SCHEMA_VERSION_PARAMETER) final String schemaVersion,
            @RequestParam(value = RESULT_OFFSET_PARAMETER, defaultValue = "0") final Integer offset,
            @RequestParam(value = RESULT_LIMIT_PARAMETER, defaultValue = DEFAULT_RESULT_LIMIT) final Integer limit,
            Authentication authentication) {

        String endUserId = getEndUserId(authentication);

        PrivateSearchCriteria searchCriteria = new PrivateSearchCriteria(schemaVersion);
        searchCriteria.setUserId(endUserId);

        Iterable<Signature> signatures = privateService.findBySearchCriteria(searchCriteria, offset, limit);

        HttpHeaders headers = new HttpHeaders();

        // FIXME add pagination headers
        // headers.set("Next");
        // headers.set("Previous");

        return new ResponseEntity<Iterable<Signature>>(signatures, headers, OK);
    }

    public String getEndUserId(Authentication authentication) {

        return ((EndUserUserDetails) authentication.getPrincipal()).getUsername();
    }

    /**
     * Writes a signature.
     *
     * @param signature the signature to write
     */
    // only allow clients with write scope to write signatures
    @PreAuthorize("#oauth2.clientHasRole('" + CLIENT_ROLE + "') and #oauth2.hasScope('" + DATA_POINT_WRITE_SCOPE + "')")
    @RequestMapping(value = "/private/signatures/my", method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> writeSignature(@RequestBody @Valid Signature signature, Authentication authentication) {

        // FIXME test validation
        if (privateService.exists(signature.getId())) {
            return new ResponseEntity<>(CONFLICT);
        }

        String endUserId = getEndUserId(authentication);
        signature.setUserId(endUserId);

        privateService.save(signature);

        return new ResponseEntity<>(CREATED);
    }
}

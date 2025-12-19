package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.PsnFetchResponse;
import fr.trophyquest.web.service.service.PsnFetcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

@RestController
@RequestMapping("/api/psn")
@CrossOrigin(origins = "*")
public class PsnController {
    private final Logger logger = LoggerFactory.getLogger(PsnController.class);
    private final PsnFetcherService psnFetcherService;

    public PsnController(PsnFetcherService psnFetcherService) {
        this.psnFetcherService = psnFetcherService;
    }

    @PostMapping("/{profileName}")
    public ResponseEntity<PsnFetchResponse> addProfile(@PathVariable String profileName) {
        try {
            InvokeResponse response = psnFetcherService.trigger(profileName);
            boolean hasFuncError = response.functionError() != null && !response.functionError().isBlank();
            var body = new PsnFetchResponse(
                    hasFuncError ? "ERROR" : "OK",
                    response.statusCode(),
                    hasFuncError
            );

            if (hasFuncError) {
                return ResponseEntity.status(502).body(body);
            }
            if (response.statusCode() == null || response.statusCode() >= 400) {
                return ResponseEntity.status(502).body(body);
            }

            return ResponseEntity.ok(body);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(502).body(new PsnFetchResponse("ERROR", 502, true));
        }
    }
}

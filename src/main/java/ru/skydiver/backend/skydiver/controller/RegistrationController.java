package ru.skydiver.backend.skydiver.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import liquibase.repackaged.net.sf.jsqlparser.util.validation.ValidationError;
import org.openapitools.api.RegistrationApi;
import org.openapitools.model.RegistrationUserRequest;
import org.openapitools.model.RegistrationValidationError;
import org.openapitools.model.RegistrationValidationRequest;
import org.openapitools.model.RegistrationValidationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skydiver.backend.skydiver.model.ContactTypes;
import ru.skydiver.backend.skydiver.services.RegistrationService;

@RestController
public class RegistrationController implements RegistrationApi {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public ResponseEntity<Void> register(RegistrationUserRequest registrationUserRequest) {
        registrationService.registerNewUser(
                registrationUserRequest.getUserName(),
                registrationUserRequest.getPassword(),
                Map.of(ContactTypes.EMAIL, registrationUserRequest.getEmail()));
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<RegistrationValidationResponse> validateUserData(RegistrationValidationRequest registrationValidationRequest) {
         var result = registrationService.validateUser(registrationValidationRequest.getUserName(),
                 Map.of(ContactTypes.EMAIL, registrationValidationRequest.getEmail()));
         var data = new RegistrationValidationResponse()
                 .validationStatus(result.isEmpty())
                 .validationError(
                         result.entrySet().stream()
                                 .map(entry -> new RegistrationValidationError()
                                         .property(entry.getKey())
                                         .message(entry.getValue()))
                                 .collect(Collectors.toList()));
         return ResponseEntity.ok(data);
    }
}

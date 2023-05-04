package ru.skydiver.backend.skydiver.services;

import java.util.HashMap;
import java.util.Map;

import org.openapitools.model.ValidationProperty;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skydiver.backend.skydiver.model.ContactTypes;
import ru.skydiver.backend.skydiver.repositories.ContactRepository;

@Service
public class RegistrationService {
    private final ContactRepository contactRepository;
    private final UserDetailsManager userDetailsManager;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegistrationService(ContactRepository contactRepository,
                               UserDetailsManager userDetailsManager,
                               BCryptPasswordEncoder passwordEncoder) {
        this.contactRepository = contactRepository;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUser(
            String userName,
            String password,
            Map<ContactTypes, String> contacts
    ) {
        var validation = validateUser(userName, contacts);
        if (!validation.isEmpty()) {
            throw new IllegalStateException("Not valid user data");
        }
        userDetailsManager.createUser(
                User
                        .withUsername(userName)
                        .password(passwordEncoder.encode(password))
                        .authorities("buyer")
                        .build()
        );
        var user = userDetailsManager.loadUserByUsername(userName);
        contacts.forEach((type, contact) ->
                contactRepository.addContactToUser(type, contact, user.getUsername()));
    }

    public Map<ValidationProperty, String> validateUser(
            String userName,
            Map<ContactTypes, String> contacts
    ) {
        var validationResult = new HashMap<ValidationProperty, String>();
        if (userDetailsManager.userExists(userName)) {
            validationResult.put(ValidationProperty.USER, "User already exists");
        }

        contacts.forEach((key, value) -> {
            if (contactRepository.findByContact(value).isPresent()) {
                validationResult.put(ValidationProperty.CONTACT, "Contact " + key.name() + "already exist");
            }
        });
        return validationResult;
    }
}

package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.Log;
import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.repository.LogRepository;
import de.dhbw.erpbackend.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class LogService {
    @Inject UserRepository userRepository;
    @Inject LogRepository logRepository;

    public void log(String actorUsername, LogType type, String description) {
        try {
            if (actorUsername == null) {
                return;
            }
            User user = userRepository.findByUsername(actorUsername).orElse(null);
            if (user == null) {
                return;
            }
            Log entry = new Log();
            entry.setUser(user);
            entry.setType(type);
            entry.setDescription(description);
            logRepository.insert(entry);
        } catch (RuntimeException ex) {
            // ignore failed logging as logs should not break the main application logic
        }
    }
}

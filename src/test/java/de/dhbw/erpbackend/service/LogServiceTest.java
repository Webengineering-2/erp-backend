package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.Log;
import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.repository.LogRepository;
import de.dhbw.erpbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    @Mock UserRepository userRepository;
    @Mock LogRepository logRepository;
    @InjectMocks LogService service;

    @Test
    void logResolvesActorAndInsertsEntry() {
        User alice = new User();
        alice.setUsername("alice");
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(alice));

        service.log("alice", LogType.PRODUCT_CREATED, "Produkt 'Cola' wurde erstellt.");

        ArgumentCaptor<Log> captor = ArgumentCaptor.forClass(Log.class);
        verify(logRepository).insert(captor.capture());
        Log saved = captor.getValue();
        assertSame(alice, saved.getUser());
        assertEquals(LogType.PRODUCT_CREATED, saved.getType());
        assertEquals("Produkt 'Cola' wurde erstellt.", saved.getDescription());
    }

    @Test
    void logWithNullActorDoesNothing() {
        service.log(null, LogType.PRODUCT_CREATED, "ignored");
        verifyNoInteractions(userRepository, logRepository);
    }

    @Test
    void logWithUnknownActorDoesNotInsert() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());
        service.log("ghost", LogType.USER_LOGIN, "ignored");
        verify(logRepository, never()).insert(any());
    }

    @Test
    void logSwallowsRepositoryFailure() {
        User alice = new User();
        alice.setUsername("alice");
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(alice));
        when(logRepository.insert(any())).thenThrow(new RuntimeException("db down"));

        // Logging must never break the action it describes.
        assertDoesNotThrow(() -> service.log("alice", LogType.USER_LOGIN, "desc"));
    }
}

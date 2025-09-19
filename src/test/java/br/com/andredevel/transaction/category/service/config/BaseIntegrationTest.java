package br.com.andredevel.transaction.category.service.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @BeforeEach
    void setUpSecurityContext() {
        // Simular usuário autenticado para os testes
        String mockUserId = "00000000-0000-0000-0000-000000000000";
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                mockUserId, null, Collections.emptyList()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
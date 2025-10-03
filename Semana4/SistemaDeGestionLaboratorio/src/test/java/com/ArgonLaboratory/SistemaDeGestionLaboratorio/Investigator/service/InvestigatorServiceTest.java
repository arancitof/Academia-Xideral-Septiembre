package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.service;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@org.springframework.test.context.ActiveProfiles("test")
public class InvestigatorServiceTest {
}

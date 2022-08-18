package com.example.finalcsis3275;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
public class LoanControllerTest {
    @Mock
    LoanRepository loanRepository;

    @Mock
    View mockView;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    LoanController loanController;

    loantable loan1, loan2;

    @BeforeEach
    void setUp() throws ParseException {
        loan1 = new loantable("1157", "Joy Ramirez", 100000.0, 5,"Business");
        loan2 = new loantable("1005", "Josaphat Dee", 5000.0, 5,"Business");
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(loanController).setSingleView(mockView).build();
    }

    @Test
    void showIndexPage() throws Exception {
        // 1. Create mock data
        List<loantable> mockList = new ArrayList<>();
        mockList.add(loan1);
        mockList.add(loan2);

        // 2. Define behavior of repository
        when(loanRepository.findAll()).thenReturn(mockList);

        // 3. Call method for testing

        // 4. assert the result
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listRecords", mockList))
                .andExpect(view().name("displayAllRecords"))
                .andExpect(model().attribute("listRecords", hasSize(2)));

        // 4.1 Ensure repository is called
        verify(loanRepository, times(1)).findAll();
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    void deleteRecord(){
        // 1. Create mock data
        ArgumentCaptor<String> idCapture = ArgumentCaptor.forClass(String.class);

        // 2. Define behavior of repository
        doNothing().when(loanRepository).deleteById(idCapture.capture());

        // 3. Call method for testing
        loanRepository.deleteById("1157");

        // 4. assert the result
        assertEquals("1157", idCapture.getValue());

        // 4.1 Ensure repository is called
        verify(loanRepository, times(1)).deleteById("1157");
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    void showAddPage() throws Exception {
        // 1. Create mock data
        List<String> savingsTypeList = Arrays.asList("Business", "Personal");

        // 2. Define behavior of repository
        // 3. Call method for testing

        // 4. assert the result
        mockMvc.perform(get("/addPage"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("record", new loantable()))
                .andExpect(model().attribute("savingsTypeList", savingsTypeList))
                .andExpect(view().name("addRecord"));

        // 4.1 Ensure repository is called
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    void showEditPage() throws Exception {
        // 1. Create mock data
        //use loan 2
        List<String> savingsTypeList = Arrays.asList("Business", "Personal");

        // 2. Define behavior of repository
        when(loanRepository.findById("1005")).thenReturn(Optional.of(loan2));

        // 3. Call method for testing

        // 4. assert the result
        mockMvc.perform(get("/editRecords").param("clientno","1005"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("savingsTypeList", savingsTypeList))
                .andExpect(model().attribute("record", loan2))
                .andExpect(view().name("editRecord"));

        // 4.1 Ensure repository is called
        verify(loanRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    void save(){
        // 1. Create mock data

        // 2. Define behavior of repository
        when(loanRepository.save(loan1)).thenReturn(loan1);

        // 3. Call method for testing
        loanRepository.save(loan1);

        // 4. assert the result

        // 4.1 Ensure repository is called
        verify(loanRepository, times(1)).save(loan1);
        verifyNoMoreInteractions(loanRepository);
    }
}

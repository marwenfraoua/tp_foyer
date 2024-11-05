package tn.esprit.rh.achat.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import tn.esprit.tpfoyer.TpFoyerApplication;
@SpringBootTest(classes= TpFoyerApplication.class)
@TestMethodOrder(OrderAnnotation.class)
class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testRetrieveAllBlocs() {
        // Arrange
        Foyer  f1 = null;
        Set<Chambre> chambres = null;
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc(1, "Bloc A", 100,f1,chambres)); // Assuming a constructor exists
        when(blocRepository.findAll()).thenReturn(blocs);

        // Act
        List<Bloc> retrievedBlocs = blocService.retrieveAllBlocs();

        // Assert
        Assertions.assertEquals(1, retrievedBlocs.size());
        Assertions.assertEquals("Bloc A", retrievedBlocs.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testAddBloc() {
        // Arrange
        Foyer  f1 = null;
        Set<Chambre> chambres = null;
        Bloc bloc = new Bloc(2L, "Bloc B", 150,f1,chambres);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc addedBloc = blocService.addBloc(bloc);

        // Assert
        Assertions.assertNotNull(addedBloc);
        Assertions.assertEquals("Bloc B", addedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    @Order(3)
    public void testRetrieveBloc() {
        // Arrange
        Foyer  f1 = null;
        Set<Chambre> chambres = null;
        Bloc bloc = new Bloc(3, "Bloc C", 200,f1,chambres);
        when(blocRepository.findById(anyLong())).thenReturn(Optional.of(bloc));

        // Act
        Bloc retrievedBloc = blocService.retrieveBloc(3L);

        // Assert
        Assertions.assertNotNull(retrievedBloc);
        Assertions.assertEquals("Bloc C", retrievedBloc.getNomBloc());
        verify(blocRepository, times(1)).findById(3L);
    }

    @Test
    @Order(4)
    public void testModifyBloc() {
        // Arrange
        Foyer  f1 = null;
        Set<Chambre> chambres = null;
        Bloc bloc = new Bloc(4, "Bloc D", 250,f1,chambres);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc modifiedBloc = blocService.modifyBloc(bloc);

        // Assert
        Assertions.assertNotNull(modifiedBloc);
        Assertions.assertEquals("Bloc D", modifiedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    @Order(5)
    public void testRemoveBloc() {
        // Arrange
        Long blocId = 5L;

        // Act
        blocService.removeBloc(blocId);

        // Assert
        verify(blocRepository, times(1)).deleteById(blocId);
    }

    @Test
    @Order(6)
    public void testRetrieveBlocsSelonCapacite() {
        // Arrange
        long capacity = 100;
        List<Bloc> blocs = new ArrayList<>();
        Foyer  f1 = null;
        Set<Chambre> chambres = null;
        blocs.add(new Bloc(1, "Bloc A", 150,f1,chambres));
        when(blocRepository.findAll()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(capacity);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Bloc A", result.get(0).getNomBloc());
    }

    @Test
    @Order(7)
    public void testTrouverBlocsSansFoyer() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        Foyer  f1 = null;
        Set<Chambre> chambres = null;
        blocs.add(new Bloc(1, "Bloc A", 100,f1,chambres));
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Bloc A", result.get(0).getNomBloc());
    }

    @Test
    @Order(8)
    public void testTrouverBlocsParNomEtCap() {
        // Arrange
        String name = "Bloc A";
        long capacity = 100;
        List<Bloc> blocs = new ArrayList<>();
        Foyer  f1 = null;
        Set<Chambre> chambres = null;
        blocs.add(new Bloc(1, "Bloc A", 100,f1,chambres));
        when(blocRepository.findAllByNomBlocAndCapaciteBloc(name, capacity)).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.trouverBlocsParNomEtCap(name, capacity);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Bloc A", result.get(0).getNomBloc());
    }
}

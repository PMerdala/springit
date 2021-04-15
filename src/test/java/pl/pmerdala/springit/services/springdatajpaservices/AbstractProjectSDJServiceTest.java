package pl.pmerdala.springit.services.springdatajpaservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pmerdala.springit.datamodel.repositories.ProjectRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbstractProjectSDJServiceTest {

    @Mock
    ProjectRepository<String, Long> repository;
    @Mock
    Mapper<String, String> mapper;

    AbstractProjectSDJService<String, String, Long> sut;

    @BeforeEach
    void setUp() {
        sut = new AbstractProjectSDJService<>(repository, mapper);
    }

    @Test
    void findAllCalledRepository() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        sut.findAll();
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAllCalledMapper() {
        List<String> data = Stream.of("a", "a").collect(Collectors.toList());
        when(repository.findAll()).thenReturn(data);
        sut.findAll();
        verify(mapper, times(2)).map("a");
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void findAll() {
        List<String> data = Stream.of("A", "C").collect(Collectors.toList());
        when(repository.findAll()).thenReturn(data);
        when(mapper.map("A")).thenReturn("B");
        when(mapper.map("C")).thenReturn("D");
        Set<String> strings = sut.findAll();
        assertEquals(2,strings.size());
        assertTrue(strings.contains("B"));
        assertTrue(strings.contains("D"));
    }

    @Test
    void findByIdCalledRepository() {
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        sut.findById(1L);
        verify(repository).findById(captor.capture());
        assertEquals(1L, captor.getValue());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findByIdCalledMapper() {
        when(repository.findById(anyLong())).thenReturn(Optional.of("A"));
        sut.findById(1L);
        verify(mapper).map(Optional.of("A"));
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of("A"));
        when(mapper.map(Optional.of("A"))).thenReturn(Optional.of("B"));
        Optional<String> string = sut.findById(1L);
        assertEquals("B", string.orElse(null));
    }

    @Test
    void saveCalledRepository() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        when(mapper.reversMap(anyString())).thenReturn("A");
        sut.save("B");
        verify(repository).save(captor.capture());
        assertEquals("A", captor.getValue());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void saveCalledMapperMap() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        when(repository.save(any())).thenReturn("B");
        sut.save("A");
        verify(mapper).map(captor.capture());
        assertEquals("B", captor.getValue());
    }

    @Test
    void saveCalledMapperReversMap() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        when(repository.save(any())).thenReturn("B");
        sut.save("A");
        verify(mapper).reversMap(captor.capture());
        assertEquals("A", captor.getValue());
    }

    @Test
    void save() {
        when(repository.save("B")).thenReturn("C");
        when(mapper.map("C")).thenReturn("D");
        when(mapper.reversMap("A")).thenReturn("B");
        String string = sut.save("A");
        assertEquals("D", string);
    }

    @Test
    void saveCalledMapperReversMapAndMap() {
        when(repository.save(any())).thenReturn("B");
        sut.save("A");
        verify(mapper).reversMap(anyString());
        verify(mapper).map(anyString());
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void deleteCalledRepository() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        when(mapper.reversMap(anyString())).thenReturn("A");
        sut.delete("B");
        verify(repository).delete(captor.capture());
        assertEquals("A", captor.getValue());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deleteCalledMapperReversMap() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        sut.delete("A");
        verify(mapper).reversMap(captor.capture());
        assertEquals("A", captor.getValue());
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void deleteByIdCalledRepository() {
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        sut.deleteById(1L);
        verify(repository).deleteById(captor.capture());
        assertEquals(1L, captor.getValue());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deleteByIdNotCalledMapper() {
        sut.deleteById(1L);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void getRepository() {
        assertSame(repository, sut.getRepository());
    }

    @Test
    void getMapper() {
        assertSame(mapper, sut.getMapper());
    }

}
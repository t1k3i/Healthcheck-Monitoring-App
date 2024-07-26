package com.dinit.healthcheck.services;

import com.dinit.healthcheck.dtos.UrlAddDto;
import com.dinit.healthcheck.dtos.UrlGetDto;
import com.dinit.healthcheck.dtos.UrlUpdateDto;
import com.dinit.healthcheck.exceptions.conflict.DisplayNameConflictException;
import com.dinit.healthcheck.exceptions.conflict.UrlConflictException;
import com.dinit.healthcheck.exceptions.notfound.UrlNotFoundException;
import com.dinit.healthcheck.models.URLInfo;
import com.dinit.healthcheck.repositories.UrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @Test
    void testGetUrls() {
        //Arrange
        List<URLInfo> urlInfos =
                List.of(new URLInfo("http://example.com", "Example", 10),
                        new URLInfo("http://example2.com", "Example2", 10));

        when(urlRepository.findAllOrderByHealthyDescThenById()).thenReturn(urlInfos);

        //Act
        List<UrlGetDto> list = urlService.getUrls(true);

        //Assert
        Assertions.assertEquals(2, list.size());

    }

    @Test
    void testGetUrl_Successful() {
        //Arrange
        URLInfo newMockUrlInfo = new URLInfo("http://example.com","Example", 10);
        when(urlRepository.findById(1L)).thenReturn(Optional.of(newMockUrlInfo));

        //Act
        UrlGetDto result = urlService.getUrl(1L);

        //Assert
        assertEquals("","http://example.com", result.getUrl());
        assertEquals("","Example", result.getDisplayName());
    }

    @Test
    void testGetUrl_whenExceptionThrown_thenAssertionSucceeds() {
        //Arrange / Act
        Exception exception = assertThrows(UrlNotFoundException.class, () -> {
            urlService.getUrl(5L);
        });

        String expectedMessage = "Url not found";
        String actualMessage = exception.getMessage();

        //Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteUrl_Successful() {
        //Arrange
        when(urlRepository.existsById(1L)).thenReturn(true);

        //Act
        urlService.deleteUrl(1L);

        //Assert
        verify(urlRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUrl_Fail() {
        //Arrange
        when(urlRepository.existsById(1L)).thenReturn(false);

        //Act
        Exception exception = assertThrows(UrlNotFoundException.class, () -> {
            urlService.deleteUrl(1L);
        });

        String expectedMessage = "Url not found";
        String actualMessage = exception.getMessage();

        //Assert
        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testAddUrlInfo_Successful() {
        //Arrange
        UrlAddDto urlAddDto = new UrlAddDto("http://example.com", "Example", 10);

        when(urlRepository.findByDisplayName(any())).thenReturn(Optional.empty());
        when(urlRepository.findByUrl(any())).thenReturn(Optional.empty());

        //Act
        urlService.addUrlInfo(urlAddDto);

        //Assert
        verify(urlRepository, times(1)).findByUrl(anyString());
        verify(urlRepository, times(1)).findByDisplayName(anyString());
        verify(urlRepository, times(1)).save(any());
    }

    @Test
    void testAddUrlInfo_Fail1() {
        UrlAddDto urlAddDto = new UrlAddDto("http://example.com", "Example", 10);
        URLInfo url = UrlAddDto.toEntity(urlAddDto);

        when(urlRepository.findByUrl(anyString())).thenReturn(Optional.of(url));

        Exception exception = assertThrows(UrlConflictException.class, () -> {
            urlService.addUrlInfo(urlAddDto);
        });

        String expectedMessage = "Url already exists";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testAddUrlInfo_Fail2() {
        UrlAddDto urlAddDto = new UrlAddDto("http://example.com", "Example", 10);
        URLInfo url = UrlAddDto.toEntity(urlAddDto);

        when(urlRepository.findByUrl(anyString())).thenReturn(Optional.empty());
        when(urlRepository.findByDisplayName(anyString())).thenReturn(Optional.of(url));

        Exception exception = assertThrows(DisplayNameConflictException.class, () -> {
            urlService.addUrlInfo(urlAddDto);
        });

        String expectedMessage = "Display name already exists";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testUpdateDisplayName_Success() {
        // Arrange
        Long urlId = 1L;
        UrlUpdateDto newUrlInfo = new UrlUpdateDto("New Display Name", "http://newurl.com", null);
        URLInfo urlInfo = new URLInfo("http://example.com", "Example", 10);

        when(urlRepository.findById(urlId)).thenReturn(Optional.of(urlInfo));
        when(urlRepository.findByDisplayName(any())).thenReturn(Optional.empty());
        when(urlRepository.findByUrl(any())).thenReturn(Optional.empty());

        // Act
        urlService.updateDisplayName(urlId, newUrlInfo);

        // Assert
        Assertions.assertEquals("New Display Name", urlInfo.getDisplayName());
        Assertions.assertEquals("http://newurl.com", urlInfo.getUrl());
    }

    @Test
    void testUpdateDisplayName_Fail1() {
        // Arrange
        Long urlId = 1L;
        UrlUpdateDto newUrlInfo = new UrlUpdateDto("New Display Name", "http://newurl.com", null);

        when(urlRepository.findById(urlId)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(UrlNotFoundException.class, () -> {
            urlService.updateDisplayName(urlId, newUrlInfo);
        });

        String expectedMessage = "Url not found";
        String actualMessage = exception.getMessage();

        // Assert
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testUpdateDisplayName_DisplayNameConflict() {
        // Arrange
        Long urlId = 1L;
        UrlUpdateDto newUrlInfo = new UrlUpdateDto("http://newurl.com", "New Display Name", null);
        URLInfo urlInfo = new URLInfo("http://example.com", "Example", 10);

        when(urlRepository.findById(urlId)).thenReturn(Optional.of(urlInfo));
        when(urlRepository.findByDisplayName(newUrlInfo.getDisplayName())).thenReturn(Optional.of(urlInfo));

        // Act & Assert
        Exception exception = assertThrows(DisplayNameConflictException.class, () -> {
            urlService.updateDisplayName(urlId, newUrlInfo);
        });

        String expectedMessage = "Display name already exists";
        String actualMessage = exception.getMessage();

        // Assert
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testUpdateDisplayName_UrlConflict() {
        // Arrange
        Long urlId = 1L;
        UrlUpdateDto newUrlInfo = new UrlUpdateDto("http://newurl.com", "New Display Name", null);
        URLInfo urlInfo = new URLInfo("http://example.com", "Example", 10);

        when(urlRepository.findById(urlId)).thenReturn(Optional.of(urlInfo));
        when(urlRepository.findByDisplayName(newUrlInfo.getDisplayName())).thenReturn(Optional.empty());
        when(urlRepository.findByUrl(newUrlInfo.getUrl())).thenReturn(Optional.of(urlInfo));

        // Act & Assert
        Exception exception = assertThrows(UrlConflictException.class, () -> {
            urlService.updateDisplayName(urlId, newUrlInfo);
        });

        String expectedMessage = "Url already exists";
        String actualMessage = exception.getMessage();

        // Assert
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

}

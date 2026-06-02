package com.example.demo;

import com.example.demo.enums.PhotoType;
import com.example.demo.service.MinioService;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MinioServiceTest {

    @Mock
    private MinioClient minioClient;

    @InjectMocks
    private MinioService minioService;

    private final String BUCKET_NAME = "test-bucket";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(minioService, "bucketName", BUCKET_NAME);
    }

    @Test
    void uploadFile_ShouldReturnObjectKey_WhenSuccess() throws Exception {
        // Arrange
        PhotoType folder = PhotoType.PASSPORT;
        String originalFilename = "test-image.png";
        byte[] content = "test data".getBytes();
        MockMultipartFile mockFile = new MockMultipartFile("file", originalFilename, "image/png", content);

        // Act
        String objectKey = minioService.uploadFile(mockFile, folder);

        // Assert
        assertNotNull(objectKey);
        assertTrue(objectKey.startsWith(folder + "/"));
        assertTrue(objectKey.endsWith(".png"));

        ArgumentCaptor<PutObjectArgs> captor = ArgumentCaptor.forClass(PutObjectArgs.class);
        verify(minioClient, times(1)).putObject(captor.capture());

        PutObjectArgs args = captor.getValue();
        assertEquals(BUCKET_NAME, args.bucket());
        assertEquals(objectKey, args.object());
        assertEquals("image/png", args.contentType());
    }

    @Test
    void uploadFile_ShouldThrowRuntimeException_WhenMinioFails() throws Exception {
        // Arrange
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "data".getBytes());
        when(minioClient.putObject(any(PutObjectArgs.class))).thenThrow(new RuntimeException("MinIO error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            minioService.uploadFile(mockFile, PhotoType.AVATAR);
        });
        assertEquals("Error uploading file to MinIO", exception.getMessage());
    }

    @Test
    void getFile_ShouldReturnInputStream_WhenSuccess() throws Exception {
        // Arrange
        String objectKey = "passports/123.jpg";
        InputStream expectedStream = new ByteArrayInputStream("data".getBytes());

        // MinioClient getObject returns GetObjectResponse, which extends InputStream
        GetObjectResponse getObjectResponse = mock(GetObjectResponse.class);

        // We configure minioClient to return our mock
        doReturn(getObjectResponse).when(minioClient).getObject(any(GetObjectArgs.class));

        // Act
        String resultStream = minioService.getFile(objectKey);

        // Assert
        assertNotNull(resultStream);

        ArgumentCaptor<GetObjectArgs> captor = ArgumentCaptor.forClass(GetObjectArgs.class);
        verify(minioClient, times(1)).getObject(captor.capture());

        GetObjectArgs args = captor.getValue();
        assertEquals(BUCKET_NAME, args.bucket());
        assertEquals(objectKey, args.object());
    }

    @Test
    void getFile_ShouldThrowRuntimeException_WhenMinioFails() throws Exception {
        // Arrange
        when(minioClient.getObject(any(GetObjectArgs.class))).thenThrow(new RuntimeException("MinIO error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            minioService.getFile("invalid-key");
        });
        assertEquals("Error getting file from MinIO", exception.getMessage());
    }

    @Test
    void deleteFile_ShouldCompleteSuccessfully_WhenNoErrors() throws Exception {
        // Arrange
        String objectKey = "avatars/456.png";

        // Act
        assertDoesNotThrow(() -> minioService.deleteFile(objectKey));

        // Assert
        ArgumentCaptor<RemoveObjectArgs> captor = ArgumentCaptor.forClass(RemoveObjectArgs.class);
        verify(minioClient, times(1)).removeObject(captor.capture());

        RemoveObjectArgs args = captor.getValue();
        assertEquals(BUCKET_NAME, args.bucket());
        assertEquals(objectKey, args.object());
    }

    @Test
    void deleteFile_ShouldThrowRuntimeException_WhenMinioFails() throws Exception {
        // Arrange
        doThrow(new RuntimeException("MinIO error")).when(minioClient).removeObject(any(RemoveObjectArgs.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            minioService.deleteFile("fail-key");
        });
        assertEquals("Error deleting file from MinIO", exception.getMessage());
    }
}

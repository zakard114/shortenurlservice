package com.heeju.shortenurlservice.application;

import com.heeju.shortenurlservice.presentation.ShortenUrlCreateRequestDto;
import com.heeju.shortenurlservice.presentation.ShortenUrlCreateResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimpleShortenUrlServiceTest {

    @Autowired
    private SimpleShortenUrlService simpleShortenUrlService;

    @Test
    @DisplayName("The original URL should be searched after shortening the URL")
    void shortenUrlAddTest() {
        String expectedOriginalUrl = "https://www.hanbit.co.kr/";
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(expectedOriginalUrl);

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        String shortenUrlKey = shortenUrlCreateResponseDto.getShortenUrlKey();

        String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

        assertTrue(originalUrl.equals(expectedOriginalUrl));
    }

    @Test
    @DisplayName("shortenUrlKey")
    void generateShortenUrl(){
        String expectedOriginalUrl = "https://www.google.com";
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(expectedOriginalUrl);

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        String shortenUrlKey = shortenUrlCreateResponseDto.getShortenUrlKey();

        String originalUrl = null;
        try {
            originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);
        } catch (Exception e){
            System.out.println("Error occurred: "+e.getMessage());
        }
        assertTrue(originalUrl.equals(expectedOriginalUrl));

        // Code to find originalUrl using a try block. In the process, the task of extracting shortenUrl that matches
        // shortenUrlKey is performed. If shortenUrl is not found in the shortenUrlRepository, a NotFoundShortenUrlException
        // instance that inherits RuntimeException is created in the catch block to handle the exception.

        // In addition to this, there is a way to handle validity and exceptions using Optional. By using Optional,
        // instead of returning null when there is no value in the Collection, the empty state is explicitly expressed.
        // This allows you to prevent NullPointerException and write safe code.
        // However, in this case, it is necessary to change part of the service code, and the result is getOriginalUrlByShortenUrlKey2 below.

        // And the client code (test code) that receives Optional as a return value is as follows.

        Optional<String> originalUrlInOptional;

        // getOriginalUrlByShortenUrlKey2
        originalUrlInOptional = simpleShortenUrlService.getOriginalUrlByShortenUrlKey2(shortenUrlKey);
        if(originalUrlInOptional.isPresent()){
            originalUrl = originalUrlInOptional.get();
        } else {
            System.out.println("Url not found");
        }
        assertTrue(originalUrl.equals(expectedOriginalUrl));

        // In the code above, the .getOriginalUrlByShortenUrlKey2 method returns originalUrl wrapped in Optional.
        // In the process, if shortenUrl does not exist in the shortenUrlRepository, Optional.empty() is returned instead of null.
        // In the test code, the returned Optional object is received as originalUrl2 and its presence is determined.
        // If you want to return an empty value as a String, you can create the constructor of NotFoundShortenUrlException
        // as follows and have the client code receive a string error message that you wrote and sent.

        // public class NotFoundShortenUrlException extends RuntimeException{
        //    public NotFoundShortenUrlException(String message){
        //        super(message);
        //    }
        }

}

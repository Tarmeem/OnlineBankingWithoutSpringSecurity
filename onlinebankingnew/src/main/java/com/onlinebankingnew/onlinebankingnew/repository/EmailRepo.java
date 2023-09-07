package com.onlinebankingnew.onlinebankingnew.repository;

import org.springframework.web.multipart.MultipartFile;

public interface    EmailRepo {

   
    String sendMail(MultipartFile[] file, String to,String[] cc, String subject, String body);

}

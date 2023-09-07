package com.onlinebankingnew.onlinebankingnew.repository;

public interface    EmailRepo {

   
    String sendMail( String to,String[] cc, String subject, String body);

}

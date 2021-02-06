package com.example.dubboExample.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


public class ServerMain {

    public static void main(String[] args) throws IOException {
        System.out.println("start");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("server.xml");
        ctx.start();
        System.in.read();
    }

}

package com.cjs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class App {
    public static void main( String[] args )
    {
//        String str = "1,2,3,4";
//        List<String> stringList = new ArrayList<>();
////        Collections.addAll(stringList, str.split(","));
//        stringList.addAll(Arrays.asList(str.split(",")));
//        System.out.println(stringList.get(1));
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }
}

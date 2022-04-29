package com.xqls.buoy_station;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xqls.buoy_station.mapper")
public class BuoyStationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuoyStationApplication.class, args);
    }

}

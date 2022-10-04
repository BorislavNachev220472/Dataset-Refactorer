package com.example.powerbidatasetprep;

import com.example.powerbidatasetprep.services.DataSetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class ConsoleRunner implements CommandLineRunner {

    public final DataSetService dataSetService;

    public ConsoleRunner(DataSetService dataSetService) {
        this.dataSetService = dataSetService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("HAH");
        dataSetService.getAllPopulationOfCountry();

    }
}

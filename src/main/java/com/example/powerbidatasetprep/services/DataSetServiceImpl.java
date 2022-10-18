package com.example.powerbidatasetprep.services;

import com.example.powerbidatasetprep.models.DataSet;
import com.example.powerbidatasetprep.repository.DataSetRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DataSetServiceImpl implements DataSetService {

    private static final String CSV_PATH = "D:\\Projects\\PowerBiDatasetPrep\\src\\main\\resources\\csv\\";
    public final DataSetRepository dataSetRepository;

    public DataSetServiceImpl(DataSetRepository dataSetRepository) {
        this.dataSetRepository = dataSetRepository;
    }


    @Override
    public void getAllPopulationOfCountry() {
        List<DataSet> totalPopulationRaw = dataSetRepository.getTotalPopulation();
        List<DataSet> largestCityPopulationRaw = dataSetRepository.getLargestCityPopulation();
        List<DataSet> urbanPopulationRaw = dataSetRepository.getUrbanPopulation();
        List<DataSet> urbanSquareMetersRaw = dataSetRepository.getUrbanSquareMeters();
        List<DataSet> countrySquareMetersRaw = dataSetRepository.getCountrySquareMeters();
        List<DataSet> ruralPopulationRaw = dataSetRepository.getRuralPopulation();
        List<DataSet> ruralSquareMetersRaw = dataSetRepository.getRuralSquareMeters();
        String[] columns = {"Country", "Year", "Total Population", "Largest City Population", "Urban Population", "Urban Square Meters"
                , "Country Square Meters", "Rural Population", "Rural Square Meters", "Population Increase"};

        Map<String, Map<Integer, Double>> totalPopulation = generateMap(totalPopulationRaw);
        Map<String, Map<Integer, Double>> largestCity = generateMap(largestCityPopulationRaw);
        Map<String, Map<Integer, Double>> urbanPopulation = generateMap(urbanPopulationRaw);
        Map<String, Map<Integer, Double>> urbanSquareMeters = generateMap(urbanSquareMetersRaw);
        Map<String, Map<Integer, Double>> countrySquareMeters = generateMap(countrySquareMetersRaw);
        Map<String, Map<Integer, Double>> ruralPopulation = generateMap(ruralPopulationRaw);
        Map<String, Map<Integer, Double>> ruralSquareMeters = generateMap(ruralSquareMetersRaw);

        List<Map<String, Map<Integer, Double>>> maps = new ArrayList<>();
        maps.add(totalPopulation);
        maps.add(largestCity);
        maps.add(urbanPopulation);
        maps.add(urbanSquareMeters);
        maps.add(countrySquareMeters);
        maps.add(ruralPopulation);
        maps.add(ruralSquareMeters);

        try {
            PrintWriter writer = new PrintWriter(CSV_PATH + "dataset" +
                    (String.format("%d_%d_%d_%d", LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getMinute()))
                    + ".csv");

            StringBuilder columnsStr = new StringBuilder();
            for (int i = 0; i < columns.length; i++) {
                if (i == columns.length - 1) {
                    columnsStr.append(columns[i]);
                    break;
                }
                columnsStr.append(columns[i]).append(", ");
            }
            writer.println(columnsStr);

            Map<String, Double> lastPopulation = new HashMap<>();
            for (int i = 1960; i <= 2021; i++) {

                Set<String> countries = maps.get(0).keySet();

                for (String currCountry : countries) {
                    StringBuilder line = new StringBuilder(currCountry.replaceAll(", ", " ") + ", " + i + ", ");
                    double currentPopulation = totalPopulation.get(currCountry).get(i);
                    for (int j = 0; j < maps.size(); j++) {
                        Double currentValue = maps.get(j).get(currCountry).get(i);
                        if (j == maps.size() - 1) {
                            line.append(currentValue);
                            break;
                        }
                        line.append(currentValue).append(", ");
                    }
                    if (!lastPopulation.containsKey(currCountry)) {
                        lastPopulation.put(currCountry, 0.0);
                    }
                    double change = currentPopulation - lastPopulation.get(currCountry);
                    if (lastPopulation.get(currCountry) == 0) {
                        change = currentPopulation;
                    }
                    lastPopulation.put(currCountry, currentPopulation);
                    line.append(", ").append(change);
                    writer.println(line);
                }

            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private Map<String, Map<Integer, Double>> generateMap(List<DataSet> population) {
        Map<String, Map<Integer, Double>> values = new HashMap<>();
        for (DataSet currDataset : population) {
            Map<Integer, Double> curr = new HashMap<>();

            for (int j = 1960; j <= 2021; j++) {
                try {
                    String fieldName = "year" + j;
                    Field field = currDataset.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Double currYearValue = (Double) field.get(currDataset);
                    curr.put(j, currYearValue);
                } catch (NoSuchFieldException | IllegalAccessException ignored) {
                    System.out.println(ignored.getMessage());
                }
            }

            values.put(currDataset.getCountryCode(), curr);
        }
        return values;
    }
}

package com.example.powerbidatasetprep.repository;

import com.example.powerbidatasetprep.models.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSetRepository extends JpaRepository<DataSet, Long> {
    String DATASET_COUNTRY = "'Austria', 'Belgium', 'Bulgaria', 'Croatia', 'Cyprus', 'Czech Republic', 'Denmark', 'Estonia'," +
            " 'Finland', 'France', 'Germany', 'Greece', 'Hungary', 'Ireland', 'Italy', " +
            "'Latvia', 'Lithuania', 'Luxembourg', 'Malta', 'Netherlands', 'Poland', 'Portugal', 'Romania', 'Slovak Republic', 'Slovenia', 'Spain', 'Sweden'";

    @Query(value = "SELECT * FROM powerbi_blocka.dataset WHERE  dataset.country_code IN ( " + DATASET_COUNTRY + " ) AND indicator_code LIKE '%Population, total%';", nativeQuery = true)
    List<DataSet> getTotalPopulation();

    @Query(value = "SELECT * FROM powerbi_blocka.dataset WHERE dataset.country_code IN ( " + DATASET_COUNTRY + " ) AND indicator_code LIKE '%Population in largest city%';", nativeQuery = true)
    List<DataSet> getLargestCityPopulation();

    @Query(value = "SELECT * FROM powerbi_blocka.dataset WHERE dataset.country_code IN ( " + DATASET_COUNTRY + " ) AND indicator_code LIKE 'Urban population';", nativeQuery = true)
    List<DataSet> getUrbanPopulation();

    @Query(value = "SELECT * FROM powerbi_blocka.dataset WHERE dataset.country_code IN ( " + DATASET_COUNTRY + " ) AND indicator_code LIKE 'Urban land area (sq. km)';", nativeQuery = true)
    List<DataSet> getUrbanSquareMeters();

    @Query(value = "SELECT * FROM powerbi_blocka.dataset WHERE dataset.country_code IN ( " + DATASET_COUNTRY + " ) AND indicator_code LIKE 'Surface area (sq. km)';", nativeQuery = true)
    List<DataSet> getCountrySquareMeters();

    @Query(value = "SELECT * FROM powerbi_blocka.dataset WHERE dataset.country_code IN ( " + DATASET_COUNTRY + " ) AND indicator_code LIKE 'Rural population';", nativeQuery = true)
    List<DataSet> getRuralPopulation();

    @Query(value = "SELECT * FROM powerbi_blocka.dataset WHERE dataset.country_code IN ( " + DATASET_COUNTRY + " ) AND indicator_code LIKE 'Rural land area (sq. km)';", nativeQuery = true)
    List<DataSet> getRuralSquareMeters();


}

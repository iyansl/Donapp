package com.iyan.donapp.services.provinces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProvincesApi {

    private final String API_URL = "https://data.opendatasoft.com/api/explore/v2.1/catalog/datasets/georef-spain-provincia@public/records?select=prov_name&limit=100";

    @SuppressWarnings("unchecked")
	public List<String> getAll() {
        RestTemplate restTemplate = new RestTemplate();
        List<String> provinces = new ArrayList<>();
        try {
            LinkedHashMap<String, Object> response = restTemplate.getForObject(API_URL, LinkedHashMap.class);
            List<LinkedHashMap<String, Object>> records = (List<LinkedHashMap<String, Object>>) response.get("results");
            for (LinkedHashMap<String, Object> record : records) {
                provinces.add((String) record.get("prov_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(provinces);
        return provinces;
    }
}

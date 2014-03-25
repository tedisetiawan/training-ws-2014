/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.muhardin.endy.training.ws.aplikasi.absen.rest.client;

import com.muhardin.endy.training.ws.aplikasi.absen.Karyawan;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author endy
 */
public class AbsenRestClient {
    private static final String SERVER_URL = "http://localhost:8080/aplikasi-absen-rest/api/rest";
    private final RestTemplate restTemplate = new RestTemplate();

    public AbsenRestClient() {
        restTemplate.setErrorHandler(new AbsenRestClientErrorHandler());
    }
    
    public Karyawan cariKaryawanByNip(Integer nip){
        String url = SERVER_URL+"/karyawan/"+nip;
        Karyawan k = restTemplate
                .getForObject(url, Karyawan.class, new HashMap<Object, Object>());
        return k;
    }
    
    public List<Karyawan> semuaKaryawan(){
        ParameterizedTypeReference<List<Karyawan>> typeRef 
                = new ParameterizedTypeReference<List<Karyawan>>() {};
        
        ResponseEntity<List<Karyawan>> response = restTemplate
         .exchange(SERVER_URL+"/karyawan/", 
                 HttpMethod.GET, null, 
                 typeRef);
        
        System.out.println("Response Status : "+response.getStatusCode());
        
        return response.getBody();
    }    

    public void simpan(Karyawan k) {
        String url = SERVER_URL + "/karyawan/";
        restTemplate.postForObject(url, k, Object.class);
    }
    
    public void update(Karyawan k){
        String url = SERVER_URL + "/karyawan/{nip}";
        restTemplate.put(url, k, k.getNip());
    }
    
    public void delete(Karyawan k){
        String url = SERVER_URL + "/karyawan/{nip}";
        
        Map<String, Object> pathVariable = new HashMap<>();
        pathVariable.put("nip", k.getNip());
        
        restTemplate.delete(url, pathVariable);
    }
}

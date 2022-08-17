package com.example.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.security.SignatureException;
import java.util.Map;

class HMACCheckerTest {

    @Test
    void checkHMAC() {
    }

    @Test
    void calculateHMAC() {
//        String data = "{\n" +
//                "    \"lastName\": \"Ермаков\",\n" +
//                "    \"firstName\": \"Сергей\",\n" +
//                "    \"patronymic\": \"Викторович\",\n" +
//                "    \"inn\": \"271700016569\"\n" +
//                "}";
        ObjectMapper mapper = new ObjectMapper();

        try {
//            Mapper<IndividualDataWrapper> individualDataWrapperMapper = new Mapper<>();
//            IndividualDataWrapper individualDataWrapper = individualDataWrapperMapper.jsonToObject(data, IndividualDataWrapper.class);

//            Map<String, String> map = mapper.convertValue(individualDataWrapper.getIndividualData(), Map.class);
            Map<String, String> map = Map.of(
                    "clientId", "111",
                    "date", "1654253084"
                    );
            System.out.println( HMACChecker.calculateHMAC(map, "qwerty") );
//            System.out.println(Hex.encode(HMACChecker.calculateHMAC(map, "qwerty").getBytes()) );
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
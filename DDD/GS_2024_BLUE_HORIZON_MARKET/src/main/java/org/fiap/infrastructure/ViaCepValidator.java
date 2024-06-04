package org.fiap.infrastructure;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class ViaCepValidator {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/";
    private static final Pattern CEP_PATTERN = Pattern.compile("\\d{5}-?\\d{3}");

    public static boolean isCepValid(String cep) {
        if (!CEP_PATTERN.matcher(cep).matches()) {
            return false;
        }

        try {
            URL url = new URL(VIA_CEP_URL + cep + "/json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            return !jsonResponse.has("erro");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

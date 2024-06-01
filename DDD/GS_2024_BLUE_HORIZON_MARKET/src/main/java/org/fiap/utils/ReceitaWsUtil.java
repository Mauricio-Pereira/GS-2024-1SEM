package org.fiap.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ReceitaWsUtil {

    private static final String RECEITA_WS_URL = "https://www.receitaws.com.br/v1/cnpj/";

    public static boolean isEmpresaAtiva(String cnpj) throws Exception {
        String url = RECEITA_WS_URL + cnpj;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            String situacao = jsonResponse.getString("situacao");

            return "ATIVA".equalsIgnoreCase(situacao);
        } else {
            throw new Exception("Erro ao consultar o CNPJ: " + responseCode);
        }
    }

    public static void main(String[] args) {
        try {
            String cnpj = "21011616000138";
            boolean ativa = isEmpresaAtiva(cnpj);
            if (ativa) {
                System.out.println("Empresa ativa");
            } else {
                System.out.println("Empresa inativa");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

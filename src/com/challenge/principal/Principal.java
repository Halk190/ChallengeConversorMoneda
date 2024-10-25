package com.challenge.principal;

import com.challenge.models.Conversion;
import com.challenge.models.ConversionResponse;
import com.challenge.models.InformacionDivisasRecord;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();
        HttpClient client = HttpClient.newHttpClient();
        List<Conversion> historial = new ArrayList<>(); // Lista para guardar el historial

        // Bucle para mantener el menú activo hasta que el usuario elija salir
        while (true) {
            System.out.println("*****************************"+
                    "\nBienvenidos al conversor de monedas");
            System.out.println("\n1. Cambio de Dólar a Peso Argentino\n2. Cambio de Peso Argentino a Dólar\n" +
                    "3. Cambio de Dólar a Real Brasileño\n4. Cambio de Real Brasileño a Dólar\n" +
                    "5. Cambio de Dólar a Peso Colombiano\n6. Cambio de Peso Colombiano a Dólar" +
                    "\n7. Ver historial de conversiones\n8. Salir\n" +
                    "\n**************************");

            System.out.print("Seleccione una opción: ");
            int opcionSeleccionada = lectura.nextInt();

            if (opcionSeleccionada == 8) {
                System.out.println("Saliendo del conversor. ¡Hasta luego!");
                break;  // Finaliza el programa si elige la opción 7
            }
            if (opcionSeleccionada == 7) { // Opción para ver el historial
                System.out.println("Historial de conversiones:");
                for (Conversion c : historial) {
                    System.out.println(c);
                }
                continue; // Volver al menú
            }
            if (opcionSeleccionada < 1 || opcionSeleccionada > 7) {
                System.out.println("Opción no válida, por favor intente de nuevo.");
                continue;
            }

            System.out.print("Ingrese el valor que desea convertir: ");
            double valor = lectura.nextDouble();

            String base = "";
            String objetivo = "";


            switch (opcionSeleccionada) {
                case 1:
                    base = "USD";
                    objetivo = "ARS";
                    break;
                case 2:
                    base = "ARS";
                    objetivo = "USD";
                    break;
                case 3:
                    base = "USD";
                    objetivo = "BRL";
                    break;
                case 4:
                    base = "BRL";
                    objetivo = "USD";
                    break;
                case 5:
                    base = "USD";
                    objetivo = "COP";
                    break;
                case 6:
                    base = "COP";
                    objetivo = "USD";
                    break;
            }

            String apiKey = "8e9ed693f5fd1470e6fbfc85";  // Tu API key
            String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + base + "/" + objetivo + "/" + valor;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.
                    send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            // Manejo de errores en caso de que la respuesta no sea válida
            if (json.contains("error")) {
                System.out.println("Error en la conversión: " + json);
                continue;
            }

            InformacionDivisasRecord infoRecord = gson.fromJson(json, InformacionDivisasRecord.class);
            ConversionResponse conversionResponse = new ConversionResponse(infoRecord);

            // Almacenar la conversión en el historial
            Conversion conversion = new Conversion(base, objetivo, valor, conversionResponse.getConversion_result());
            historial.add(conversion);
            System.out.println("El valor " + valor + " [" + base +
                    "] corresponde al valor final de =>>> " + conversionResponse.getCalculoConversion(valor) + " [" +
                    objetivo + "]");
        }

        lectura.close();
    }
}

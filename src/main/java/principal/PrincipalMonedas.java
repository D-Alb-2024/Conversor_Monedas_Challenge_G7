package principal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.DatoMoneda;
import model.Moneda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PrincipalMonedas {

    private final String API_KEY = "cd09fe9aa94d86cb4c3257ee"; // Reemplaza con tu clave API
    private final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private final List<String> historial = new ArrayList<>();

    public void resultado() throws IOException, InterruptedException {

        Scanner lectura = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        boolean continuar = true;
        while (continuar) {
            try {
                System.out.println("""
                        ****************************************
                        ******** Sea bienvenido(a) a mi *********
                        ***********CONVERSOR DE MONEDAS**********
                        
                        1. Soles ----> Dólar
                        2. Soles ----> Euro
                        3. Dólar ----> Soles
                        4. Euro -----> Soles
                        5. Dólar ----> Yuan
                        6. Soles ----> Peso argentino
                        7. Soles ----> Peso mexicano
                        8. Soles ----> Peso colombiano
                        9 .Mostrar Historial
                        10. SALIR
                        ******************************************
                        """);
                System.out.println("Elija una opción:");
                if (!lectura.hasNextInt()) {
                    throw new InputMismatchException("Debe ingresar un número válido.");
                }
                int opcion = lectura.nextInt();
                lectura.nextLine();
                if (opcion < 1 || opcion > 10) {
                    System.out.println("Opción fuera del rango permitido. Intente nuevamente.");
                    continue;
                }
                if (opcion == 10) {
                    continuar = false;
                    System.out.println("Gracias por usar la aplicación!!!");
                    break;
                }
                if (opcion == 9) {
                    mostrarHistorial();
                    continue;
                }
                System.out.println("Ingrese la cantidad a convertir: ");
                double cantidadInicial = lectura.nextDouble();
                lectura.nextLine();
                String monedaInicial = obtenerMonedaInicial(opcion);
                String monedaFinal = obtenerMonedaFinal(opcion);
                // Validación de monedas obtenidas
                if (monedaInicial == null || monedaFinal == null) {
                    System.out.println("Opción inválida. Intente nuevamente.");
                    System.out.println("");
                    continue;
                }
                // Construcción de la URL para consultar la API
                String URL_FINAL = BASE_URL + API_KEY + "/pair/" +
                        monedaInicial + "/" + monedaFinal;
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(URL_FINAL))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                // Procesar el JSON recibido de la API
                String json = response.body();
                DatoMoneda datoMoneda = gson.fromJson(json, DatoMoneda.class);
                Moneda moneda = new Moneda(datoMoneda, cantidadInicial);
                // Realizar la conversión y agregar al historial
                moneda.convertirMoneda();
                historial.add(moneda.toString());
                System.out.println("Operación exitosa!!!");
                System.out.println(moneda);
                System.out.println("");
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida: " + e.getMessage());
                System.out.println("");
                lectura.nextLine();
            }
        }
    }

    private void mostrarHistorial() {
        if (historial.isEmpty()) {
            System.out.println("No hay registro de operaciones realizadas.");
        } else {
            System.out.println("***** Historial de Conversiones *****");
            for (String registro : historial) {
                System.out.println(registro);
            }
        }
        System.out.println("");
    }
    // Método para obtener la moneda inicial según la opción seleccionada
    private String obtenerMonedaInicial(int opcion) {
        return switch (opcion) {
            case 1, 2, 6, 7, 8 -> "PEN"; // Soles
            case 3, 5 -> "USD";          // Dólar
            case 4 -> "EUR";             // Euro
            default -> null;
        };
    }
    // Método para obtener la moneda final según la opción seleccionada
    private String obtenerMonedaFinal(int opcion) {
        return switch (opcion) {
            case 1 -> "USD"; // Soles -> Dólar
            case 2 -> "EUR"; // Soles -> Euro
            case 3 -> "PEN"; // Dólar -> Soles
            case 4 -> "PEN"; // Euro -> Soles
            case 5 -> "CNY"; // Dólar -> Yuan
            case 6 -> "ARS"; // Soles -> Peso argentino
            case 7 -> "MXN"; // Soles -> Peso mexicano
            case 8 -> "COP"; // Soles -> Peso colombiano
            default -> null;
        };
    }

}

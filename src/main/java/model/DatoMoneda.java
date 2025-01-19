package model;

public record DatoMoneda(

    String base_code,
    String target_code,
    double conversion_rate

) {
}

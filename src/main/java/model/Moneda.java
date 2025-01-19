package model;

import java.text.DecimalFormat;

public class Moneda {

    private String base_code;
    private String target_code;
    private double conversion_rate;
    private double cantidadInicial;
    private double cantidadFinal;

    public Moneda(DatoMoneda datoMoneda, double cantidadInicial) {
        this.base_code = datoMoneda.base_code();
        this.target_code = datoMoneda.target_code();
        this.cantidadInicial = cantidadInicial;
        this.conversion_rate = datoMoneda.conversion_rate();
    }

    public String getBase_code() {
        return base_code;
    }

    public String getTarget_code() {
        return target_code;
    }

    public double getConversion_rate() {
        return conversion_rate;
    }

    public double getCantidadInicial() {
        return cantidadInicial;
    }

    public double getCantidadFinal() {
        return cantidadFinal;
    }

    public double convertirMoneda (){
        cantidadFinal = cantidadInicial*conversion_rate;
        return cantidadFinal;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String cantidadInicialConFormato = decimalFormat.format(cantidadInicial);
        String cantidadFinalConFormato = decimalFormat.format(cantidadFinal);
        return "Moneda Origen = '" + base_code + '\'' +
                ", Cantidad a Convertir= " + String.format("%.2f" ,cantidadInicial) +
                ", Moneda Destino = '" + target_code + '\'' +
                ", Tipo de cambio = " + String.format("%.4f", conversion_rate) + "\n"+
                "Convertimos " + cantidadInicialConFormato + "[" +base_code +
                "] a " + cantidadFinalConFormato + "[" + target_code + "]";
    }
}

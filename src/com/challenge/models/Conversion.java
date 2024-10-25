package com.challenge.models;

public class Conversion {
    private final String base;
    private final String objetivo;
    private final double valorOriginal;
    private final double valorConvertido;

    public Conversion(String base, String objetivo, double valorOriginal, double valorConvertido) {
        this.base = base;
        this.objetivo = objetivo;
        this.valorOriginal = valorOriginal;
        this.valorConvertido = valorConvertido;
    }

    @Override
    public String toString() {
        return "Conversión: " + valorOriginal + " [" + base + "] = " + valorConvertido + " [" + objetivo + "]";
    }
}

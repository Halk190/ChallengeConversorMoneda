package com.challenge.models;

public class ConversionResponse{
    private String base_code;
    private String target_code;
    private double conversion_rate;
    private double conversion_result;
    private double conversionCalculate;

    public ConversionResponse(InformacionDivisasRecord infoRecord){
        this.base_code =infoRecord.base_code();
        this.target_code = infoRecord.target_code();
        this.conversion_rate = infoRecord.conversion_rate();
        this.conversion_result = infoRecord.conversion_result();
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

    public double getConversion_result() {
        return this.conversion_result;
    }

    public double getCalculoConversion(double value){
        return this.conversionCalculate = conversion_rate * value;
    }
}

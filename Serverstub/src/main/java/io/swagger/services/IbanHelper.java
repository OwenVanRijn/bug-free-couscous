package io.swagger.services;

public class IbanHelper {
    static public boolean validIban(String iban){
        return iban.matches("(NL)\\d{2}(INHO0)\\d{9}$");
    }
}

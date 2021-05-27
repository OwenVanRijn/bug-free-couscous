package io.swagger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class IbanHelper {

    @Autowired
    private BankaccountService bankaccountService;

    static public boolean validIban(String iban){
        return iban.matches("(NL)\\d{2}(INHO0)\\d{9}$");
    }

    static public String generateIban(){
        return String.format("NL%02dINHO0%09d", ThreadLocalRandom.current().nextInt(99), ThreadLocalRandom.current().nextInt(999999999));
    }

    public String generateUnusedIban(){
        while (true){
            String iban = IbanHelper.generateIban();
            if (!bankaccountService.getBankaccountByIBANSafe(iban).isPresent())
                return iban;
        }
    }
}

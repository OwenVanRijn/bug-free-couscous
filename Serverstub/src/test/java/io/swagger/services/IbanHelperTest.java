package io.swagger.services;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class IbanHelperTest {

    @Test
    public void validateInvalidIban() {
        String[] bogusIbans = {"thisisnotvalid!", "NL", "NL0000000000000000", "0000INHO0000000000", "000000000000000000", "NLAAINHOAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "NL01INHO1000000001"};

        for (String s : bogusIbans){
            assert (!IbanHelper.validIban(s));
        }
    }

    @Test
    public void validateValidIban() {
        String[] validIbans = {"NL01INHO0000000001", "NL01INHO0999999999", "NL99INHO0999999999"};

        for (String s : validIbans){
            assert (IbanHelper.validIban(s));
        }
    }

    @Test
    public void validateGeneratedIbans() {
        for (int i = 0; i < 100; i++){
            assert (IbanHelper.validIban(IbanHelper.generateIban()));
        }
    }
}
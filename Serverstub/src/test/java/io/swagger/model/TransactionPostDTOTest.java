package io.swagger.model;

import io.swagger.dto.TransactionDTO;
import io.swagger.dto.TransactionPostDTO;
import io.swagger.exceptions.RestException;
import io.swagger.services.IbanHelper;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TransactionPostDTOTest {

    @Test
    void toTransaction() throws Exception {
        TransactionPostDTO dto = new TransactionPostDTO();
        assertThrows(
                RestException.class,
                () -> dto.toTransaction(new User())
        );

        dto.ibANFrom(IbanHelper.generateIban());
        dto.ibANTo(IbanHelper.generateIban());
        dto.amount(10.00);

        dto.toTransaction(new User());
    }

    @Test
    void getAmountLong() throws Exception {
        TransactionPostDTO dto = new TransactionPostDTO();
        dto.setAmount(10.00);
        assert dto.getAmountLong() == 1000;
    }
}
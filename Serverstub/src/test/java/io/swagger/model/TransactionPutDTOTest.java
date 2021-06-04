package io.swagger.model;

import io.swagger.dto.TransactionPutDTO;
import io.swagger.exceptions.RestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionPutDTOTest {

    @Test
    void toPostDto() throws Exception {
        TransactionPutDTO dto = new TransactionPutDTO();
        Transaction t = new Transaction();
        t.ibANFrom("a");
        t.ibANTo("b");
        t.amount(1L);

        assertThrows(
                RestException.class,
                dto::toPostDto
        );

        dto.fillEmpty(t);
        dto.toPostDto();
    }

    @Test
    void fillEmpty() {
        TransactionPutDTO dto = new TransactionPutDTO();
        Transaction t = new Transaction();
        t.ibANFrom("a");
        t.ibANTo("b");
        t.amount(1L);
        assert dto.isEmpty();
        dto.fillEmpty(t);
        assert dto.getIbanFrom().equals("a");
        assert dto.getIbanTo().equals("b");
        assert dto.getAmount() == 0.01;
    }

    @Test
    void isEmpty() {
        TransactionPutDTO dto = new TransactionPutDTO();
        assert dto.isEmpty();

        dto.setAmount(100.00);
        assert !dto.isEmpty();
    }
}
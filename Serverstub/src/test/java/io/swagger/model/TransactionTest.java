package io.swagger.model;

import io.swagger.exceptions.BadRequestException;
import io.swagger.exceptions.RestException;
import io.swagger.services.IbanHelper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: create TransactionPostDTO tests
// TODO: create TransactionPutDTO tests
class TransactionTest {

    @Test
    void toTransactionDTO() {
        Transaction a = new Transaction();
        a.type(Transaction.TypeEnum.TRANSACTION)
                .ibANFrom(IbanHelper.generateIban())
                .ibANTo(IbanHelper.generateIban())
                .amount(100L)
                .performedBy(new User());

        assert (a.toTransactionDTO() != null);
    }

    @Test
    void getAmountAsDecimal() {
        Transaction t = new Transaction();
        t.setAmount(100L);
        assert(t.getAmountAsDecimal() == 1.00);
    }

    @Test
    void copy() {
        Transaction a = new Transaction();
        a.type(Transaction.TypeEnum.TRANSACTION)
                .ibANFrom(IbanHelper.generateIban())
                .ibANTo(IbanHelper.generateIban())
                .amount(100L)
                .performedBy(new User());

        Transaction b = a.copy();

        assert (a != b);
        assert(a.equals(b));
    }

    @Test
    void swapIban() {
        String ibanA = IbanHelper.generateIban();
        String ibanB = IbanHelper.generateIban();

        Transaction t = new Transaction();
        t.ibANFrom(ibanA);
        t.ibANTo(ibanB);
        t.swapIban();

        assert(ibanA.equals(t.getIbanTo()));
        assert(ibanB.equals(t.getIbanFrom()));
    }

    @Test
    void validate() throws Exception {
        Transaction t = new Transaction();
        assertTrue(assertThrows(
                BadRequestException.class,
                t::validate
        ).getMessage().contains("Transaction info not set"));

        t.type(Transaction.TypeEnum.TRANSACTION);
        t.ibANTo("a");
        t.amount(-1L);
        t.performedBy(new User());

        assertTrue(assertThrows(
                BadRequestException.class,
                t::validate
        ).getMessage().contains("Iban from not set on transaction"));

        t.ibANFrom("b");

        assertTrue(assertThrows(
                BadRequestException.class,
                t::validate
        ).getMessage().contains("Invalid IBAN"));

        String iban = IbanHelper.generateIban();

        t.ibANTo(iban);
        t.ibANFrom(iban);

        assertTrue(assertThrows(
                BadRequestException.class,
                t::validate
        ).getMessage().contains("Source and Dest cannot be the same"));

        t.ibANFrom(IbanHelper.generateIban());

        assertTrue(assertThrows(
                BadRequestException.class,
                t::validate
        ).getMessage().contains("Invalid amount"));

        t.amount(1L);

        t.validate();
    }
}
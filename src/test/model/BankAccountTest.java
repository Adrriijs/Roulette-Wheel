package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    void runBefore() {
        bankAccount = new BankAccount(1000);
    }

    @Test
    void testConstructor() {
        assertEquals(1000, bankAccount.getBalance());
    }

    @Test
    void testDeposit() {
        bankAccount.deposit(5000);
        assertEquals(6000, bankAccount.getBalance());
    }

    @Test
    void testSubtract() {
        bankAccount.subtract(550);
        assertEquals(450, bankAccount.getBalance());
    }
}

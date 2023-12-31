package kt.springboot.tutorial.learningktspringb.datasource.mock

import kt.springboot.tutorial.learningktspringb.datasource.BankDataSource
import kt.springboot.tutorial.learningktspringb.model.Bank
import org.springframework.stereotype.Repository
import kotlin.NoSuchElementException

@Repository("mockData")
class MockBankDataSource : BankDataSource {

    val banks = mutableListOf(
            Bank("1234", 0.1, 1),
            Bank("2345", 17.1, 2),
            Bank("7689", 15.1, 5),
    )

    override fun retrieveBanks(): Collection<Bank> {
        return banks
    }

    override fun retrieveBank(accountNumber: String): Bank {
        return banks.firstOrNull() { it.accountNumber == accountNumber }
                ?: throw NoSuchElementException("Could not find a bank account with account number: $accountNumber")
    }

    override fun createBank(bank: Bank): Bank {
        if (banks.any {it.accountNumber == bank.accountNumber}) {
            throw IllegalArgumentException("Bank with this account number already exists: ${bank.accountNumber}")
        }
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == bank.accountNumber }
                ?: throw NoSuchElementException("Could not find a bank account with account number: ${bank.accountNumber}")
        banks.remove(currentBank)
        banks.add(bank)

        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull { it.accountNumber == accountNumber }
                ?: throw NoSuchElementException("Could not find a bank account with account number: ${accountNumber}")
        banks.remove(currentBank)
    }
}
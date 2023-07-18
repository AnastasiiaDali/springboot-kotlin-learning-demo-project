package kt.springboot.tutorial.learningktspringb.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kt.springboot.tutorial.learningktspringb.datasource.BankDataSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest {
    private val dataSource: BankDataSource = mockk()
    private val bankService = BankService(dataSource)
   @Test
   fun `should call it's data source to retrive the data`() {
   // given
       every { dataSource.retrieveBanks() } returns emptyList()
       // when
       val banks = bankService.getBanks()
       // then
       verify(exactly = 1) { dataSource.retrieveBanks() }
   }
}
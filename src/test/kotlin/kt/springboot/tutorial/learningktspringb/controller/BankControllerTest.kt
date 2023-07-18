package kt.springboot.tutorial.learningktspringb.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.InternalPlatformDsl.toStr
import kt.springboot.tutorial.learningktspringb.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor (
         val mockMvc: MockMvc,
         val objectMapper: ObjectMapper
) {

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            // when / then
            mockMvc.get(baseUrl)
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$[2].accountNumber") {value("0987")}
                    }
        }
    }

    @Nested
    @DisplayName("GET api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return a bank with given account number`() {
            // given
            val accountNumber = 2345
            // when / then
            mockMvc.get("$baseUrl/$accountNumber")
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.trust") {value("17.1")}
                        jsonPath("$.transactionFee") {value("2")}
                    }
        }

        @Test
        fun `should return not found if account number does not exists`() {
            // given
            val accountNumber = "does_not_exists"
            // when / then
            mockMvc.get("$baseUrl/$accountNumber")
                    .andDo { print() }
                    .andExpect {
                        status { isNotFound() }
                    }

        }
    }

    @Nested
    @DisplayName("POST api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddBank {
        @Test
        fun `should add the new bank`() {
            // given
            val bank = Bank("0987", 1.16, 4)

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }

            // then
            performPost
                    .andDo { print() }
                    .andExpect {
                        status { isCreated() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.accountNumber") {value("0987")}
                        jsonPath("$.trust") {value("1.16")}
                        jsonPath("$.transactionFee") {value("4")}
                    }
                    }

        @Test
        fun `should BAD REQUEST if bank account with given number already exists`() {
            // given
            val invalidBank = Bank("2345", 17.1, 2)

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            // then
            performPost
                    .andDo { print() }
                    .andExpect {
                        status { isBadRequest() }
                    }
                    }
        }

    @Nested
    @DisplayName("PATH api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        @Test
        @DirtiesContext
        fun `should update existing bank`() {
            // given
            val updatedBank = Bank("2345", 3.90, 3)

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }

            // then
            performPatch
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content {
                            contentType(MediaType.APPLICATION_JSON)
                            json(objectMapper.writeValueAsString(updatedBank))
                        }
                    }
            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                    .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }
        }

        @Test
        fun `should return NOT FOUND if no bank exists for the given account number`() {
            // given
            val invalidBank = Bank("does_not_exists", 3.90, 3)

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            // then
            performPatch
                    .andDo { print() }
                    .andExpect {
                        status { isNotFound() }
                    }
        }
    }

    @Nested
    @DisplayName("DELETE api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {
        @Test
        @DirtiesContext
        fun `should delete the bank with provided account number`() {
            // given
            val accountNumber = 1234

            // when / then
            mockMvc.delete("$baseUrl/$accountNumber")
                    .andDo { print() }
                    .andExpect {
                        status { isNoContent() }
                    }
            mockMvc.get("$baseUrl/${accountNumber}")
                    .andExpect { status { isNotFound() } }
        }

        @Test
        @DirtiesContext
        fun `should return NOT FOUND if no bank exists for the given account number`() {
            // given
            val invalidAccountNumber = "does_not_exists"

            // when / then
            mockMvc.delete("$baseUrl/$invalidAccountNumber")
                    .andDo { print() }
                    .andExpect {
                        status { isNotFound() }
                    }
        }
    }
}
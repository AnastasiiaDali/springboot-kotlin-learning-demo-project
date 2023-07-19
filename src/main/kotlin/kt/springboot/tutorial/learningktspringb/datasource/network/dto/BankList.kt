package kt.springboot.tutorial.learningktspringb.datasource.network.dto

import kt.springboot.tutorial.learningktspringb.model.Bank

data class BankList (
 val results: Collection<Bank>
)
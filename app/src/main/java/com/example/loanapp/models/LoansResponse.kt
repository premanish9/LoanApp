package com.example.loanapp.models

data class LoansResponse(
    val loans: List<Loan>
)

data class Loan(
    val amount: Double,
    val duration: Int,
    val status: String
)

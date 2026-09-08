package za.org.ecdoe.elevate

import za.org.ecdoe.elevate.value.UserId

data class Portfolio(
    val userId: UserId,
    val certificates: List<String>,
    val badges: List<String>,
)

data class Certificate(
    val code: String,
    val name: String,
    val description: String,
    val prerequisites: Set<String>
)

data class Badge(
    val name: String,
    val category: String,
    val description: String,
)

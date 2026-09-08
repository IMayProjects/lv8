package za.org.ecdoe.elevate.rx

import za.org.ecdoe.elevate.value.Contact
import za.org.ecdoe.elevate.value.UserId

interface DomainEvent

data class UserContactAdded(
    val id: UserId,
    val contact: Contact
) : DomainEvent
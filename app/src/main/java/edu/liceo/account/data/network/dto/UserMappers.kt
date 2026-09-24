package edu.liceo.account.data.network.dto

import edu.liceo.account.domain.model.User

fun UserDto.toDomain(): User = User(
    id = id ?: "",
    fullName = fullname?.trim() ?: "(no name)",
    email = email?.trim() ?: "",
    birthdate = birthdate ?: "(not set)"
)

package edu.liceo.fieldkit.ui

import edu.liceo.fieldkit.domain.model.User

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data class Error(val message: String) : AuthUiState
    data class AccountCreated(val name: String) : AuthUiState
    data class LoggedIn(val user: User) : AuthUiState
}

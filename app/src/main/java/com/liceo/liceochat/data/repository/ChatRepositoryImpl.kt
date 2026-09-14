package com.liceo.liceochat.data.repository

import com.liceo.liceochat.core.AppResult
import com.liceo.liceochat.data.local.MessageDao
import com.liceo.liceochat.data.network.ChatApiService
import com.liceo.liceochat.data.network.dto.NewMessageDto
import com.liceo.liceochat.data.network.dto.toDomain
import com.liceo.liceochat.data.network.dto.toEntity
import com.liceo.liceochat.domain.ChatRepository
import com.liceo.liceochat.domain.Message
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChatRepositoryImpl(
    private val api: ChatApiService,
    private val dao: MessageDao
) : ChatRepository {

    override suspend fun getMessages(): AppResult<List<Message>> {
        val networkResult = safeCall { api.getMessages().toDomain() }
        
        return if (networkResult is AppResult.Success) {
            // Save to database successfully
            try {
                dao.insertAll(networkResult.data.map { it.toEntity() })
            } catch (e: Exception) {
                // Ignore DB save errors if network succeeded
            }
            networkResult
        } else {
            // Network failed, try database
            try {
                val saved = dao.getAll().map { it.toDomain() }
                if (saved.isNotEmpty()) {
                    AppResult.Success(saved)
                } else {
                    networkResult
                }
            } catch (e: Exception) {
                // DB also failed
                networkResult
            }
        }
    }

    override suspend fun sendMessage(sender: String, text: String): AppResult<Unit> =
        safeCall {
            val dto = NewMessageDto(sender, text, System.currentTimeMillis())
            api.sendMessage(dto)
            Unit
        }

    private inline fun <T> safeCall(block: () -> T): AppResult<T> =
        try { AppResult.Success(block()) }
        catch (e: UnknownHostException) { AppResult.Failure.NoInternet }
        catch (e: SocketTimeoutException) { AppResult.Failure.Timeout }
        catch (e: IOException) { AppResult.Failure.NoInternet }
        catch (e: Exception) { AppResult.Failure.Unknown(e.message) }
}

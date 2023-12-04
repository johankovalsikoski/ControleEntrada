package johan.kovalsikoski.controledeentrada.domain.repository

import johan.kovalsikoski.controledeentrada.data.local.entity.GuestEntity
import kotlinx.coroutines.flow.Flow

interface GuestsRepository {
    suspend fun insertGuest(guestEntity: GuestEntity)
    suspend fun removeGuest(guestEntity: GuestEntity)
    fun getGuestList(): Flow<List<GuestEntity>>
}
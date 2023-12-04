package johan.kovalsikoski.controledeentrada.data.repository

import johan.kovalsikoski.controledeentrada.data.local.dao.GuestDao
import johan.kovalsikoski.controledeentrada.data.local.entity.GuestEntity
import johan.kovalsikoski.controledeentrada.domain.repository.GuestsRepository
import kotlinx.coroutines.flow.Flow

class GuestsRepositoryImpl(private val dao: GuestDao): GuestsRepository {

    override suspend fun insertGuest(guestEntity: GuestEntity) {
        dao.insertGuest(guestEntity)
    }

    override suspend fun removeGuest(guestEntity: GuestEntity) {
        dao.deleteGuest(guestEntity)
    }

    override fun getGuestList(): Flow<List<GuestEntity>> {
        return dao.getGuestList()
    }

}
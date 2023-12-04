package johan.kovalsikoski.controledeentrada.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import johan.kovalsikoski.controledeentrada.data.local.entity.GuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GuestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuest(guestEntity: GuestEntity)

    @Delete
    suspend fun deleteGuest(guestEntity: GuestEntity)

    @Query("""SELECT * FROM person""")
    fun getGuestList(): Flow<List<GuestEntity>>

}

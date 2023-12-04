package johan.kovalsikoski.controledeentrada.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import johan.kovalsikoski.controledeentrada.data.local.dao.GuestDao
import johan.kovalsikoski.controledeentrada.data.local.entity.GuestEntity

@Database(
    entities = [GuestEntity::class],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract val dao: GuestDao
}
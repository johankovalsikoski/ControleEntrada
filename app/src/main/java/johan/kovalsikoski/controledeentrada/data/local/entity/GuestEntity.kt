package johan.kovalsikoski.controledeentrada.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Person")
data class GuestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

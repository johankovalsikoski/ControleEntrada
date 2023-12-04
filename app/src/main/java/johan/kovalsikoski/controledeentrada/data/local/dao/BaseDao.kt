package johan.kovalsikoski.controledeentrada.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery

@Dao
abstract class BaseDao<Entity, KeyType>(private val tableName: String) {

    @Transaction
    open suspend fun replaceAll(entities: List<Entity>) {
        deleteAll()
        insertReplace(entities)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReplace(entity: Entity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReplace(entities: List<Entity>)

    @Update
    abstract suspend fun update(entity: Entity)

    @Update
    abstract suspend fun update(entities: List<Entity>)

    @Delete
    abstract suspend fun delete(entity: Entity)

    @Delete
    abstract suspend fun delete(entities: List<Entity>)

    @RawQuery
    protected abstract suspend fun findAll(query: SimpleSQLiteQuery): List<Entity>

    open suspend fun findAll(): List<Entity> =
        findAll(SimpleSQLiteQuery("SELECT * FROM $tableName"))

    @RawQuery
    protected abstract suspend fun find(query: SimpleSQLiteQuery): List<Entity>

    open suspend fun find(id: KeyType): Entity? =
        find(listOf(id)).firstOrNull()

    open suspend fun find(ids: List<KeyType>): List<Entity> {
        val queryIds = ids.joinToString(separator = ",") { "'$it'" }
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE id IN ($queryIds)")
        return find(query)
    }

    @RawQuery
    protected abstract suspend fun deleteAll(query: SimpleSQLiteQuery): Int

    open suspend fun deleteAll() {
        deleteAll(SimpleSQLiteQuery("DELETE FROM $tableName"))
    }
}
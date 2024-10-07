package id.ac.umn.project_ukm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1,
    entities = [Variable::class, DataHarian::class, Penanggalan::class])
abstract class DatabaseUKM: RoomDatabase() {
    abstract fun getVarDao(): DaoVariable
    abstract fun getHarianDao(): DaoDataHarian
    abstract fun getPenanggalanDao(): DaoPenanggalan

    companion object {
        @Volatile
        private var INSTANCE: DatabaseUKM? = null

        fun getDatabase(context: Context): DatabaseUKM {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, DatabaseUKM::class.java, "database_ukm"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
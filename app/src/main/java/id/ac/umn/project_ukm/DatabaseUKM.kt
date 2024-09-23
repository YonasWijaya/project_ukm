package id.ac.umn.project_ukm

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [Variable::class, DataHarian::class])
abstract class DatabaseUKM: RoomDatabase() {
    abstract fun getVarDao(): DaoVariable
    abstract fun getHarianDao(): DaoDataHarian
    abstract fun getPenanggalanDao(): DaoPenanggalan
}
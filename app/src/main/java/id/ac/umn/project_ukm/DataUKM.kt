package id.ac.umn.project_ukm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Variable(
    @PrimaryKey(true) val id: Int,
    val nama: String,
    val tipe: String
)

@Entity
data class DataHarian(
    @PrimaryKey(true) val id: Int,
    val tahunBulan: Int,
    val tanggal: Int,
    val keterangan: String,
    val tipe: String,
    val nominal: Int
)

@Entity(primaryKeys = ["tahunBulan", "tanggal"])
data class Penanggalan(
    val tahunBulan: Int,
    val tanggal: Int
)
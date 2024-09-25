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
    val tahun: Int,
    val bulan: Int,
    val tanggal: Int,
    val deskripsi: String,
    val keteranganTambahan : String?,
    val tipe: String,
    val nominal: Int
)

@Entity(primaryKeys = ["tahun", "bulan", "tanggal"])
data class Penanggalan(
    val tahun: Int,
    val bulan: Int,
    val tanggal: Int
)
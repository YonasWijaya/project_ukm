package id.ac.umn.project_ukm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoVariable {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addVariable(newVariable: Variable)

    @Delete
    fun deleteVariable(variable: Variable)

    @Query("SELECT * FROM Variable")
    fun getAllVar(): Array<Variable>

    @Query("SELECT nama FROM Variable WHERE tipe LIKE :tipe ")
    fun getNama(tipe: String): Array<String>
}

@Dao
interface DaoDataHarian {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addHarian(newDataHarian: DataHarian)

    @Delete
    fun deleteDataHarian(dataHarian: DataHarian)

    @Query("SELECT * FROM DataHarian WHERE tahun = :tahun AND bulan = :bulan")
    fun getBulanan(tahun: Int, bulan: Int): Array<DataHarian>

    @Query("SELECT * FROM DataHarian WHERE tahun = :tahun AND bulan = :bulan AND tanggal = :tanggal")
    fun getHarian(tahun: Int, bulan: Int, tanggal: Int): Array<DataHarian>
}

@Dao
interface DaoPenanggalan {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPenanggalan(newPenanggalan: Penanggalan)

    @Query("SELECT DISTINCT (tahun || PRINTF('%02d', bulan)) FROM Penanggalan")
    fun getDaftarBulan(): Array<String>

    @Query("SELECT * FROM Penanggalan WHERE tahun = :tahun AND bulan = :bulan")
    fun getDaftarHari(tahun: Int, bulan: Int): Array<Penanggalan>
}
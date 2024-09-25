package id.ac.umn.project_ukm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoVariable {
    @Insert
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
    @Insert
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
    @Insert
    fun addPenanggalan(newPenanggalan: Penanggalan)

    @Query("SELECT DISTINCT tahun, bulan FROM Penanggalan")
    fun getDaftarBulan(): Pair<Int, Int>

    @Query("SELECT tanggal FROM Penanggalan WHERE tahun = :tahun AND bulan = :bulan")
    fun getDaftarHari(tahun: Int, bulan: Int): Array<Int>
}
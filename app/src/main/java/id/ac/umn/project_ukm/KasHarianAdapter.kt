package id.ac.umn.project_ukm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class KasHarianAdapter(private val kasHarian: MutableList<DataHarian>, private var saldoAwal: Int):
RecyclerView.Adapter<KasHarianAdapter.KasHarianViewHolder>(){
    private lateinit var db: DatabaseUKM
    private lateinit var saldoListener: SaldoListener

    fun setSaldoListener(listener: SaldoListener) {
        saldoListener = listener
    }

    interface SaldoListener{
        fun saldoAkhir()
    }

    class KasHarianViewHolder(item: View):RecyclerView.ViewHolder(item) {
        val keterangan: TextView = item.findViewById(R.id.keteranganHarianList)
        val pemasukan: TextView = item.findViewById(R.id.pemasukanHarianList)
        val pengeluaran: TextView = item.findViewById(R.id.pengeluaranHarianList)
        val saldo: TextView = item.findViewById(R.id.saldoHarianList)
        val hapusBtn: Button = item.findViewById(R.id.hapusKasHarian)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KasHarianViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kas_harian_list, parent, false)
        return KasHarianViewHolder(view)
    }

    override fun onBindViewHolder(holder: KasHarianViewHolder, position: Int) {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        db = DatabaseUKM.getDatabase(holder.itemView.context)
        if (kasHarian[position].debitKredit == 0) {
            holder.pemasukan.text = format.format(kasHarian[position].nominal)
            saldoAwal += kasHarian[position].nominal
        }
        else {
            holder.pengeluaran.text = format.format(kasHarian[position].nominal)
            saldoAwal -= kasHarian[position].nominal
        }
        holder.saldo.text = format.format(saldoAwal)

        val keterangan: String = when(kasHarian[position].tipe) {
            "Penjualan" -> {"Penjualan Tunai ${kasHarian[position].deskripsi} ${kasHarian[position].keteranganTambahan}"}
            "Piutang" -> {"Penerimaan Piutang ${kasHarian[position].keteranganTambahan}"}
            "Pembelian" -> {"Pembelian ${kasHarian[position].deskripsi} ${kasHarian[position].keteranganTambahan}"}
            "Hutang" -> {"Pembayaran Hutang ${kasHarian[position].keteranganTambahan}"}
            "Gaji" -> {"Pembayaran Gaji ${kasHarian[position].keteranganTambahan}"}
            "Biaya" -> {"Biaya ${kasHarian[position].keteranganTambahan}"}
            else -> {""}
        }
        holder.keterangan.text = keterangan

        holder.hapusBtn.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            with(builder) {
                setTitle("HAPUS")
                setMessage("Apakah anda yakin ingin menghapus ${holder.keterangan.text}?")
                setPositiveButton("Hapus") {_, _ ->
                    db.getHarianDao().deleteDataHarian(kasHarian[position])
                    kasHarian.removeAt(position)
                    notifyItemRemoved(position)
                    saldoListener.saldoAkhir()
                }
                setNegativeButton("Kembali") {_, _ ->}
                show()
            }
        }
    }

    override fun getItemCount(): Int {
        return kasHarian.size
    }
}
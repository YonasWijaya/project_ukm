package id.ac.umn.project_ukm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class BarangAdapter(private val variable: MutableList<Variable>):
RecyclerView.Adapter<BarangAdapter.BarangViewHolder>(){
    private lateinit var db: DatabaseUKM

    class BarangViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val nama: TextView = item.findViewById(R.id.namaVariable)
        val tipe: TextView = item.findViewById(R.id.tipeVariable)
        val hapusBtn: Button = item.findViewById(R.id.hapusVariable)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.barang_list, parent, false)
        return BarangViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        db = DatabaseUKM.getDatabase(holder.itemView.context)
        holder.nama.text = variable[position].nama
        holder.tipe.text = variable[position].tipe
        holder.hapusBtn.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            with(builder) {
                setTitle("HAPUS")
                setMessage("Apakah anda yakin ingin menghapus ${holder.nama.text}?")
                setPositiveButton("Hapus") {_, _ ->
                    db.getVarDao().deleteVariable(variable[position])
                    variable.removeAt(position)
                    notifyItemRemoved(position)
                }
                setNegativeButton("Kembali") {_, _ ->}
                show()
            }
        }
    }

    override fun getItemCount(): Int {
        return variable.size
    }
}
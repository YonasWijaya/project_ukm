package id.ac.umn.project_ukm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class BulanAdapter(val bulan: Array<Penanggalan>, private val adaTanggal: Boolean):
    RecyclerView.Adapter<BulanAdapter.BulanViewHolder>() {
    class BulanViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val button: Button = item.findViewById(R.id.btnBulanList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BulanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bulan_list, parent, false)
        return BulanViewHolder(view)
    }

    override fun onBindViewHolder(holder: BulanViewHolder, position: Int) {
        val hari = LocalDate.of(bulan[position].tahun, bulan[position].bulan, bulan[position].tanggal)

        val format: DateTimeFormatter = if (adaTanggal){
            DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
        } else {
            DateTimeFormatter.ofPattern("MMMM yyyy", Locale("id", "ID"))
        }

        holder.button.text = hari.format(format)
        holder.button.setOnClickListener {
            val navController = it.findNavController()
            val bundle = Bundle()
            bundle.putString("TahunBulanKey", hari.toString())

            val navId: Int = if (adaTanggal) {
                R.id.action_hariFragment_to_kasHarianFragment
            } else {
                R.id.action_bulanFragment_to_isiBulanFragment
            }
            navController.navigate(navId, bundle)
        }
    }

    override fun getItemCount(): Int {
        return bulan.size
    }
}
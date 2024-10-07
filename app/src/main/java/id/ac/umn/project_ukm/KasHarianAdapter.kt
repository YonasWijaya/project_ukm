package id.ac.umn.project_ukm

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class KasHarianAdapter(val kasHarian: Array<DataHarian>, val saldoAwal: Int):
RecyclerView.Adapter<KasHarianAdapter.KasHarianViewHolder>(){
    class  KasHarianViewHolder(item: View):RecyclerView.ViewHolder(item) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KasHarianViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: KasHarianViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return kasHarian.size
    }
}
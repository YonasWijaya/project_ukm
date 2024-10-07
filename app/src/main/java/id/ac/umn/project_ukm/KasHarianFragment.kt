package id.ac.umn.project_ukm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import id.ac.umn.project_ukm.databinding.FragmentKasHarianBinding
import java.text.NumberFormat
import java.util.Locale

class KasHarianFragment : Fragment() {
    private lateinit var binding: FragmentKasHarianBinding
    private lateinit var db: DatabaseUKM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kas_harian, container, false)
        val key = requireArguments().getString("TahunBulanKey")
        db = DatabaseUKM.getDatabase(requireContext())
        binding.btnTambahIsi.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("TahunBulanKey", key)
            it.findNavController().navigate(R.id.action_kasHarianFragment_to_tambahKasHarianFragment, bundle)
        }
        val penanggalan = key!!.split("-")
        val dao = db.getHarianDao()
        var saldo = dao.getTotalTahunSebelum(penanggalan[0].toInt(), 0) ?: 0
        saldo -= dao.getTotalTahunSebelum(penanggalan[0].toInt(), 1) ?: 0
        saldo += dao.getTotalBulanSebelum(penanggalan[0].toInt(), penanggalan[1].toInt(), 0) ?: 0
        saldo -= dao.getTotalBulanSebelum(penanggalan[0].toInt(), penanggalan[1].toInt(), 1) ?: 0
        saldo += dao.getTotalHariSebelum(penanggalan[0].toInt(), penanggalan[1].toInt(), penanggalan[2].toInt(), 0) ?: 0
        saldo -= dao.getTotalHariSebelum(penanggalan[0].toInt(), penanggalan[1].toInt(), penanggalan[2].toInt(), 1) ?: 0
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        binding.saldoAwal.text = format.format(saldo)

        return binding.root
    }
}
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
        setHasilAkhir(dao, format, penanggalan, saldo)
        val tanggal = LocalDate.parse(key)
        val formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
        binding.hariJurnal.text = tanggal.format(formatTanggal)

        val kas = dao.getHarian(penanggalan[0].toInt(), penanggalan[1].toInt(), penanggalan[2].toInt())
        val mutableKas = kas.toMutableList()
        val adapter = KasHarianAdapter(mutableKas, saldo)
        binding.kasHarianList.adapter = adapter
        adapter.setSaldoListener(object: KasHarianAdapter.SaldoListener{
            override fun saldoAkhir() {
                setHasilAkhir(dao, format, penanggalan, saldo)
            }
        })

        return binding.root
    }

    private fun setHasilAkhir(dao: DaoDataHarian, format: NumberFormat, penanggalan: List<String>, saldo: Int) {
        val pemasukan = dao.getTotalHariIni(penanggalan[0].toInt(), penanggalan[1].toInt(), penanggalan[2].toInt(), 0) ?: 0
        val pengeluaran = dao.getTotalHariIni(penanggalan[0].toInt(), penanggalan[1].toInt(), penanggalan[2].toInt(), 1) ?: 0
        val saldoAkhir = saldo + pemasukan - pengeluaran
        binding.pemasukanHarian2.text = format.format(pemasukan)
        binding.pengeluaranHarian2.text = format.format(pengeluaran)
        binding.saldoAkhir2.text = format.format(saldoAkhir)
    }
}
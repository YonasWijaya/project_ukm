package id.ac.umn.project_ukm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import id.ac.umn.project_ukm.databinding.FragmentBulanBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class BulanFragment : Fragment() {
    private lateinit var binding: FragmentBulanBinding
    private lateinit var db: DatabaseUKM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bulan, container, false)
        db = DatabaseUKM.getDatabase(requireContext())
        binding.btnTambahBulan.setOnClickListener {
            visibilityCalendar(View.VISIBLE)
            visibilityList(View.GONE)
            val hari = LocalDate.of(binding.pilihBulan.year, binding.pilihBulan.month + 1, binding.pilihBulan.dayOfMonth)
            val format = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
            binding.teksBulan2.text = hari.format(format)
        }

        binding.btnBatalBulan.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            with(builder) {
                setTitle("BATAL")
                setMessage("Apakah anda yakin ingin membatalkan ini?")
                setPositiveButton("Batal") {_, _ ->
                    visibilityCalendar(View.GONE)
                    visibilityList(View.VISIBLE)
                }
                setNegativeButton("Kembali") {_, _ ->}
                show()
            }
        }

        binding.btnSimpanBulan.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            with(builder) {
                setTitle("SIMPAN")
                setMessage("Apakah anda yakin ingin menyimpan ini?")
                setPositiveButton("Simpan") {_, _ ->
                    val bulan = db.getPenanggalanDao()
                    val newBulan = Penanggalan(binding.pilihBulan.year, binding.pilihBulan.month + 1, binding.pilihBulan.dayOfMonth)
                    bulan.addPenanggalan(newBulan)
                    binding.bulanList.adapter = BulanAdapter(daftarBulan(bulan.getDaftarBulan()), false)
                    visibilityCalendar(View.GONE)
                    visibilityList(View.VISIBLE)
                }
                setNegativeButton("Kembali") {_, _ ->}
                show()
            }
        }

        binding.pilihBulan.setOnDateChangedListener{ _, tahun, bulan, tanggal ->
            val hari = LocalDate.of(tahun, bulan + 1, tanggal)
            val format = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
            binding.teksBulan2.text = hari.format(format)
        }

        val bulan = db.getPenanggalanDao().getDaftarBulan()
        binding.bulanList.adapter = BulanAdapter(daftarBulan(bulan), false)

        return binding.root
    }

    private fun visibilityList(code: Int) {
        binding.bulanList.visibility = code
        binding.btnTambahBulan.visibility = code
    }

    private fun visibilityCalendar(code: Int) {
        binding.pilihBulan.visibility = code
        binding.teksBulan1.visibility = code
        binding.teksBulan2.visibility = code
        binding.btnSimpanBulan.visibility = code
        binding.btnBatalBulan.visibility = code
    }

    private fun daftarBulan(bulanInput: Array<String>): Array<Penanggalan> {
        bulanInput.sort()
        var temp: Array<Penanggalan> = arrayOf()
        for(tiapBulan in bulanInput){
            val bulanTemp = Penanggalan(tiapBulan.substring(0, 4).toInt(),
                tiapBulan.substring(4).toInt(), 1)
            temp += bulanTemp
        }
        return temp
    }
}
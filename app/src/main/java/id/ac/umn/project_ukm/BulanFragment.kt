package id.ac.umn.project_ukm

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.databinding.DataBindingUtil
import id.ac.umn.project_ukm.databinding.FragmentBulanBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class BulanFragment : Fragment() {
    private lateinit var binding: FragmentBulanBinding
    private lateinit var db: DatabaseUKM

    @RequiresApi(Build.VERSION_CODES.O)
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
            visibilityCalendar(View.GONE)
            visibilityList(View.VISIBLE)
        }

        binding.btnSimpanBulan.setOnClickListener {
            visibilityCalendar(View.GONE)
            visibilityList(View.VISIBLE)
            val bulan = db.getPenanggalanDao()
            val newBulan = Penanggalan(binding.pilihBulan.year, binding.pilihBulan.month + 1, binding.pilihBulan.dayOfMonth)
            bulan.addPenanggalan(newBulan)
        }

        binding.pilihBulan.setOnDateChangedListener{ _, tahun, bulan, tanggal ->
            val hari = LocalDate.of(tahun, bulan + 1, tanggal)
            val format = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
            binding.teksBulan2.text = hari.format(format)
        }

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
}
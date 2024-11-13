package id.ac.umn.project_ukm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import id.ac.umn.project_ukm.databinding.FragmentBulanBinding
import id.ac.umn.project_ukm.databinding.FragmentHariBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class HariFragment : Fragment() {
    private lateinit var binding: FragmentHariBinding
    private lateinit var db: DatabaseUKM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hari, container, false)
        val key = arguments?.getString("TahunBulanKey")
        db = DatabaseUKM.getDatabase(requireContext())
        binding.btnTambahHari.setOnClickListener{
            visibilityCalendar(View.VISIBLE)
            visibilityList(View.GONE)
            val hari = LocalDate.of(binding.pilihHari.year, binding.pilihHari.month + 1, binding.pilihHari.dayOfMonth)
            val format = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
            binding.teksHari2.text = hari.format(format)
        }

        binding.btnBatalHari.setOnClickListener {
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

        val hari = db.getPenanggalanDao().getDaftarHari(tahun = key?.substring(0, 4)!!.toInt(), bulan = key.substring(5, 7).toInt())
        binding.btnSimpanHari.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            with(builder) {
                setTitle("SIMPAN")
                setMessage("Apakah anda yakin ingin menyimpan ini?")
                setPositiveButton("Simpan") {_, _ ->
                    val hariDao = db.getPenanggalanDao()
                    val newHari = Penanggalan(binding.pilihHari.year, binding.pilihHari.month + 1, binding.pilihHari.dayOfMonth)
                    hariDao.addPenanggalan(newHari)
                    binding.hariList.adapter = BulanAdapter(hari, true)
                    visibilityCalendar(View.GONE)
                    visibilityList(View.VISIBLE)
                }
                setNegativeButton("Kembali") {_, _ ->}
                show()
            }
        }

        binding.hariList.adapter = BulanAdapter(hari, true)

        return binding.root
    }

    private fun visibilityList(code: Int) {
        binding.hariList.visibility = code
        binding.btnTambahHari.visibility = code
    }

    private fun visibilityCalendar(code: Int) {
        binding.pilihHari.visibility = code
        binding.teksHari1.visibility = code
        binding.teksHari2.visibility = code
        binding.btnSimpanHari.visibility = code
        binding.btnBatalHari.visibility = code
    }
}
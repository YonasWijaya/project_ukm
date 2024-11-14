package id.ac.umn.project_ukm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import id.ac.umn.project_ukm.databinding.FragmentIsiBulanBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class IsiBulanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentIsiBulanBinding>(inflater, R.layout.fragment_isi_bulan, container, false)
        val key = arguments?.getString("TahunBulanKey")
        val tanggal = LocalDate.parse(key)
        val format = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("id", "ID"))
        binding.namaBulan.text = tanggal.format(format)
        binding.btnJurnalHarian.setOnClickListener {
            val navController = it.findNavController()
            val bundle = Bundle()
            bundle.putString("TahunBulanKey", key)
            navController.navigate(R.id.action_isiBulanFragment_to_hariFragment, bundle)
        }
        return binding.root
    }
}
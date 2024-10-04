package id.ac.umn.project_ukm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import id.ac.umn.project_ukm.databinding.FragmentKasHarianBinding

class KasHarianFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentKasHarianBinding>(inflater, R.layout.fragment_kas_harian, container, false)
        val key = requireArguments().getString("TahunBulanKey")
        binding.btnTambahIsi.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("TahunBulanKey", key)
            it.findNavController().navigate(R.id.action_kasHarianFragment_to_tambahKasHarianFragment, bundle)
        }

        return binding.root
    }
}
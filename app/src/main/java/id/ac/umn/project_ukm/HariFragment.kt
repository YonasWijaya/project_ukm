package id.ac.umn.project_ukm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import id.ac.umn.project_ukm.databinding.FragmentBulanBinding
import id.ac.umn.project_ukm.databinding.FragmentHariBinding

class HariFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHariBinding>(inflater, R.layout.fragment_hari, container, false)
        binding.btnTambahHari.setOnClickListener{

        }

        return binding.root
    }
}
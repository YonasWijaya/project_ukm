package id.ac.umn.project_ukm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import id.ac.umn.project_ukm.databinding.FragmentVariableBinding

class VariableFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentVariableBinding>(inflater, R.layout.fragment_variable, container, false)
        binding.btnTambahVariable.setOnClickListener{
            it.findNavController().navigate(R.id.action_variableFragment_to_tambahVariableFragment)
        }
        return binding.root
    }
}
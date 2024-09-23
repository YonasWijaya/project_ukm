package id.ac.umn.project_ukm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import id.ac.umn.project_ukm.databinding.FragmentTambahVariableBinding

class TambahVariableFragment : Fragment() {
    private lateinit var binding : FragmentTambahVariableBinding
    private lateinit var tipe: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tambah_variable, container, false)
        binding.batalBarang.setOnClickListener{ tambahBarang(true) }
        binding.simpanBarang.setOnClickListener{ tambahBarang(false) }

        tipe = arrayOf("Bahan baku", "Produk")
        val kategoriSpinner = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, tipe)
        kategoriSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.kategoriBarang.adapter = kategoriSpinner

        return binding.root
    }

    fun tambahBarang(sukses:Boolean){
        if(sukses && binding.namaBarang.text != null){
            
        }

        val navController = view?.findNavController()
        navController?.navigate(R.id.action_tambahVariableFragment_to_variableFragment)
    }
}
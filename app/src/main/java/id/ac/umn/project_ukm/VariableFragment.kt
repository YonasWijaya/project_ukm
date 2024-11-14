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
    private lateinit var binding: FragmentVariableBinding
    private lateinit var db: DatabaseUKM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_variable, container, false)
        binding.btnTambahVariable.setOnClickListener{
            it.findNavController().navigate(R.id.action_variableFragment_to_tambahVariableFragment)
        }
        db = DatabaseUKM.getDatabase(requireContext())
        val barang = db.getVarDao().getAllVar()
        val mutableBarang = barang.toMutableList()
        val adapter = BarangAdapter(mutableBarang)
        binding.bahanList.adapter = adapter

        return binding.root
    }
}
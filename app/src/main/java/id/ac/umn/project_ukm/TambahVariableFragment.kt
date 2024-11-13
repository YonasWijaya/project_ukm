package id.ac.umn.project_ukm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import id.ac.umn.project_ukm.databinding.FragmentTambahVariableBinding

class TambahVariableFragment : Fragment() {
    private lateinit var binding : FragmentTambahVariableBinding
    private lateinit var db: DatabaseUKM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tambah_variable, container, false)
        binding.batalBarang.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            with(builder) {
                setTitle("BATAL")
                setMessage("Apakah anda yakin ingin membatalkan ini?")
                setPositiveButton("Batal") {_, _ ->
                    tambahBarang(false)
                }
                setNegativeButton("Kembali") {_, _ ->}
                show()
            }
        }
        binding.simpanBarang.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            with(builder) {
                setTitle("SIMPAN")
                setMessage("Apakah anda yakin ingin menyimpan ini?")
                setPositiveButton("Simpan") {_, _ ->
                    tambahBarang(true)
                }
                setNegativeButton("Kembali") {_, _ ->}
                show()
            }
        }
        db = DatabaseUKM.getDatabase(requireContext())

        ArrayAdapter.createFromResource(requireContext(), R.array.tipe_barang, android.R.layout.simple_spinner_item).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.kategoriBarang.adapter = adapter
        }

        return binding.root
    }

    private fun tambahBarang(sukses:Boolean){
        if(sukses && binding.namaBarang.text != null){
            val barang = db.getVarDao()
            val newBarang = Variable(id = 0, nama = binding.namaBarang.text.toString(), tipe = binding.kategoriBarang.selectedItem.toString())
            barang.addVariable(newBarang)
        }
        val navController = requireView().findNavController()
        navController.navigate(R.id.action_tambahVariableFragment_to_variableFragment)
    }
}
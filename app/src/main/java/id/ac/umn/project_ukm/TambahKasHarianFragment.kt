package id.ac.umn.project_ukm

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import id.ac.umn.project_ukm.databinding.FragmentTambahKasHarianBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class TambahKasHarianFragment : Fragment() {
    private lateinit var binding: FragmentTambahKasHarianBinding
    private lateinit var db: DatabaseUKM
    private var debitKredit: Int = 0 //0 jika debit (pemasukan), 1 jika kredit (pengeluaran)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tambah_kas_harian, container, false)
        val key = requireArguments().getString("TahunBulanKey")
        db = DatabaseUKM.getDatabase(requireContext())

        val tanggal = LocalDate.parse(key)
        val format = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
        binding.namaHari.text = tanggal.format(format)
        binding.btnBatalJurnal.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            with(builder) {
                setTitle("BATAL")
                setMessage("Apakah anda yakin ingin membatalkan ini?")
                setPositiveButton("Batal") {_, _ ->
                    tambahJurnal(false, key!!)
                }
                setNegativeButton("Kembali") {_, _ ->}
                show()
            }
        }
        binding.btnSimpanJurnal.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            with(builder) {
                setTitle("SIMPAN")
                setMessage("Apakah anda yakin ingin menyimpan ini?")
                setPositiveButton("Simpan") {_, _ ->
                    tambahJurnal(true, key!!)
                }
                setNegativeButton("Kembali") {_, _ ->}
                show()
            }
        }
        ArrayAdapter.createFromResource(requireContext(), R.array.tipe_data, android.R.layout.simple_spinner_item).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.tipeData.adapter = adapter
        }

        binding.tipeData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val list = db.getVarDao()
                val isi: Array<String>
                val penjelasan: String
                fun isiDeskripsi(deskripsi: Boolean, isi: Array<String>) {
                    binding.deskripsiData.isEnabled = deskripsi
                    if(deskripsi) {
                        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, isi)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.deskripsiData.adapter = adapter
                    }
                }
                when(position){
                    0 -> {
                        isi = list.getNama("Produk") + "Lain-lain"
                        isiDeskripsi(true, isi)
                        penjelasan = "Penjualan Tunai ${binding.deskripsiData.selectedItem} "
                        debitKredit = 0
                    }
                    1 -> {
                        isi = arrayOf("")
                        isiDeskripsi(false, isi)
                        penjelasan = "Penerimaan Piutang "
                        debitKredit = 0
                    }
                    2 -> {
                        isi = list.getNama("Bahan baku") + "Lain-lain"
                        isiDeskripsi(true, isi)
                        penjelasan = "Pembelian ${binding.deskripsiData.selectedItem} "
                        debitKredit = 1
                    }
                    3 -> {
                        isi = arrayOf("")
                        isiDeskripsi(false, isi)
                        penjelasan = "Pembayaran Hutang "
                        debitKredit = 1
                    }
                    4 -> {
                        isi = arrayOf("")
                        isiDeskripsi(false, isi)
                        penjelasan = "Pembayaran Gaji "
                        debitKredit = 1
                    }
                    5 -> {
                        isi = arrayOf("")
                        isiDeskripsi(false, isi)
                        penjelasan = "Biaya "
                        debitKredit = 1
                    }
                    else -> {
                        penjelasan = ""
                    }
                }
                binding.penjelasan2.text = penjelasan
            }
        }

        binding.deskripsiData.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {  }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val penjelasan: String = when(binding.tipeData.selectedItemPosition) {
                    0 -> "Penjualan Tunai ${binding.deskripsiData.selectedItem} "
                    2 -> "Pembelian ${binding.deskripsiData.selectedItem} "
                    else -> ""
                }
                binding.penjelasan2.text = penjelasan
            }
        }

        binding.tambahanData.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.penjelasan3.text = s
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {  }
        })
        return binding.root
    }

    private fun tambahJurnal(sukses:Boolean, tanggal:String){
        if (sukses && binding.nominalData.text != null){
            val jurnal = db.getHarianDao()
            val split = tanggal.split("-")
            val newJurnal = DataHarian(0, split[0].toInt(), split[1].toInt(), split[2].toInt(), binding.deskripsiData.selectedItem.toString(), binding.tambahanData.text.toString(), binding.tipeData.selectedItem.toString(), binding.nominalData.text.toString().toInt(), debitKredit)
            jurnal.addHarian(newJurnal)
        }
        val bundle = Bundle()
        bundle.putString("TahunBulanKey", tanggal)
        val navController = requireView().findNavController()
        navController.navigate(R.id.action_tambahKasHarianFragment_to_kasHarianFragment, bundle)
    }
}
package id.ac.umn.project_ukm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import id.ac.umn.project_ukm.databinding.ActivityInsideBinding

class InsideActivity : AppCompatActivity() {
    //menu samping
    private lateinit var appBarConfig : AppBarConfiguration
    private lateinit var binding: ActivityInsideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_inside)
        //menu samping dan navigasi
        appBarConfig = AppBarConfiguration(setOf(R.id.bulanFragment, R.id.variableFragment), binding.drawerLayout)
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfig)
    }
}
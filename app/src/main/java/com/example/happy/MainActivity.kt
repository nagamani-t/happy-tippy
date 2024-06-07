package com.example.happy
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.happy.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        //the below code is to show the navigated page name in the actionBar
//        var appBarConfiguration = AppBarConfiguration(setOf(R.id.happy_home_item,R.id.happy_wallet_item,R.id.happy_tippy_item))
//        setupActionBarWithNavController(navController,appBarConfiguration)
        // Set up a listener to ensure we navigate to the correct fragment
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.happy_tippy_item -> {
                    navController.navigate(R.id.happy_tippy_item)
                    true
                }
                R.id.happy_wallet_item -> {
                    navController.navigate(R.id.happy_wallet_item)
                    true
                }
                R.id.happy_home_item-> {
                    navController.navigate(R.id.happy_home_item)
                    true
                }
                else -> false
            }
        }
    }
}
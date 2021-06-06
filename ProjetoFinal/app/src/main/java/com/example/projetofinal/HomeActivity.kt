package com.example.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.projetofinal.databinding.ActivityHomeBinding
import com.example.projetofinal.databinding.CardItemBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    val homeFrag = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, homeFrag)
            .commit()



        val w = ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.topAppBar,R.string.app_name,R.string.app_name
        )

        binding.drawerLayout.addDrawerListener(w)
        w.syncState()
        binding.navigationView.setCheckedItem(R.id.itemHome)
        binding.navigationView.setNavigationItemSelectedListener{menuItem ->
            when (menuItem.itemId) {
                R.id.itemHome -> {
                    val homeFrag = HomeFragment()
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.mainContainer, homeFrag)
                            .commit()
                }
                R.id.itemCart -> {
                    val cartFrag = CartFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainContainer, cartFrag)
                        .commit()
                }
                R.id.itemCategory -> {
                    val catFrag = CategoryFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainContainer, catFrag)
                        .commit()
                }
                R.id.itemPurchases -> {
                    val purFrag = PurchasesFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainContainer, purFrag)
                        .commit()
                }

            }
            binding.drawerLayout.closeDrawer(binding.navigationView)
            true

        }




    }

    override fun onResume() {
        super.onResume()

        // Vai precisar fazer o foreach aqui
        val card = CardItemBinding.inflate(layoutInflater)
        homeFrag.setIntent(card)

    }


}
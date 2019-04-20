package com.roacult.kero.team7.bottomnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.roacult.kero.team7.bottomnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        //to save current fragment on saveInstanceState
        const val CURENT_FRAGMET = "curent_fragment"

        //tag for each fragment
        const val TAG_HOME = "home"
        const val TAG_SEARCH = "search"
        const val TAG_NOTIFICATION = "notification"
        const val TAG_PROFILE = "profile"
    }

    //fragments
    private val homeFragment : HomeFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_HOME)
        if(fr != null)fr as HomeFragment
        else HomeFragment()
    }
    private val searchFragment  : SearchFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_SEARCH)
        if(fr != null) fr as SearchFragment
        else SearchFragment()
    }
    private val notificationFragment : NotificationFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_NOTIFICATION)
        if(fr != null) fr as NotificationFragment
        else NotificationFragment()
    }
    private val profileFragment : ProfileFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_PROFILE)
        if(fr != null) fr as ProfileFragment
        else ProfileFragment()
    }

    private var selectedFragment :Int = R.id.main
    private var activeFragment  : Fragment? = null

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        //retrieve current fragment for savedInstanceState
        savedInstanceState?.let {
            selectedFragment = it.getInt(CURENT_FRAGMET,R.id.main)
        }

        when(selectedFragment){
            R.id.main -> activeFragment = homeFragment
            R.id.search -> activeFragment = searchFragment
            R.id.notification -> activeFragment = notificationFragment
            R.id.profile -> activeFragment = profileFragment
        }

        if(savedInstanceState == null){
            //add all fragments but show only 
            //active fragment
            supportFragmentManager.beginTransaction().
                add(R.id.main_container,homeFragment, TAG_HOME).hide(homeFragment).
                add(R.id.main_container,searchFragment, TAG_SEARCH).hide(searchFragment).
                add(R.id.main_container,notificationFragment, TAG_NOTIFICATION).hide(notificationFragment).
                add(R.id.main_container,profileFragment , TAG_PROFILE).hide(profileFragment).
                show(activeFragment!!)
                .commit()
            
        }

        binding.bottomNav.setOnNavigationItemSelectedListener {
            setFragment(it.itemId)
        }
    }

    private fun setFragment(itemId: Int): Boolean {
        selectedFragment = itemId
        when(itemId){
            R.id.main -> {
                if(activeFragment is HomeFragment) return false
                supportFragmentManager.beginTransaction().
                    hide(activeFragment!!).
                    show(homeFragment).
                    commit()

                activeFragment = homeFragment
            }
            R.id.search  ->{
                if(activeFragment is SearchFragment) return false
                supportFragmentManager.beginTransaction().
                    hide(activeFragment!!).
                    show(searchFragment).
                    commit()

                activeFragment = searchFragment
            }
            R.id.notification ->{
                if(activeFragment is NotificationFragment) return false
                supportFragmentManager.beginTransaction().
                    hide(activeFragment!!).
                    show(notificationFragment).
                    commit()

                activeFragment = notificationFragment
            }
            R.id.profile ->{
                if(activeFragment is ProfileFragment) return false
                supportFragmentManager.beginTransaction().
                    hide(activeFragment!!).
                    show(profileFragment).
                    commit()

                activeFragment = profileFragment
            }
        }
        return true
    }

    //save current fragment when configuration changed
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURENT_FRAGMET,selectedFragment)
    }
}

package com.example.recipes.mainScreen.mainGuestActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.recipes.R
import com.example.recipes.data.model.Card
import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.logIn.LoginActivity
import com.example.recipes.mainScreen.MainActivityAdapter
import kotlinx.android.synthetic.main.activity_guest_main.*

class MainGuestActivity : AppCompatActivity(), MainGuestActivityContract.View {
    val presenter: MainGuestActivityContract.Presenter =
        MainGuestActivityPresenter(this, CardsRepository())

    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_main)
        presenter.setFirstScreen()
    }

    override fun getContext(): Context? {
        return applicationContext
    }

    override fun goLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun setToolbar(){
        setSupportActionBar(toolbarGuestMainScreen)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    override fun setRecyclerView(cardList: List<Card>?) {
        if(cardList == null){
            Toast.makeText(applicationContext, "Brak kart do wyświetlenia!", Toast.LENGTH_SHORT).show()
        }else{
            setLinearLayoutForRecyclerView(cardList)
            setSwipeRefreshLayoutEnabledStatus()
        }
    }

    private fun setLinearLayoutForRecyclerView(cardList: List<Card>?) {
        val adapterCards = MainActivityAdapter(cardList, applicationContext)
        RecyclerViewGuestMainScreen.adapter = adapterCards
        linearLayoutManager = LinearLayoutManager(this)
        RecyclerViewGuestMainScreen.layoutManager = linearLayoutManager
    }

    override fun setSwipeRefreshLayoutEnabledStatus() {
        if(linearLayoutManager != null){
            RecyclerViewGuestMainScreen.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    swipeRefreshLayoutGuestMainScreen.isEnabled =
                        linearLayoutManager!!.findFirstVisibleItemPosition() == 0
                }
            })
        }
    }

    override fun setSwipeRefreshLayout() {
        swipeRefreshLayoutGuestMainScreen.setOnRefreshListener{
            Toast.makeText(applicationContext, "Refresh!", Toast.LENGTH_SHORT).show()
        }

        swipeRefreshLayoutGuestMainScreen?.setColorSchemeResources(R.color.white,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark)
    }

    override fun setDrawerLayoutListener(){
        drawerLayoutGuestMainScreen.addDrawerListener(object: DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(p0: Int) {

            }

            override fun onDrawerSlide(p0: View, p1: Float) {

            }

            override fun onDrawerClosed(p0: View) {

            }

            override fun onDrawerOpened(p0: View) {

            }

        })
    }


    override fun setNavigationViewListener(){
        navigationViewGuestMainScreen.menu.clear()
        navigationViewGuestMainScreen.inflateMenu(R.menu.guest_drawer_menu)
        navigationViewGuestMainScreen.setNavigationItemSelectedListener { menuItem: MenuItem ->
            menuItem.isChecked = true
            drawerLayoutGuestMainScreen.closeDrawers()
            val id = menuItem.itemId

            when (id) {
                R.id.login_nav -> goLoginScreen()
                R.id.description_app_nav -> Toast.makeText(applicationContext, resources.getString(R.string.description),
                    Toast.LENGTH_SHORT).show()
                R.id.licenses_nav -> Toast.makeText(applicationContext, resources.getString(R.string.licenses),
                    Toast.LENGTH_SHORT).show()
                R.id.send_error_nav -> Toast.makeText(applicationContext, resources.getString(R.string.send_error),
                    Toast.LENGTH_SHORT).show()
            }
            false
        }
    }

    override fun setSearchView(){
        searchGuestViewMainScreen.isActivated = true
        searchGuestViewMainScreen.isIconified = false
        searchGuestViewMainScreen.clearFocus()
    }

    override fun setBottomNavigationViewListener(){
        bottomNavigationViewGuestMainScreen.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.app_bar_category -> Toast.makeText(applicationContext, "CATEGORY", Toast.LENGTH_SHORT).show()
            }
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> drawerLayoutGuestMainScreen.openDrawer(GravityCompat.START)
        }

        return true
    }

}

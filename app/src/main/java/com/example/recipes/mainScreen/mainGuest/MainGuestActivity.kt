package com.example.recipes.mainScreen.mainGuest

import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.recipes.R
import com.example.recipes.dagger.activity.ActivityModule
import com.example.recipes.dagger.activity.DaggerActivityComponent
import com.example.recipes.dagger.mainScreen.mainGuest.DaggerMainGuestActivityComponent
import com.example.recipes.dagger.mainScreen.mainGuest.MainGuestActivityComponent
import com.example.recipes.dagger.mainScreen.mainGuest.MainGuestActivityModule
import com.example.recipes.data.model.Card
import com.example.recipes.logIn.LoginActivity
import com.example.recipes.MainCardsAdapter
import com.example.recipes.mainScreen.MainUserViewModel
import com.example.recipes.utils.BottomBarActions
import kotlinx.android.synthetic.main.activity_guest_main.*
import timber.log.Timber
import javax.inject.Inject

class MainGuestActivity : AppCompatActivity() {
    @Inject
    lateinit var linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager

    @Inject
    lateinit var adapterCards: MainCardsAdapter

    @Inject
    lateinit var mainUserViewModel: MainUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_main)

        setDaggerComponent()
        setViewModelObserver()
        setFirstScreen()
    }

    private fun setDaggerComponent(){
        Timber.tag(TAG).d("setDaggerComponent")
        val component: MainGuestActivityComponent = DaggerMainGuestActivityComponent.builder()
            .mainGuestActivityModule(MainGuestActivityModule(this))
            .activityComponent(DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this)).build())
            .build()

        component.injectMainGuestActivity(this)
    }

    private fun setViewModelObserver(){
        Timber.tag(TAG).d("setViewModelObserver")
        mainUserViewModel.getCardList().observe(this, Observer { setRecyclerView(it) })
        mainUserViewModel.getError().observe(this, Observer { showError(it) })
    }

    private fun setFirstScreen(){
        Timber.tag(TAG).d("setFirstScreen")
        setToolbar()
        setNavigationViewListener()
        setBottomNavigationViewListener()
        setSearchView()
        setSwipeRefreshLayout()
    }

    private fun goLoginScreen() {
        Timber.tag(TAG).d("goLoginScreen")
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun setToolbar(){
        Timber.tag(TAG).d("setToolbar")
        setSupportActionBar(toolbarGuestMainScreen)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun setRecyclerView(cardList: List<Card>?) {
        Timber.tag(TAG).d("setRecyclerView")
        if(cardList == null){
            Toast.makeText(applicationContext, "Brak kart do wyświetlenia!", Toast.LENGTH_SHORT).show()
        }else{
            adapterCards.setCardList(cardList)
            setLinearLayoutForRecyclerView()
            setSwipeRefreshLayoutEnabledStatus()
        }
    }

    private fun setLinearLayoutForRecyclerView() {
        Timber.tag(TAG).d("setLinearLayoutForRecyclerView")
        RecyclerViewGuestMainScreen.adapter = adapterCards
        RecyclerViewGuestMainScreen.layoutManager = linearLayoutManager
    }

    private fun setSwipeRefreshLayoutEnabledStatus() {
        Timber.tag(TAG).d("setSwipeRefreshLayoutEnabledStatus")
        RecyclerViewGuestMainScreen.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                swipeRefreshLayoutGuestMainScreen.isEnabled =
                    linearLayoutManager.findFirstVisibleItemPosition() == 0

                if (dy > 0 && bottomNavigationViewGuestMainScreen.isShown){
                    BottomBarActions.hideBottomBar(bottomNavigationViewGuestMainScreen)
                }else if (dy < 0){
                    BottomBarActions.showBottomBar(bottomNavigationViewGuestMainScreen)
                }
            }
        })
    }

    private fun setSwipeRefreshLayout() {
        Timber.tag(TAG).d("setSwipeRefreshLayout")
        swipeRefreshLayoutGuestMainScreen.setOnRefreshListener{
            mainUserViewModel.getCardsFromServer()
            Toast.makeText(applicationContext, "Refresh!", Toast.LENGTH_SHORT).show()
            setFinishRefreshingSwipeRefresh()
        }

        swipeRefreshLayoutGuestMainScreen?.setColorSchemeResources(R.color.white,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark)
    }

    private fun setFinishRefreshingSwipeRefresh() {
        Timber.tag(TAG).d("setFinishRefreshingSwipeRefresh")
        swipeRefreshLayoutGuestMainScreen.isRefreshing = false
    }

    private fun setNavigationViewListener(){
        Timber.tag(TAG).d("setNavigationViewListener")
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

    private fun setSearchView(){
        Timber.tag(TAG).d("setSearchView")
        searchGuestViewMainScreen.isActivated = true
        searchGuestViewMainScreen.isIconified = false
        searchGuestViewMainScreen.clearFocus()
    }

    private fun setBottomNavigationViewListener(){
        Timber.tag(TAG).d("setBottomNavigationViewListener")
        bottomNavigationViewGuestMainScreen.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.app_bar_category -> Toast.makeText(applicationContext, "CATEGORY", Toast.LENGTH_SHORT).show()
            }
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Timber.tag(TAG).d("onOptionsItemSelected")
        when (item!!.itemId) {
            android.R.id.home -> drawerLayoutGuestMainScreen.openDrawer(GravityCompat.START)
        }
        return true
    }

    private fun showError(errorMessageText: String?) {
        Toast.makeText(this, errorMessageText, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "MainGuestActivity"
    }
}

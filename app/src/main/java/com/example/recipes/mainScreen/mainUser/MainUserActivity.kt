package com.example.recipes.mainScreen.mainUser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.recipes.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_user_main.*
import com.example.recipes.R
import com.example.recipes.addRecipe.AddRecipeFragment
import com.example.recipes.dagger.activity.ActivityModule
import com.example.recipes.dagger.activity.DaggerActivityComponent
import com.example.recipes.dagger.mainScreen.mainUser.DaggerMainUserActivityComponent
import com.example.recipes.dagger.mainScreen.mainUser.MainUserActivityComponent
import com.example.recipes.dagger.mainScreen.mainUser.MainUserActivityModule
import com.example.recipes.data.model.Card
import com.example.recipes.logIn.LoginActivity
import com.example.recipes.MainCardsAdapter
import com.example.recipes.utils.BottomBarActions
import com.facebook.Profile
import kotlinx.android.synthetic.main.drawer_header.view.*
import javax.inject.Inject

class MainUserActivity : AppCompatActivity(), MainUserContract.View {
    @Inject
    lateinit var presenter: MainUserContract.Presenter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var adapterCards: MainCardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)

        val component: MainUserActivityComponent = DaggerMainUserActivityComponent.builder()
            .mainUserActivityModule(MainUserActivityModule(this))
            .activityComponent(DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this)).build())
            .build()

        component.injectMainUserActivity(this)

        presenter.setFirstScreen()
    }

    private fun goProfileScreen(){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    override fun goLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun setToolbar(){
        setSupportActionBar(toolbarMainScreen)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    override fun setListeners() {
        addRecipeFab.setOnClickListener{ startAddFragment() }
    }

    private fun startAddFragment(){
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val addRecipeFragment = AddRecipeFragment()
        fragmentTransaction.replace(R.id.fragmentContent, addRecipeFragment)
        fragmentTransaction.commit()
        setAllViewsUnenabled()
    }

    override fun setRecyclerView(cardList: List<Card>?) {
        if(cardList == null){
            Toast.makeText(applicationContext, "Brak kart do wyświetlenia!", Toast.LENGTH_SHORT).show()
        }else{
            adapterCards.setCardList(cardList)
            setLinearLayoutForRecyclerView()
            setSwipeRefreshLayoutEnabledStatus()
        }
    }

    private fun setLinearLayoutForRecyclerView() {
        RecyclerViewMainScreen.adapter = adapterCards
        RecyclerViewMainScreen.layoutManager = linearLayoutManager
    }

    override fun setSwipeRefreshLayoutEnabledStatus() {
        RecyclerViewMainScreen.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                swipeRefreshLayoutMainScreen.isEnabled =
                        linearLayoutManager.findFirstVisibleItemPosition() == 0

                if (dy > 0 && bottomNavigationViewMainScreen.isShown){
                    BottomBarActions.hideBottomBar(bottomNavigationViewMainScreen)
                }else if (dy < 0){
                    BottomBarActions.showBottomBar(bottomNavigationViewMainScreen)
                }
            }
        })
    }

    override fun setSwipeRefreshLayout() {
        swipeRefreshLayoutMainScreen.setOnRefreshListener{
            Toast.makeText(applicationContext, "Refresh!", Toast.LENGTH_SHORT).show()
            setFinishRefreshingSwipeRefresh()
        }

        swipeRefreshLayoutMainScreen?.setColorSchemeResources(R.color.white,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark)
    }

    override fun setFinishRefreshingSwipeRefresh() {
        swipeRefreshLayoutMainScreen.isRefreshing = false
    }

    override fun setNavigationViewListener(){
        setNavigationHeader()
        navigationViewMainScreen.menu.clear()
        navigationViewMainScreen.inflateMenu(R.menu.drawer_menu)
        navigationViewMainScreen.setNavigationItemSelectedListener { menuItem: MenuItem ->
            menuItem.isChecked = true
            drawerLayoutMainScreen.closeDrawers()
            val id = menuItem.itemId

            when (id) {
                R.id.added_recipes_nav -> Toast.makeText(applicationContext, resources.getString(R.string.added_recipes),
                    Toast.LENGTH_SHORT).show()
                R.id.saved_recipes_nav -> Toast.makeText(applicationContext, resources.getString(R.string.saved_recipes),
                    Toast.LENGTH_SHORT).show()
                R.id.description_app_nav -> Toast.makeText(applicationContext, resources.getString(R.string.description),
                    Toast.LENGTH_SHORT).show()
                R.id.licenses_nav -> Toast.makeText(applicationContext, resources.getString(R.string.licenses),
                    Toast.LENGTH_SHORT).show()
                R.id.send_error_nav -> Toast.makeText(applicationContext, resources.getString(R.string.send_error),
                    Toast.LENGTH_SHORT).show()
                R.id.logout_nav -> presenter.logout()

            }
            false
        }
    }

    private fun setNavigationHeader(){
        val profile = Profile.getCurrentProfile()
        if(profile != null){
            val name = profile.name
            val photoUrl = profile.getProfilePictureUri(100, 100).toString()

            navigationViewMainScreen.getHeaderView(0).nameHeaderTextView.text = name
            Glide.with(applicationContext)
                .load(photoUrl)
                .into(navigationViewMainScreen.getHeaderView(0).avatarImageView)
        }else{
            Profile.fetchProfileForCurrentAccessToken()
        }
    }

    override fun setSearchView(){
        searchViewMainScreen.isActivated = true
        searchViewMainScreen.isIconified = false
        searchViewMainScreen.clearFocus()
    }


    override fun setBottomNavigationViewListener(){
        bottomNavigationViewMainScreen.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.app_bar_profile -> goProfileScreen()
                R.id.app_bar_category -> Toast.makeText(applicationContext, "CATEGORY", Toast.LENGTH_SHORT).show()
                R.id.app_bar_friends -> Toast.makeText(applicationContext, "FRIENDS", Toast.LENGTH_SHORT).show()
                R.id.app_bar_settings -> Toast.makeText(applicationContext, "SETTINGS", Toast.LENGTH_SHORT).show()
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_bar_notification -> Toast.makeText(applicationContext, "NOTIFICATIONS", Toast.LENGTH_SHORT).show()
            android.R.id.home -> drawerLayoutMainScreen.openDrawer(GravityCompat.START)
        }
        return true
    }

    override fun setAllViewsEnabled() {
        toolbarMainScreen.isClickable = true
        bottomNavigationViewMainScreen.isClickable = true
        RecyclerViewMainScreen.isClickable = true
    }

    override fun setAllViewsUnenabled() {
        toolbarMainScreen.isClickable = false
        bottomNavigationViewMainScreen.isClickable = false
        RecyclerViewMainScreen.isClickable = false
    }
}

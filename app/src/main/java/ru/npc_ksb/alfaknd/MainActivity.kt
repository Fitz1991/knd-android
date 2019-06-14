package ru.npc_ksb.alfaknd

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SlidingPaneLayout
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import ru.npc_ksb.alfaknd.fragments.*
import ru.npc_ksb.alfaknd.sidebar.SidebarItems


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mSlidingLayout: SlidingPaneLayout = findViewById(R.id.sliding_pane_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setIcon(R.mipmap.ic_alfaknd)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        toolbar.setNavigationOnClickListener {
            if (mSlidingLayout.isOpen) {
                mSlidingLayout.closePane()
            } else {
                mSlidingLayout.openPane()
            }
        }

        val listView = findViewById<ListView>(android.R.id.list)
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val tx = supportFragmentManager.beginTransaction()
            when (position) {
                SidebarItems.INSPECTIONS.ordinal -> {
                    tx.replace(R.id.fragment_container, BlankFragment0())
                }
                SidebarItems.RAIDS.ordinal -> {
                    tx.replace(R.id.fragment_container, BlankFragment1())
                }
                SidebarItems.PREVENTIONS.ordinal -> {
                    tx.replace(R.id.fragment_container, BlankFragment2())
                }
                SidebarItems.CATALOG.ordinal -> {
                    tx.replace(R.id.fragment_container, BlankFragment3())
                }
            }
            tx.addToBackStack(null)
            tx.commit()
        }
        val tx = supportFragmentManager.beginTransaction()
        tx.replace(R.id.fragment_container, DashboardFragment())
        tx.addToBackStack(null)
        tx.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    fun onQRCodeClick(v: View) {
        val intent = Intent(this, QRCodeActivity::class.java)
        startActivity(intent)
    }
}
package com.example.scheduleapp.ui.event.update

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.example.scheduleapp.R
import com.example.scheduleapp.data.model.Event
import com.example.scheduleapp.databinding.ActivityUpdateEventBinding
import com.example.scheduleapp.ui.event.EventParentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateEventActivity : AppCompatActivity(R.layout.activity_update_event) {

    private val eventParentViewModel by viewModels<EventParentViewModel>()

    private lateinit var binding: ActivityUpdateEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            createNavHostFragment(intent.getParcelableExtra(ARG_SELECT_ITEM))
        addNavHostToContainer(navHostFragment)
    }

    private fun createNavHostFragment(event: Event?) = NavHostFragment.create(
        R.navigation.nav_update_event,
        bundleOf(ARG_SELECT_ITEM to event)
    )

    private fun addNavHostToContainer(navHostFragment: NavHostFragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commit()
    }

    companion object {
        fun createIntent(context: Context, event: Event): Intent {
            return Intent(context, UpdateEventActivity::class.java).apply {
                putExtra(ARG_SELECT_ITEM, event)
            }
        }
    }
}
package com.skyline.practice.ui.song

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.tabs.TabLayoutMediator
import com.hi.dhl.binding.viewbind
import com.skyline.practice.R
import com.skyline.practice.databinding.ActivitySongBinding
import com.skyline.practice.getLifecycleOwner
import com.skyline.practice.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SongActivity: BaseActivity<ActivitySongBinding, SongViewModel>() {

    companion object {
        const val KEY_SONG = "key_song"
    }

    override val mBinding: ActivitySongBinding by viewbind()
    override val mModel: SongViewModel by viewModel()

    private val tabs: Array<String>
        get() = arrayOf(
            "Text",
            "Image",
            "Video"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setIcon(R.drawable.ic_note)
            setDisplayHomeAsUpEnabled(true)
        }

        with(mModel) {
            song.observe(getLifecycleOwner(), {
                supportActionBar?.apply {
                    title = it.name
                }
            })
        }

        intent.extras?.getString(KEY_SONG)
            ?.let { songId ->
                with(mBinding) {
                    pager.apply {
                        adapter = SongDetailAdapter(this@SongActivity, songId)
                    }
                    TabLayoutMediator(tabMenu, pager) { tab, position ->
                        tab.text = tabs[position]
                    }.attach()
                }
                mModel.getData(songId)
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
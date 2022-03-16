package com.skyline.practice.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hi.dhl.binding.viewbind
import com.skyline.practice.databinding.ActivityHomeBinding
import com.skyline.practice.getLifecycleOwner
import com.skyline.practice.show
import com.skyline.practice.ui.base.BaseActivity
import com.skyline.practice.ui.song.SongActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity: BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override val mBinding: ActivityHomeBinding by viewbind()
    override val mModel: HomeViewModel by viewModel()

    private val mAdapter: SongListAdapter by lazy {
        SongListAdapter(mModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(mAdapter) {
            clickEvent.subscribe {
                show(
                    SongActivity::class.java,
                    Bundle().apply {
                        putString(SongActivity.KEY_SONG, it.id)
                    }
                )
            }.let {
                mDisposables.add(it)
            }
        }

        with(mBinding.list) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
            adapter = mAdapter
        }

        with(mModel) {
            isEmpty.observe(getLifecycleOwner(), {
                with(mBinding) {
                    if (it == false) {
                        list.visibility = View.VISIBLE
                        noData.visibility = View.GONE
                    } else {
                        list.visibility = View.GONE
                        noData.visibility = View.VISIBLE
                    }
                    progress.visibility = View.GONE
                }
            })
            data.observe(getLifecycleOwner(), {
                mAdapter.notifyDataSetChanged()
            })
            getData()
        }
    }
}
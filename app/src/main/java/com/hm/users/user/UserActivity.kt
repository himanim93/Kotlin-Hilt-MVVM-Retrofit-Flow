package com.hm.users.user

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.hm.users.R
import com.hm.users.base.BaseActivity
import com.hm.users.data.model.UserDetails
import com.hm.users.databinding.ActivityUserBinding
import com.hm.users.remote.Resource
import com.hm.users.user.adapter.UserAdapter
import com.hm.users.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserActivity : BaseActivity() {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityUserBinding

    @Inject
    lateinit var userAdapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(Color.WHITE, StatusIconColorType.Light)
    }

    override fun initViewBinding() {
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun observeViewModel() {
        observe(viewModel.getUserResult, ::setUserData)
        observe(viewModel.showError, ::showError)
        observe(viewModel.loading, ::showLoadingView)
    }

    private fun showError(@StringRes errorStr: Int?) {
        errorStr?.let {
            if (errorStr == AppData.Error.NO_INTERNET_CONNECTION) {
                showNoInterView()
            } else
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUserData(status: Resource<UserDetails>) {
        when (status) {
            is Resource.Success -> status.data?.let { setData(it) }
            is Resource.DataError -> {
                showError(status.errorString)
            }
        }
    }

    private fun showLoadingView(isLoading: Boolean) {
        binding.swipeRefresh.isRefreshing = false
        if (isLoading) {
            binding.progressView.visibility = View.VISIBLE
            binding.grpNoData.visibility = View.GONE
            binding.rvUser.visibility = View.GONE
        } else binding.progressView.visibility = View.GONE
    }

    private fun showNoDataView() {
        binding.swipeRefresh.isRefreshing = false
        binding.progressView.visibility = View.GONE
        binding.grpNoData.visibility = View.VISIBLE
        binding.rvUser.visibility = View.GONE
    }

    private fun showNoInterView() {
        binding.swipeRefresh.isRefreshing = false
        binding.progressView.visibility = View.GONE
        binding.grpNoData.visibility = View.GONE
        binding.rvUser.visibility = View.GONE
        binding.grpNoInternet.visibility = View.VISIBLE
    }

    private fun setData(data: UserDetails) {
        binding.swipeRefresh.isRefreshing = false

        if (data.users.isEmpty()) {
            showNoDataView()
        } else {
            if (viewModel.empList.isEmpty()) viewModel.empList = data.users

            binding.progressView.visibility = View.GONE
            binding.grpNoData.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE

            userAdapter.updateUser(data.users)
            binding.rvUser.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = userAdapter
            }
        }
    }

    override fun onActionPerform() {
        super.onActionPerform()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getUserList()
        }

        binding.edtEmpSearch.afterTextChanged {
            if (NetworkUtils.isConnected()) {
                viewModel.applySearching(it)
            } else {
                Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
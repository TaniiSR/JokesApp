package com.task.joke.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import com.task.joke.R
import com.task.joke.databinding.ActivitySplashBinding
import com.task.joke.ui.homemain.HomeMainActivity
import com.task.joke.utils.SharedPreferenceManager
import com.task.joke.utils.base.BaseActivity
import com.task.joke.utils.extensions.hideSystemUI
import com.task.joke.utils.extensions.showSystemUI
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, ISplash.State, ISplash.ViewModel>(),
    ISplash.View {
    override fun getViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    @Inject
    lateinit var sharedPreference: SharedPreferenceManager

    override val viewModel: ISplash.ViewModel by viewModels<SplashVM>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI(mViewBinding.root)
        initCountDownTimer(3)
        val count = sharedPreference.getAppCount().plus(1)
        sharedPreference.setAppCount(count)
        mViewBinding.tvTitle.text =
            getString(R.string.screen_splash_display_text_title, count.toString())
    }

    private fun initCountDownTimer(time: Int) {
        object : CountDownTimer(time.times(1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) = Unit

            override fun onFinish() {
                startHomeActivity()
            }
        }.start()
    }

    private fun startHomeActivity() {
        showSystemUI(mViewBinding.root)
        val intent = Intent(this@SplashActivity, HomeMainActivity::class.java)
        activityLauncher.launch(intent)
        finish()
    }
}
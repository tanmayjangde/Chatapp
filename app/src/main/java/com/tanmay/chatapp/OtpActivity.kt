package com.tanmay.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_otp.*

const val PHONE_NUMBER = "phoneNumber"
class OtpActivity : AppCompatActivity() {
    var phoneNumber:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        initViews()
        showTimer(60000)
    }

    private fun showTimer(milliSecInFuture: Long) {
        resendCodeBtn.isEnabled=false
        object :CountDownTimer(milliSecInFuture,1000){
            override fun onTick(milliSecUntilFinished: Long) {
                counterTv.isVisible = true
                counterTv.text = getString(R.string.secondsRemaining,milliSecUntilFinished/1000)
            }

            override fun onFinish() {
                counterTv.isVisible = false
                resendCodeBtn.isEnabled=true
            }
        }.start()
    }

    private fun initViews() {
        phoneNumber=intent.getStringExtra(PHONE_NUMBER)
        verify.setText("""Verify $phoneNumber""")
        setSpannableString()
    }

    private fun setSpannableString() {
        val span = SpannableString("Waiting to automatically detect an otp sent to ${phoneNumber}. Wrong number?")
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = ds.linkColor
            }
        }
        span.setSpan(clickableSpan,span.length-13,span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        waitingTv.movementMethod = LinkMovementMethod.getInstance()
        waitingTv.text = span
    }

    private fun showLoginctivity(){
        startActivity(Intent(this,LoginActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

    override fun onBackPressed() {}
}
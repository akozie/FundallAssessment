package com.example.fundallassessment.utils

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.fundallassessment.R
import com.example.fundallassessment.domain.models.UserProfile
import com.example.fundallassessment.presentation.viewState.Resource

@BindingAdapter("android:setUserImage")
fun ImageView.setUserImage(imageUrl: String?) {
    imageUrl?.let {
        if (it.isEmpty()) {
            Log.d("USER_IMAGE_1", "GOT HERE")
            setBackgroundResource(R.drawable.user_image)
        } else {
            Log.d("USER_IMAGE", "HEREEEE+++")
            load(it)
        }
    }
}

@BindingAdapter("android:formatAmountWithCurrency")
fun TextView.formatAmountWithCurrency(amount: String?) {
    amount?.let {
        text = context.getString(R.string.currency_symbol, it)
    }
}

@BindingAdapter("android:setBalance")
fun TextView.setBalance(userProfileDetails: Resource<UserProfile>?){
    userProfileDetails?.let {
        if (it is Resource.Success){
            formatAmountWithCurrency(it.data?.totalBalance)
        }
    }
}
@BindingAdapter("android:setIncome")
fun TextView.setIncome(userProfileDetails: Resource<UserProfile>?){
    userProfileDetails?.let {
        if (it is Resource.Success){
            formatAmountWithCurrency(it.data?.income)
        }
    }
}
@BindingAdapter("android:setSpent")
fun TextView.setSpent(userProfileDetails: Resource<UserProfile>?){
    userProfileDetails?.let {
        if (it is Resource.Success){
            formatAmountWithCurrency(it.data?.spent)
        }
    }
}

@BindingAdapter("android:setProfileImage")
fun ImageView.setProfileImage(userProfileDetails: Resource<UserProfile>?){
    userProfileDetails?.let {
        if (it is Resource.Success){
            setUserImage(it.data?.profileImage)
        }
    }
}
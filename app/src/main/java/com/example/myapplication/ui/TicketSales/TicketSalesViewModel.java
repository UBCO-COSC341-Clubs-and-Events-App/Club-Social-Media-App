package com.example.myapplication.ui.TicketSales;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TicketSalesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TicketSalesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Sales by Day");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
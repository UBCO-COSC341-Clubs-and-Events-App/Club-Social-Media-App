package com.example.myapplication.ui.EventsClub;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventsClubViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EventsClubViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.example.myapplication.ui.EventsStudent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventsStudentViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EventsStudentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
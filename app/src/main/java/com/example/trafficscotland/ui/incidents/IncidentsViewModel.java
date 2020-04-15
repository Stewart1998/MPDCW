package com.example.trafficscotland.ui.incidents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IncidentsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IncidentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Incident's fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
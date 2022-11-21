package com.test.medicalpanel.Activity.Interface;

import com.test.medicalpanel.Activity.Model.DataSlot;

import java.util.List;

public interface IDataSlotLoadListener {
    void onDataSlotLoadSuccess(List<DataSlot> dataSlotList);
    void onDataSlotLoadFailed(String message);
    void onDataSlotLoadEmpty();

}

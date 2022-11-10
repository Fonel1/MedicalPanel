package com.test.medicalpanel.Activity.Interface;

import java.util.List;

public interface IALLSalonLoadListener {
    void onAllSalonLoadSuccess(List<String> areaNameList);
    void onAllSalonLoadFailed(String message);
}

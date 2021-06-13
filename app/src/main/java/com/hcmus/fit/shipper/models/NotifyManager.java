package com.hcmus.fit.shipper.models;

import java.util.ArrayList;

public class NotifyManager {
    private static NotifyManager instance = null;

    private final ArrayList<NotifyModel> notifyList = new ArrayList<>();

    private NotifyManager() {

    }

    public static NotifyManager getInstance() {
        if (instance == null) {
            instance = new NotifyManager();

            NotifyModel notifyModel = new NotifyModel("", "","");
            instance.addNotifyModel(notifyModel);
            instance.addNotifyModel(notifyModel);
            instance.addNotifyModel(notifyModel);
            instance.addNotifyModel(notifyModel);
        }

        return instance;
    }

    public int getNotifyListSize() {
        return this.notifyList.size();
    }

    public NotifyModel getNotify(int index) {
        return this.notifyList.get(index);
    }

    public void addNotifyModel(NotifyModel notifyModel) {
        this.notifyList.add(notifyModel);
    }
}

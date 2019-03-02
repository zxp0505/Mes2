package com.zjyk.repair.ui.present;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import com.zjyk.repair.ui.views.RPBaseView;

/**
 * Created by zjgz on 2017/11/2.
 */

public  class RPRxPresent<T extends RPBaseView> extends RPMvpBasePresenter<T> {
    List<Subscription> subscriptionList =new ArrayList<>();
    void addSubscription(Subscription subscription){
        if(!subscriptionList.contains(subscription)){
            subscriptionList.add(subscription);
        }
//        RxApiManager.get().add("",subscription);
    }

    void removeSubscription(){
        for (int i = 0; i < subscriptionList.size(); i++) {
            Subscription subscription = subscriptionList.get(i);
            if(subscription != null && !subscription.isUnsubscribed()){
                subscription.unsubscribe();
            }
        }

    }

}

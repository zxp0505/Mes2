package workstation.zjyk.line.ui.present;

import workstation.zjyk.line.ui.views.BaseView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by zjgz on 2017/11/2.
 */

public  class RxPresent<T extends BaseView> extends MvpBasePresenter<BaseView> {
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

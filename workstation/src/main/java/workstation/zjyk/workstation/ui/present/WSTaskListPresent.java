package workstation.zjyk.workstation.ui.present;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSTray;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTask;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTaskListBean;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTaskTableCount;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTaskTypeEnum;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSErrorCode;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSTaskListView;

/**
 * Created by zjgz on 2017/12/7.
 */

public class WSTaskListPresent extends WSRxPresent<WSTaskListView> {

    private final WSTaskRequest mTaskRequest;

    public WSTaskListPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void requestTaskListCount(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        mTaskRequest.requestTaskListCount(params, getView(), new WSRxDataCallBack<WSWorkStationTaskTableCount>() {
            @Override
            public void onSucess(WSWorkStationTaskTableCount wsWorkStationTaskTableCount) {
                if (getView() != null) {
                    getView().showTaskListCount(wsWorkStationTaskTableCount);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showTaskListCount(null);
                }
            }
        }, isShowLoading);
    }
    public void requestTaskList(WSTaskTypeEnum type, int pageNo, int pageSize, String searchText, String searchDate,String  dateType, boolean isShowLoad) {
        Map<String, String> params = new HashMap<>();
        params.put("taskType", type + "");
        params.put("pageNumber", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("searchText", searchText);
        params.put("searchDate", searchDate);
        params.put("dateType", dateType);
        mTaskRequest.requestTaskList(params, getView(), new WSRxDataCallBack<WSWorkStationTaskListBean>() {
            @Override
            public void onSucess(WSWorkStationTaskListBean wsWorkStationTasks) {
                if (getView() != null) {
                    if (wsWorkStationTasks != null) {
                        wsWorkStationTasks.setType(type);
                    }
                    if (pageNo == 1) {
                        getView().showFirstData(wsWorkStationTasks);
                    } else {
                        getView().loadMoreData(wsWorkStationTasks);
                    }
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().loadError();
                }
            }
        }, isShowLoad);
//        Observable.create(new ObservableOnSubscribe<List<WSMyTaskBean>>() {
//            @Override
//            public void subscribe(ObservableEmitter<List<WSMyTaskBean>> e) throws Exception {
//                List<WSMyTaskBean> list = new ArrayList<>();
//                for (int i = 0; i < pageSize; i++) {
//                    WSMyTaskBean bean = new WSMyTaskBean();
//                    bean.setCount(1);
//                    bean.setOrder("order" + ((pageNo - 1) * pageSize + i + 1));
//                    bean.setProcedure("kkk");
//                    if (i % 2 == 0) {
//
//                        bean.setWorkStationCondition(0);
//                    } else {
//                        bean.setWorkStationCondition(1);
//                    }
//                    list.add(bean);
//                }
//                e.onNext(list);
//                e.onComplete();
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<WSMyTaskBean>>() {
//            @Override
//            public void accept(List<WSMyTaskBean> wsMyTaskBeans) throws Exception {
//                if (getView() != null) {
//                    if (pageNo == 1) {
//                        ((WSTaskListView)getView()).showFirstData(wsMyTaskBeans);
//                    } else {
//                        ((WSTaskListView)getView()).loadMoreData(wsMyTaskBeans);
//                    }
//                }
//            }
//        });

    }

    public void getBarCodeStatus(String scanResult) {
        Map<String, String> params = new HashMap<>();
        params.put("code", scanResult);
        mTaskRequest.getBarcodeStatus(params, getView(), new WSRxDataCallBack<WSTrayLoadInfo>() {
            @Override
            public void onSucess(WSTrayLoadInfo trayLoadInfo) {
                if (getView() != null) {
                    if (trayLoadInfo != null && (WSErrorCode.CODE_RECIVERED.equals(trayLoadInfo.getCode()) || WSErrorCode.CODE_PRODUCT.equals(trayLoadInfo.getCode()))) {
                        if (trayLoadInfo.getTray() == null) {
                            WSTray wsTray = new WSTray();
                            trayLoadInfo.setTray(wsTray);
                        }
                        trayLoadInfo.getTray().setOneDemensionCode(scanResult);
                    }
                    getView().showScanResult(trayLoadInfo);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showScanResult(null);
                }
            }
        }, true);

    }

    //获取物料清单
    public void getMaterailBill(Map<String, String> params) {

        mTaskRequest.getMaterailDetail(params, getView(), new WSRxDataCallBack<List<WSMaterial>>() {
            @Override
            public void onSucess(List<WSMaterial> materials) {
                if (getView() != null) {
                    getView().showMaterailBill(materials);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showMaterailBill(null);
                }
            }
        }, true);
    }

    public void requestDateList(Map<String, String> params) {

        mTaskRequest.requestDateList(params, getView(), new WSRxDataCallBack<List<String>>() {
            @Override
            public void onSucess(List<String> dateList) {
                if (getView() != null) {
                    getView().showDateList(dateList);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showDateList(null);
                }
            }
        }, true);
    }

    public void requesStationTrasferList(Map<String, String> params, WSWorkStationTask wsWorkStationTask) {
        mTaskRequest.requesStationTrasferList(params, getView(), new WSRxDataCallBack<List<WSWorkStationInfo>>() {
            @Override
            public void onSucess(List<WSWorkStationInfo> wsWorkStationInfos) {
                if (getView() != null) {
                    getView().showStationList(wsWorkStationInfos, wsWorkStationTask);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showStationList(null, wsWorkStationTask);
                }
            }
        }, true);
    }
}

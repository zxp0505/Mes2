package workstation.zjyk.workstation.modle.request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSBaseResultCommon;
import workstation.zjyk.workstation.modle.bean.WSBeginOrEnd;
import workstation.zjyk.workstation.modle.bean.WSExceptionOutRecordVo;
import workstation.zjyk.workstation.modle.bean.WSFollowedBean;
import workstation.zjyk.workstation.modle.bean.WSInputInfo;
import workstation.zjyk.workstation.modle.bean.WSLabelBean;
import workstation.zjyk.workstation.modle.bean.WSMaintainReason;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSMaterialTray;
import workstation.zjyk.workstation.modle.bean.WSOrderCheckVo;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepair;
import workstation.zjyk.workstation.modle.bean.WSReviewWorkStationVo;
import workstation.zjyk.workstation.modle.bean.WSReworkTrayInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskOtherInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskProductCheckTray;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSTrayVo;
import workstation.zjyk.workstation.modle.bean.WSValidateProcessInstructionVo;
import workstation.zjyk.workstation.modle.bean.WSWipHistory;
import workstation.zjyk.workstation.modle.bean.WSWipTray;
import workstation.zjyk.workstation.modle.bean.WSWorkStationInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTask;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTaskListBean;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTaskTableCount;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.download.DownloadProgressListener;
import workstation.zjyk.workstation.modle.net.WSApiManager;
import workstation.zjyk.workstation.modle.net.WSChildHttpResultObsever;
import workstation.zjyk.workstation.modle.net.WSErrorCode;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.ui.views.WSBaseView;
import workstation.zjyk.workstation.util.WSURLBuilder;

/**
 * Created by zjgz on 2017/12/20.
 */

public class WSTaskRequest {
    /**
     * 请求任务列表
     *
     * @param params
     * @param baseView
     * @param callBack
     */
    public void requestTaskList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSWorkStationTaskListBean> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
//        WSURLBuilder.GET_TASK_LIST  POST
        WSApiManager.getInstance().post(WSURLBuilder.GET_TASK_LIST, params, baseView.<WSWorkStationTaskListBean>bindToLife(), new WSChildHttpResultObsever<List<WSWorkStationTask>>(baseView, WSWorkStationTask.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSWorkStationTask> wsWorkStationTasks, int total) {
                super._onSuccess(wsWorkStationTasks, total);
                WSWorkStationTaskListBean bean = new WSWorkStationTaskListBean();
                bean.setWsWorkStationTasks(wsWorkStationTasks);
                bean.setTotal(total);
                callBack.onSucess(bean);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _onSuccess(List<WSWorkStationTask> wsWorkStationTasks) {
//                callBack.onSucess(wsWorkStationTasks);
//                if (isShowLoad) {
//                    baseView.hideLoadingDialog();
//                }
            }


        });
    }

    /**
     * 请求巡检订单列表
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestInspectTaskList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSOrderCheckVo>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
//        WSURLBuilder.GET_TASK_LIST  POST
        WSApiManager.getInstance().post(WSURLBuilder.GET_INSPECT_LIST, params, baseView.<List<WSOrderCheckVo>>bindToLife(), new WSChildHttpResultObsever<List<WSOrderCheckVo>>(baseView, WSOrderCheckVo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSOrderCheckVo> wsWorkStationTasks, int total) {
                super._onSuccess(wsWorkStationTasks, total);
//                WSWorkStationTaskListBean bean = new WSWorkStationTaskListBean();
//                bean.setWsWorkStationTasks(wsWorkStationTasks);
//                bean.setTotal(total);
//                callBack.onSucess(wsWorkStationTasks);
//                if (isShowLoad) {
//                    baseView.hideLoadingDialog();
//                }
            }

            @Override
            public void _onSuccess(List<WSOrderCheckVo> wsWorkStationTasks) {
                callBack.onSucess(wsWorkStationTasks);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    public void requestTaskListCount(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSWorkStationTaskTableCount> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_TASK_LIST_COUNT, params, baseView.<WSWorkStationTaskTableCount>bindToLife(), new WSChildHttpResultObsever<WSWorkStationTaskTableCount>(baseView, WSWorkStationTaskTableCount.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }


            @Override
            public void _onSuccess(WSWorkStationTaskTableCount wsWorkStationTaskTableCount) {
                callBack.onSucess(wsWorkStationTaskTableCount);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 扫码区分接收物料 、在制品
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void getBarcodeStatus(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSTrayLoadInfo> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_TRAY_INFO, params, baseView.<WSTrayLoadInfo>bindToLife(), new WSChildHttpResultObsever<WSTrayLoadInfo>(baseView, WSTrayLoadInfo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSTrayLoadInfo info) {
//                callBack.onSucess(info);
//                if (isShowLoad) {
//                    baseView.hideLoadingDialog();
//                }
            }

            @Override
            public void _onSuccess(WSBaseResultCommon<WSTrayLoadInfo> baseResultCommon) {
                super._onSuccess(baseResultCommon);
                WSTrayLoadInfo data = baseResultCommon.getData();
                if (WSErrorCode.CODE_RECIVERED.equals(baseResultCommon.getCode()) || WSErrorCode.CODE_PRODUCT.equals(baseResultCommon.getCode())) {
                    if (data == null) {
                        data = new WSTrayLoadInfo();
                    }
                    data.setCode(baseResultCommon.getCode());
                }
                callBack.onSucess(data);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 接收物料检查托盘
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void reciverCheckTray(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.RECIVER_MATERAIL_CHECK_TRAY, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 获取日期列表
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestDateList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<String>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_TASK_DATE_LIST, params, baseView.<List<String>>bindToLife(), new WSChildHttpResultObsever<List<String>>(baseView, String.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<String> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    public void getMaterailDetail(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSMaterial>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_STATION_MATERAIL_DETAIL, params, baseView.<List<WSMaterial>>bindToLife(), new WSChildHttpResultObsever<List<WSMaterial>>(baseView, WSMaterial.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSMaterial> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }


    /**
     * 接收物料
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void reciverMaterail(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner errorResultClickListner) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.RECIVER_MATERAIL, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, errorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 接收在制品
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void reciverMaking(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner errorResultClickListner) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.RECIVER_MAKING, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, errorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 接收物料和在制品
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void reciverMaterailAndMaking(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.RECIVER_MATERAIL_AND_MAKING, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 获取工位的主要信息
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestTaskMainInfo(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSTaskMainInfo> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_WS_INFO, params, baseView.<WSTaskMainInfo>bindToLife(), new WSChildHttpResultObsever<WSTaskMainInfo>(baseView, WSTaskMainInfo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSTaskMainInfo info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 获取工位的其他信息 目标 输出 可输出
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestTaskOtherInfo(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSTaskOtherInfo> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_WS_OTHER_INFO, params, baseView.<WSTaskOtherInfo>bindToLife(), new WSChildHttpResultObsever<WSTaskOtherInfo>(baseView, WSTaskOtherInfo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSTaskOtherInfo info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }


    /**
     * 获取关注列表
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void getFollowData(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSFollowedBean>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_BOM_FOLLOWED, params, baseView.<List<WSFollowedBean>>bindToLife(), new WSChildHttpResultObsever<List<WSFollowedBean>>(baseView, WSFollowedBean.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSFollowedBean> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 请求工位的物料在制品列表
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestTaskMaterailAndMakingList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSInputInfo> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_WS_MATERAIL_MAKING_LIST, params, baseView.<WSInputInfo>bindToLife(), new WSChildHttpResultObsever<WSInputInfo>(baseView, WSInputInfo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSInputInfo info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 请求托盘中的物料列表
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestTrayMaterailList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSTrayVo>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_WS_TRAY_MATERAIL_LIST, params, baseView.<List<WSTrayVo>>bindToLife(), new WSChildHttpResultObsever<List<WSTrayVo>>(baseView, WSTrayVo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSTrayVo> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 请求托盘中的在制品列表
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestTrayMakingList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSTrayLoadInfo>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_WS_TRAY_MAKING_LIST, params, baseView.<List<WSTrayLoadInfo>>bindToLife(), new WSChildHttpResultObsever<List<WSTrayLoadInfo>>(baseView, WSTrayLoadInfo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSTrayLoadInfo> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 请求物料托盘列表
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestMaterailTrayList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSMaterialTray> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_WS_MATERAIL_TRAY_LIST, params, baseView.<WSMaterialTray>bindToLife(), new WSChildHttpResultObsever<WSMaterialTray>(baseView, WSMaterialTray.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSMaterialTray info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 请求在制品托盘列表
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestMakingTrayList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSWipTray> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_WS_MAKING_TRAY_LIST, params, baseView.<WSWipTray>bindToLife(), new WSChildHttpResultObsever<WSWipTray>(baseView, WSWipTray.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSWipTray info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 请求bom清单信息
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestBomInfo(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSTrayLoadInfo> callBack, boolean isShowLoad) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_BOM_INFO, params, baseView.<WSTrayLoadInfo>bindToLife(), new WSChildHttpResultObsever<WSTrayLoadInfo>(baseView, WSTrayLoadInfo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSTrayLoadInfo info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 请求暂停播放
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestStartPause(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSBeginOrEnd> callBack, boolean isShowLoad, WSErrorResultClickListner errorResultClickListner) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.OPREAT_STATION_STATUS, params, baseView.<WSBeginOrEnd>bindToLife(), new WSChildHttpResultObsever<WSBeginOrEnd>(baseView, WSBeginOrEnd.class, errorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSBeginOrEnd info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }


    /**
     * 请求返工扫描托盘 获取托盘信息
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestReworkTrayInfo(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSReworkTrayInfo> callBack, boolean isShowLoad) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.REVIEWWROK_SCAN_TRAY, params, baseView.<WSReworkTrayInfo>bindToLife(), new WSChildHttpResultObsever<WSReworkTrayInfo>(baseView, WSReworkTrayInfo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSReworkTrayInfo info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 返工前请求返回的工位
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestReviewWorkStation(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSReviewWorkStationVo>> callBack, boolean isShowLoad) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.REVIEWWORK_GET_STATION_INFO, params, baseView.<List<WSReviewWorkStationVo>>bindToLife(), new WSChildHttpResultObsever<List<WSReviewWorkStationVo>>(baseView, WSReviewWorkStationVo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSReviewWorkStationVo> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 请求返工
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestRework(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.REVIEWWORK, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    public void requestReviewHistory(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSReturnOrRepair> callBack, boolean isShowLoad) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_REVIEWWORK_HISTORY, params, baseView.<WSReturnOrRepair>bindToLife(), new WSChildHttpResultObsever<WSReturnOrRepair>(baseView, WSReturnOrRepair.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSReturnOrRepair info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }


    /**
     * 确认维修
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestRepair(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.REPAIR_CONFIRM, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    public void printLabelTypes(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.PRINT_LABEL, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, String.class, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    public void requestLabelTypes(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSLabelBean>> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.REQUEST_LABEL_TYPES, params, baseView.<List<WSLabelBean>>bindToLife(), new WSChildHttpResultObsever<List<WSLabelBean>>(baseView, WSLabelBean.class, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSLabelBean> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 获取维修历史记录
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestRepairHistory(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSReturnOrRepair> callBack, boolean isShowLoad) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_REAPIR_HISTORY, params, baseView.<WSReturnOrRepair>bindToLife(), new WSChildHttpResultObsever<WSReturnOrRepair>(baseView, WSReturnOrRepair.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSReturnOrRepair info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 获取投递记录
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestDeliveryRecord(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSWipHistory> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_DELIVELY_RECORD, params, baseView.<WSWipHistory>bindToLife(), new WSChildHttpResultObsever<WSWipHistory>(baseView, WSWipHistory.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSWipHistory info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 请求报验托盘列表
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestInspectionData(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSTaskProductCheckTray>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_INSPECTION_TRAY_LIST, params, baseView.<List<WSTaskProductCheckTray>>bindToLife(), new WSChildHttpResultObsever<List<WSTaskProductCheckTray>>(baseView, WSTaskProductCheckTray.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSTaskProductCheckTray> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 校验报验码
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void checkInspectionCode(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.CHECK_INSPECTION_TRAY, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    public void toMakeSureInspect(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.UPDATE_INSPECT_STATUS, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 发送警告
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void toSendWran(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.TO_WARN, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    public void toInspection(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.TO_INSPECTION, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 在制品输出绑定托盘
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requesBindTray(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.MAKING_OUT_BIND_TRAY, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    public void requestReairReasons(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSMaintainReason>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.REPAIR_RESEON_TYPE, params, baseView.<List<WSMaintainReason>>bindToLife(), new WSChildHttpResultObsever<List<WSMaintainReason>>(baseView, WSMaintainReason.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }


            @Override
            public void _onSuccess(List<WSMaintainReason> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 投递在制品托盘
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requesMakingDelivery(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.MAKING_OUT_DELIVERY, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    public void requestPrintDelivery(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_DELIVELY_PRINT, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 工序获取工步列表
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requesStepList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSProcedureStep>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_STEP_LIST, params, baseView.<List<WSProcedureStep>>bindToLife(), new WSChildHttpResultObsever<List<WSProcedureStep>>(baseView, WSProcedureStep.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSProcedureStep> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 请求报工历史记录
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     * @param <T>
     */
    public <T> void requesStepHistoryList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<T>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        Type type = null;
        Type[] types = callBack.getClass().getGenericInterfaces();
        Type[] actualTypeArguments = ((ParameterizedType) types[0]).getActualTypeArguments();
        Type actualTypeArgument = actualTypeArguments[0];
//        if(  ((Class)actualTypeArgument) instanceof  List){//判断是否为List类型
        if (actualTypeArgument instanceof ParameterizedType) {//如果是泛型参数得类型
            ParameterizedType pt = (ParameterizedType) actualTypeArgument;
            type = pt.getActualTypeArguments()[0];
        }
//        }

//        Class < T >  entityClass  =  (Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ];
        if (type == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_STEP_LIST_HISTORY, params, baseView.<List<T>>bindToLife(), new WSChildHttpResultObsever<List<T>>(baseView, type) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<T> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 确认报工
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     * @param wsErrorResultClickListner
     */
    public void requesStepConfirm(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_STEP_CONFIRM, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    public void requesExceptList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSTrayLoadInfo> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_EXCEPT_LIST, params, baseView.<WSTrayLoadInfo>bindToLife(), new WSChildHttpResultObsever<WSTrayLoadInfo>(baseView, WSTrayLoadInfo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSTrayLoadInfo info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    public void requesExceptHistoryList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSExceptionOutRecordVo>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_EXCEPT_HISTORY_LIST, params, baseView.<List<WSExceptionOutRecordVo>>bindToLife(), new WSChildHttpResultObsever<List<WSExceptionOutRecordVo>>(baseView, WSExceptionOutRecordVo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSExceptionOutRecordVo> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 打印bom清单
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requestPrintBom(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.PRINT_BOM_INFO, params, baseView.<WSTrayLoadInfo>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    public void commitFollowData(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.COMMIT_BOM_FOLLOWED, params, baseView.<WSTrayLoadInfo>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 异常投递
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requesExceptDelivery(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.EXCEPT_DELIVERY, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 异常打印
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     * @param wsErrorResultClickListner
     */
    public void requesExceptPrint(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_EXCEPTION_PRINT, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 清空托盘
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requesClearTray(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.CLEAR_TRAY, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 检查文件的md5
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void checkFileMd5(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSValidateProcessInstructionVo> callBack, boolean isShowLoad) {

        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.CHECK_GUIDE_VERSION, params, baseView.<WSValidateProcessInstructionVo>bindToLife(), new WSChildHttpResultObsever<WSValidateProcessInstructionVo>(baseView, WSValidateProcessInstructionVo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(WSValidateProcessInstructionVo info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    /**
     * 下载pdf
     *
     * @param pdfUrl
     * @param saveUrl
     * @param baseView
     * @param callBack
     * @param isShowLoad
     * @param downloadProgressListener
     */
    public void downLoadPdf(String pdfUrl, String saveUrl, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, DownloadProgressListener downloadProgressListener) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().downFile(pdfUrl, saveUrl, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        }, downloadProgressListener);
    }

    /**
     * 获取转移工位列表
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void requesStationTrasferList(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<List<WSWorkStationInfo>> callBack, boolean isShowLoad) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.GET_STATION_TRANSFER_LIST, params, baseView.<WSWorkStationInfo>bindToLife(), new WSChildHttpResultObsever<List<WSWorkStationInfo>>(baseView, WSWorkStationInfo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(List<WSWorkStationInfo> info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 确认工位转移
     *
     * @param params
     * @param baseView
     * @param callBack
     * @param isShowLoad
     */
    public void confirmStationTrasfer(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, WSErrorResultClickListner wsErrorResultClickListner) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().post(WSURLBuilder.STATION_TRANSFER_CONFIRM, params, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null, wsErrorResultClickListner) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }
}

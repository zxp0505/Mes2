package workstation.zjyk.workstation.ui.present;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSPerson;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSWip;
import workstation.zjyk.workstation.modle.bean.WSWorkStationMaterielVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStationReceiveVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStationVo;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayBindTypeEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayLoadTypeEnum;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.views.WSReciverBaseView;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2018/1/4.
 */

public abstract class WSReciverBasePresent<T extends WSReciverBaseView> extends WSRxPresent<T> {
    public String getReciverType(WSTrayLoadInfo wrap) {
        String result = "可接收";
        WSTrayBindTypeEnum bindTypeEnum = wrap.getBindType();
        if (bindTypeEnum != null) {
            return bindTypeEnum.getKey();
//            switch (bindTypeEnum) {
//                case LINE_DISTRIBUTE_MATERIEL:
//                    result = "线边库分发到工位的物料";
//                    break;
//                case WORK_STATION_WIP:
//                    result = "工位正常输出在制品";
//                    break;
//                case WORK_STATION_REWORK_WIP:
//                    result = "工位转返工在制品";
//                    break;
//                case WORK_STATION_REWORK_RETURN_WIP:
//                    result = "工位返工任务返回在制品";
//                    break;
//                case REPAIR_WORK_STATION_WIP:
//                    result = "维修转工位在制品和物料";
//                    break;
//                case REPAIR_WORK_STATION_RETURN_WIP:
//                    result = "维修返回工位在制品和物料";
//                    break;
//                case QUALITY_RETURN_MATERIEL:
//                    result = "质量返回物料";
//                    break;
//
//            }

        }
        return result;
    }

    public String getType(WSTrayLoadInfo wrap) {
        String result = "物料";
        WSTrayLoadTypeEnum typeEnum = wrap.getType();

        if (typeEnum != null) {
            switch (typeEnum) {
                case MATERIAL:
                    result = "物料";
                    break;
                case WIP:
                    result = "在制品";
                    break;
                case WIPANDMATERIAL:
                    result = "物料和在制品";
                    break;
            }
        }


        return result;
    }

    abstract WSTaskRequest getRequest();

    /**
     * 检查接收物料是否绑定
     *
     * @param params
     */
    public void reciverCheckTray(Map<String, String> params) {
        if (getRequest() != null) {
            getRequest().reciverCheckTray(params, getView(), new WSRxDataCallBack<String>() {
                @Override
                public void onSucess(String s) {
                    if (getView() != null) {
                        checkTrayResult(true, s);
                    }
                }

                @Override
                public void onFail() {
                    if (getView() != null) {
                        getView().checkTrayResult(false, null);
                    }
                }
            }, true);
        }
    }

    private void checkTrayResult(boolean result, String str) {
        if (result) {
            if (getView() != null) {
                getView().checkTrayResult(true, str);
                return;
                //直接接收 后台自动绑盘
//                Context contextByView = getView().getContextByView();
//                if (contextByView != null) {
//                    WSDialogUtils.showReciverBindTrayDialog(contextByView, "请扫描或手动输入接收托盘码", str, new WSDialogCallBackTwo() {
//                        @Override
//                        public void OnPositiveClick(Object obj) {
//                            String str = (String) obj;
//                            if (!TextUtils.isEmpty(str) && str.contains(":")) {
//                                String[] split = str.split(":");
//                                String code = split[0];
//                                int status = Integer.parseInt(split[1]);
//                                if (status == 0) {
//                                    //需要校验托盘吗
//                                    requesBindTray(code);
//                                } else if (status == 1) {
//                                    if (getView() != null) {
//                                        getView().checkTrayResult(true, code);
//                                    }
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void OnNegativeClick() {
//
//                        }
//                    });
//                }
            }

        }
    }

    private void requesBindTray(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        getRequest().requesBindTray(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().checkTrayResult(true, s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().checkTrayResult(false, "");
                }
            }
        }, true);
    }

    public String setReciverData(WSTrayLoadInfo wrap, String bindTrayCode) {
        if (wrap == null) {
            return "";
        }
        WSWorkStationReceiveVO wsWorkStationReceiveVO = new WSWorkStationReceiveVO();
        List<WSWorkStationMaterielVO> sendMaterils = new ArrayList<>();
        List<WSMaterial> materiels = wrap.getMaterialList();
        List<WSWip> wipList = wrap.getWipList();
        WSTrayLoadTypeEnum type = wrap.getType();
        if (type == WSTrayLoadTypeEnum.MATERIAL) {
            if (materiels != null && materiels.size() > 0) {
                for (int i = 0; i < materiels.size(); i++) {
                    WSMaterial wsMaterial = materiels.get(i);
                    WSWorkStationMaterielVO wsWorkStationMaterielVO = new WSWorkStationMaterielVO();
                    wsWorkStationMaterielVO.setMaterielId(wsMaterial.getId());
                    wsWorkStationMaterielVO.setCount(wsMaterial.getCount());
                    sendMaterils.add(wsWorkStationMaterielVO);
                }
            }
            wsWorkStationReceiveVO.setMaterielList(sendMaterils);//物料集合
        } else {
            if (wipList != null && wipList.size() > 0) {
                wsWorkStationReceiveVO.setCount(wipList.get(0).getCount());//在制品 、返工/维修辅助/维修返还数量
            }


        }

//        if (wipList != null && wipList.size() > 0) {
//            for (int i = 0; i < wipList.size(); i++) {
//                WSWip wsWip = wipList.get(i);
//                WSWorkStationMaterielVO wsWorkStationMaterielVO = new WSWorkStationMaterielVO();
//                wsWorkStationMaterielVO.setMaterielId(wsWip.getId());
//                wsWorkStationMaterielVO.setCount(wsWip.getCount());
//                sendMaterils.add(wsWorkStationMaterielVO);
//            }
//        }


        WSUserManager userManager = WSUserManager.getInstance();
        WSPerson currentPerson = userManager.getCurrentPerson();
        if (currentPerson != null) {
            wsWorkStationReceiveVO.setPersonId(currentPerson.getPersonId());//收料人员ID
    }
        wsWorkStationReceiveVO.setReceiveType(wrap.getBindType());//托盘绑定类型
        if (wrap.getTray() != null) {
            wsWorkStationReceiveVO.setTrayCode(wrap.getTray().getOneDemensionCode());//接收托盘的码
        }
        WSWorkStationVo workStationVo = userManager.getWorkStationVo();
        if (workStationVo != null) {
            wsWorkStationReceiveVO.setWorkStationId(workStationVo.getWorkStationId());//工位id
        }
        wsWorkStationReceiveVO.setOutRecordId(wrap.getId());
        wsWorkStationReceiveVO.setWorkStationTaskId(wrap.getWorkStationTaskId());//收料任务id
        wsWorkStationReceiveVO.setWorkStationTrayCode(bindTrayCode);//绑定码
        return JsonUtil.toJson(wsWorkStationReceiveVO);
    }


}

/**
 * Created by Administrator on 2017/5/10.
 */
!function (e) {
    function t(o) {
        if (a[o])return a[o].exports;
        var c = a[o] = {exports: {}, id: o, loaded: !1};
        return e[o].call(c.exports, c, c.exports, t), c.loaded = !0, c.exports
    }

    var a = {};
    return t.m = e, t.c = a, t.p = "", t(0)
}([function (e, t, a) {
    var o;
    o = function (e) {
        var t = angular.module("App");
        t.controller("mainCtrl", ["$scope", "$http", "$uibModal", function (e, t, a) {
            function o(e) {
                return !e.isParent && e.checked
            }

            function c(e) {
                for (var t = [], a = [], o = [], c = 0; c < e.length; c++)a.indexOf(e[c].lv1CategoryId) == -1 && (a.push(e[c].lv1CategoryId), t.push(r(e[c]))), o.indexOf(e[c].lv2CategoryId) == -1 && (o.push(e[c].lv2CategoryId), t.push(s(e[c]))), t.push(i(e[c]));
                return t
            }

            function r(e) {
                return {id: e.lv1CategoryId, name: e.lv1CategoryName, pId: 0, open: !0}
            }

            function s(e) {
                return {id: e.lv2CategoryId, name: e.lv2CategoryName, pId: e.lv1CategoryId, open: !1}
            }

            function i(e) {
                return {
                    id: e.id,
                    name: e.laborName,
                    pId: e.lv2CategoryId,
                    lv1CategoryId: e.lv1CategoryId,
                    lv1CategoryName: e.lv1CategoryName,
                    lv2CategoryId: e.lv2CategoryId,
                    lv2CategoryName: e.lv2CategoryName,
                    laborCode: e.laborCode,
                    laborName: e.laborName
                }
            }

            e.stuffCategoryStruct = [], e.laborCostCategoryStruct = [], e.careShopList = [], e.selectedShopList = [], e.stuffList = [], e.selectedStuffItemList = [], e.packageList = [], e.selectedPackageItemList = [], e.laborCostList = [], e.selectedLaborCostItemList = [], e.isShopSelected = !1, e.careShopType = 0, e.isChecked = !1, t({
                method: "GET",
                url: window.globalConfig.ctx + "/commodity/stuff/lv2StuffCategory/getEnableStuffCategoryStruct"
            }).success(function (t, a, o, c) {
                e.stuffCategoryStruct = t
            }), t({
                method: "GET",
                url: window.globalConfig.ctx + "/commodity/laborCost/lv2LaborCostCategory/getEnableLaborCostCategoryStruct"
            }).success(function (t, a, o, c) {
                e.laborCostCategoryStruct = t
            }), e.lv2StuffCategoryList = [{
                id: "",
                categoryCode: "",
                categoryName: "请选择"
            }], e.searchShopParam = {
                visitSource: 1,
                cityId: "-1",
                districtId: "-1",
                signStatus: "-1",
                keywords: "",
                careShopType: 0,
                visitType: -1,
                pageSize: 20,
                pageNo: 1
            }, e.signStatus = [{id: -1, name: "全部"}, {id: 0, name: "已下线"}, {id: 1, name: "合作中"}];
            var l;
            e.createShopModelDialog = function () {
                l = a.open({templateUrl: "selectShopEdit.html", scope: e, backdrop: "static", keyboard: "false"})
            }, e.selectCareShop = function () {
                e.getCareShopTypeDefine(), e.createShopModelDialog()
            }, e.getCareShopTypeDefine = function () {
                t({
                    method: "GET",
                    url: window.globalConfig.ctx + "/careshop/typeconfig/define/get"
                }).success(function (t) {
                    e.careShopTypeList = t.data
                })
            }, e.openCitys = [{id: "-1", name: "请选择城市"}], e.districts = [{
                id: "-1",
                name: "请选择区域"
            }], t.get(window.globalConfig.ctx + "/common/city/all").success(function (t) {
                t instanceof Array && (e.openCitys = e.openCitys.concat(t), e.cityList = t, e.cityList.unshift({
                    id: 0,
                    name: "全部"
                }))
            }).error(function () {
                alert("获取城市列表出错")
            }), e.getDistrictsByOpenCitysId = function (a) {
                e.districts = [{
                    id: "-1",
                    name: "请选择区域"
                }], t.get(window.globalConfig.ctx + "/common/district/byCityId?cityId=" + a).success(function (t) {
                    t instanceof Array && (e.districts = e.districts.concat(t))
                }).error(function () {
                    alert("获取区域列表出错")
                }), e.searchShopParam.districtId = "-1"
            }, e.refreshDistricts = function () {
                "-1" == e.cityId ? e.districts = [{
                    id: "-1",
                    name: "请选择区域"
                }] : e.getDistrictsByOpenCitysId(e.searchShopParam.cityId), e.searchShopParam.districtId = "-1"
            }, e.chooseC = function (t) {
                e.searchShopParam.cityId = t.id, e.refreshDistricts()
            }, e.chooseD = function (t) {
                e.searchShopParam.districtId = t
            }, e.searchBusiness = function () {
                t({
                    method: "POST",
                    url: window.globalConfig.ctx + "/careshop/careShopList/get/data",
                    data: e.searchShopParam
                }).success(function (t, a, o, c) {
                    t.success && (e.careShopList = t.data, e.searchShopParam.totalSize = t.recordCount)
                })
            }, e.getCareShopType = function (e) {
                switch (e) {
                    case 0:
                        return "未分类";
                    case 1:
                        return "A类店";
                    case 2:
                        return "B类店";
                    case 3:
                        return "C类店";
                    case 4:
                        return "认证店";
                    case 5:
                        return "特许店";
                    case 6:
                        return "典典专供";
                    case 7:
                        return "签约供应商";
                    case 8:
                        return "单店自建供应商";
                    case 9:
                        return "精英店";
                    case 21:
                        return "BBC轮胎服务商";
                    case 22:
                        return "BBC贴膜服务商";
                    default:
                        return ""
                }
            }, e.selectingShop = function (t) {
                var a = !1;
                angular.forEach(e.selectedShopList, function (e, o) {
                    e.careShopId == t.careShopId && (a = !0)
                }), a || (e.selectedShopList.push(t), e.selectedShopList.length > 0 && (e.isShopSelected = !0, e.careShopType = e.searchShopParam.careShopType))
            }, e.selectAllPageShop = function () {
                e.selectedShopList = e.careShopList, e.selectedShopList.length > 0 && (e.isShopSelected = !0, e.careShopType = e.searchShopParam.careShopType)
            }, e.cancelAllPageShop = function () {
                e.selectedShopList = [], 0 == e.selectedShopList.length && (e.isShopSelected = !1, e.careShopType = 0)
            }, e.cancelSelectShop = function (t) {
                e.selectedShopList.splice(t, 1), 0 == e.selectedShopList.length && (e.isShopSelected = !1, e.careShopType = 0)
            }, e.closeEditSelectShop = function () {
                e.searchShopParam = {
                    cityId: "-1",
                    districtId: "-1",
                    signStatus: "-1",
                    keywords: "",
                    careShopType: e.careShopType,
                    visitType: -1,
                    pageSize: 20,
                    pageNo: 1
                }, e.careShopList = [], e.selectedShopList.length > 0, l.close()
            }, e.searchLaborCostParam = {careShopType: 0, pageNo: 1, pageSize: 2e3};
            var n, d, f = {
                check: {enable: !0, chkStyle: "checkbox", chkboxType: {Y: "ps", N: "ps"}},
                data: {simpleData: {enable: !0, idKey: "id", pIdKey: "pId", rootPId: 0}}
            };
            e.createLaborCostModelDialog = function () {
                n = a.open({templateUrl: "selectLaborCostEdit.html", scope: e, backdrop: "static", keyboard: "false"})
            }, e.initTree = function () {
                d = $.fn.zTree.init($("#laborCostTree"), f, c(e.laborCostList))
            }, e.searchLaborCost = function () {
                e.searchLaborCostParam.careShopType = e.careShopType, t({
                    method: "POST",
                    url: window.globalConfig.ctx + "/commodity/laborCost/laborCostItem/getLaborCostEnabledPageByLv1CodeAndLv2CodeAndFuzzyName",
                    data: e.searchLaborCostParam
                }).success(function (t, a, o, c) {
                    t.success && (e.laborCostList = t.data, e.initTree())
                })
            }, e.selectLaborCostItem = function () {
                e.isShopSelected && (e.lv1LaborCostCategoryList = [{
                    id: "",
                    categoryCode: "",
                    categoryName: "请选择"
                }], e.searchLaborCost(), e.createLaborCostModelDialog())
            };
            var u;
            e.createShowAllLaborCostModal = function () {
                u = a.open({templateUrl: "showAllLaborCostModal.html", scope: e, backdrop: "static", keyboard: "false"})
            }, e.closeShowAllLaborCostModal = function () {
                u.close()
            }, e.closeEditSelectLaborCost = function () {
                e.laborCostList = [], n.close()
            }, e.saveSetting = function () {
                e.selectedLaborCostItemList = d.getNodesByFilter(o), e.closeEditSelectLaborCost()
            }, e.searchStuffParam = {pageNo: 1, pageSize: 20};
            var g;
            e.createStuffModelDialog = function () {
                g = a.open({templateUrl: "selectStuffEdit.html", scope: e, backdrop: "static", keyboard: "false"})
            }, e.selectStuffItem = function () {
                e.isShopSelected && (e.lv1StuffCategoryList = [{
                    id: "",
                    categoryCode: "",
                    categoryName: "请选择"
                }], e.lv1StuffCategoryList = e.lv1StuffCategoryList.concat(e.stuffCategoryStruct), e.createStuffModelDialog())
            }, e.switchLv1StuffCategory = function (t) {
                e.lv2StuffCategoryList = [{
                    id: "",
                    categoryCode: "",
                    categoryName: "请选择"
                }], e.searchStuffParam.lv1CategoryCode = t.categoryCode, e.searchStuffParam.lv2CategoryCode = "", "" != t.id && t.subCategories && (e.lv2StuffCategoryList = e.lv2StuffCategoryList.concat(t.subCategories))
            }, e.switchLv2StuffCategory = function (t) {
                t && null != t ? e.searchStuffParam.lv2CategoryCode = t.categoryCode : e.searchStuffParam.lv2CategoryCode = ""
            }, e.searchStuff = function () {
                e.stuffList = [], t({
                    method: "POST",
                    url: window.globalConfig.ctx + "/commodity/stuff/stuffItem/getStuffEnabledPageByLv1CodeAndLv2CodeAndFuzzyName",
                    data: e.searchStuffParam
                }).success(function (t, a, o, c) {
                    t.success && (e.searchStuffParam.totalSize = t.recordCount, e.stuffList = t.data)
                })
            }, e.selectAllPageStuff = function () {
                e.selectedStuffItemList = e.stuffList
            }, e.cancelAllPageStuff = function () {
                e.selectedStuffItemList = []
            }, e.selectingStuff = function (t) {
                var a = !1;
                angular.forEach(e.selectedStuffItemList, function (e, o) {
                    e.stuffId == t.stuffId && (a = !0)
                }), a || e.selectedStuffItemList.push(t)
            }, e.cancelSelectStuff = function (t) {
                e.selectedStuffItemList.splice(t, 1)
            }, e.closeEditSelectStuff = function () {
                e.lv2StuffCategoryList = [{
                    id: "",
                    categoryCode: "",
                    categoryName: "请选择"
                }], e.searchStuffParam = {pageNo: 1, pageSize: 20}, e.stuffList = [], g.close()
            }, e.searchPackageParam = {pageNo: 1, pageSize: 20};
            var p;
            e.createPackageModelDialog = function () {
                p = a.open({templateUrl: "selectPackageEdit.html", scope: e, backdrop: "static", keyboard: "false"})
            }, e.selectPackageItem = function () {
                e.isShopSelected && (e.lv1StuffCategoryList = [{
                    id: "",
                    categoryCode: "",
                    categoryName: "请选择"
                }], e.lv1StuffCategoryList = e.lv1StuffCategoryList.concat(e.stuffCategoryStruct), e.createPackageModelDialog())
            }, e.switchLv1PackageCategory = function (t) {
                e.lv2StuffCategoryList = [{
                    id: "",
                    categoryCode: "",
                    categoryName: "请选择"
                }], e.searchPackageParam.lv1CategoryCode = t.categoryCode, e.searchPackageParam.lv2CategoryCode = "", "" != t.id && t.subCategories && (e.lv2StuffCategoryList = e.lv2StuffCategoryList.concat(t.subCategories))
            }, e.switchLv2PackageCategory = function (t) {
                t && null != t ? e.searchPackageParam.lv2CategoryCode = t.categoryCode : e.searchPackageParam.lv2CategoryCode = ""
            }, e.searchPackage = function () {
                e.packageList = [], t({
                    method: "POST",
                    url: window.globalConfig.ctx + "/commodity/packages/packageItem/getEnabledPackageList",
                    data: e.searchPackageParam
                }).success(function (t, a, o, c) {
                    t.success && (e.searchPackageParam.totalSize = t.recordCount, e.packageList = t.data)
                })
            }, e.selectingPackage = function (t) {
                var a = !1;
                angular.forEach(e.selectedPackageItemList, function (e, o) {
                    e.id == t.id && (a = !0)
                }), a || e.selectedPackageItemList.push(t)
            }, e.selectAllPagePackage = function () {
                e.selectedPackageItemList = e.packageList
            }, e.cancelAllPagePackage = function () {
                e.selectedPackageItemList = []
            }, e.cancelSelectPackage = function (t) {
                e.selectedPackageItemList.splice(t, 1)
            }, e.closeEditSelectPackage = function () {
                e.lv2StuffCategoryList = [{
                    id: "",
                    categoryCode: "",
                    categoryName: "请选择"
                }], e.searchPackageParam = {pageNo: 1, pageSize: 20}, e.packageList = [], p.close()
            };
            var h = !1;
            e.submit = function (a) {
                if (!h) {
                    if (!e.selectedShopList || !e.selectedShopList.length > 0)return void alert("请选择要关联的商户");
                    if (!e.selectedStuffItemList.length > 0 && !e.selectedPackageItemList.length > 0 && !e.selectedLaborCostItemList.length > 0)return void alert("请选择要关联的商品(基础套餐或配件或组合套餐)");
                    var o = [];
                    angular.forEach(e.selectedShopList, function (t, a) {
                        e.selectedLaborCostItemList.length > 0 && angular.forEach(e.selectedLaborCostItemList, function (e, a) {
                            var c = {};
                            c.shopId = t.careShopId, c.commodityCode = e.laborCode, c.commodityId = e.id, c.cityId = t.cityId, c.commodityType = 1, c.lv1CategoryId = e.lv1CategoryId, o.push(c)
                        }), e.selectedStuffItemList.length > 0 && angular.forEach(e.selectedStuffItemList, function (e, a) {
                            var c = {};
                            c.shopId = t.careShopId, c.commodityCode = e.stuffCode, c.commodityId = e.stuffId, c.cityId = t.cityId, c.commodityType = 2, c.lv1CategoryId = e.lv1CategoryId, o.push(c)
                        }), e.selectedPackageItemList.length > 0 && angular.forEach(e.selectedPackageItemList, function (e, a) {
                            var c = {};
                            c.shopId = t.careShopId, c.commodityCode = e.packageCode, c.cityId = t.cityId, c.commodityId = e.id, c.commodityType = 3, c.lv1CategoryId = e.lv1CategoryId, o.push(c)
                        })
                    }), h = !0, t({
                        method: "POST",
                        url: window.globalConfig.ctx + "/careshopcommodity/careShopCommodityList/createCommodityShopRel",
                        data: o
                    }).success(function (e, t, a, o) {
                        if (e.success) {
                            alert("新增商户商品成功");
                            var c = "/careshopcommodity/careShopCommodityList";
                            top.location.hash = window.globalConfig.ctx + c, window.location.replace(window.globalConfig.ctx + c)
                        } else alert(e.message);
                        h = !1
                    }).error(function (e, t, a, o) {
                        alert(e.message), h = !1
                    })
                }
            }
        }])
    }.call(t, a, t, e), !(void 0 !== o && (e.exports = o))
}]);
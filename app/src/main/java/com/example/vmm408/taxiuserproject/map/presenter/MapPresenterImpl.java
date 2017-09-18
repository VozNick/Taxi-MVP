package com.example.vmm408.taxiuserproject.map.presenter;

import android.location.Address;

import com.example.vmm408.taxiuserproject.constants.MyKeys;
import com.example.vmm408.taxiuserproject.map.model.MapModel;
import com.example.vmm408.taxiuserproject.map.view.MapView;
import com.example.vmm408.taxiuserproject.models.OrderModel;
import com.example.vmm408.taxiuserproject.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class MapPresenterImpl implements MapPresenter {
    private MapView mapView;
    private MapModel mapModel;
    private UserModel userModel;
    private OrderModel currentOrder;
    private UserModel driverAcceptedOrder;
    private List<String> searchAddressList = new ArrayList<>();

    public MapPresenterImpl(MapView mapView, MapModel mapModel) {
        this.mapView = mapView;
        this.mapModel = mapModel;
        if (!mapView.selfPermissionGranted()) {
            mapView.requestPermissions();
        } else {
            mapView.setLastLocation();
        }
        userModel = mapModel.getUserProfile();
        mapView.createMapAsync();
        mapView.setUserAvatarOnButton(userModel.getAvatarUser());
        checkIfCurrentOrderExist();
        mapView.initSearchAutoComplete();
    }

    private void checkIfCurrentOrderExist() {
        mapModel.findCurrentOrder(userModel.getIdUser(), orderModel -> {
            currentOrder = orderModel;
            if (currentOrder == null) {
                mapView.setOrderButtonClickable();
            } else {
                mapView.startCurrentOrderListenerService();
                checkIfOrderAccepted();
            }
        });
    }

    private void checkIfOrderAccepted() {
        if (currentOrder.getOrderAcceptedDriver() == null) {
            mapView.setOrderButtonClickable();
        } else {
            mapModel.findOrderAcceptedDriver(currentOrder.getOrderAcceptedDriver(), driverModel -> {
                driverAcceptedOrder = driverModel;
                mapView.setOrderButtonClickable();
            });
        }
    }

    @Override
    public void onMapClickListener() {
        mapView.clearMap();
        mapView.clearFocus();
    }

    @Override
    public void onMapLongClickListener(double latitude, double longitude) {
        mapView.clearMap();
        List<Address> addressList = mapModel.getAddressFromLocation(latitude, longitude, 1);
        if (addressList.size() > 0) {
            mapView.showOneMarker(addressList.get(0));
        }
    }

    @Override
    public void onMarkerClickListener() {
    }

    @Override
    public void onMyLocationClickListener() {

    }

    @Override
    public void onSearchAutoCompleteListItemClick(int position) {
        mapView.clearMap();
        mapView.showQueryInSearchWidget(searchAddressList.get(position), true);
    }

    @Override
    public void onToolbarClick() {
        mapView.showSearchViewContainer(true);
        mapView.setSearchViewIconified(false);
        mapView.setOnSearchQueryListener();
    }

    @Override
    public void onBtnBackClick() {
        mapView.showSearchViewContainer(false);
        mapView.showQueryInSearchWidget("", false);
    }

    @Override
    public void onBtnClearSearchClick() {
        mapView.showSearchViewContainer(false);
        mapView.showQueryInSearchWidget("", false);
    }

    @Override
    public void onBtnProfileClick() {
        mapView.showProfileMenuDialog();
    }

    @Override
    public void onSearchQueryTextSubmit(String query) {
        mapView.clearFocus();
        mapView.clearMap();
        List<Address> addressList = mapModel.getAddressFromLocationName(query, 10);
        if (addressList.size() > 0) {
            mapView.showMarkers(addressList);
        } else {
            mapView.showNoSearchResultsFound(query);
        }
    }

    @Override
    public void onSearchQueryTextChange(String newText) {
        mapView.showSearchClearButton(newText.length() > 0);
        if (newText.length() > 2) {
            searchAddressList.clear();
            List<Address> addressList = mapModel.getAddressFromLocationName(newText, 10);
            for (int i = 0; i < addressList.size(); i++) {
                searchAddressList.add(addressList.get(i).getAddressLine(0));
            }
            mapView.setSearchAutoCompleteList(searchAddressList);
        }
    }

    @Override
    public void onProfileMenuSelected(int key) {
        if (key == MyKeys.ITEM_PROFILE_KEY) {
            mapView.navigateToProfileActivity();
        } else if (key == MyKeys.ITEM_RATING_KEY) {
            mapView.navigateToRatingActivity();
        } else if (key == MyKeys.ITEM_ARCHIVE_ORDER_KEY) {
            mapView.navigateToArchiveOrderActivity();
        } else if (key == MyKeys.ITEM_SETTINGS_KEY) {
            mapView.navigateToSettingActivity();
        }
    }

    @Override
    public void onFabNewOrderClick() {
        if (currentOrder == null) {
            mapView.showNewOrderDialog();
        } else {
            mapView.showCurrentOrderDialog();
        }
    }

    @Override
    public void onBtnLocationClick() {
        mapView.setLastLocation();
        mapView.showMyLocation();
    }
}

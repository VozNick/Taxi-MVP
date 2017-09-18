package com.example.vmm408.taxiuserproject.map.view;

import android.location.Address;

import java.util.List;

public interface MapView {
    boolean selfPermissionGranted();

    void requestPermissions();

    void setLastLocation();

    void setUserAvatarOnButton(String avatar);

    void startCurrentOrderListenerService();

    void setOrderButtonClickable();

    void createMapAsync();

    void initSearchAutoComplete();

    void setSearchAutoCompleteList(List list);

    void showQueryInSearchWidget(String query, boolean submit);

    void showSearchViewContainer(boolean isShowing);

    void setSearchViewIconified(boolean isIconified);

    void showSearchClearButton(boolean isShowing);

    void setOnSearchQueryListener();

    void showProfileMenuDialog();

    void clearMap();

    void clearFocus();

    void navigateToProfileActivity();

    void navigateToRatingActivity();

    void navigateToArchiveOrderActivity();

    void navigateToSettingActivity();

    void showMyLocation();

    void showNewOrderDialog();

    void showCurrentOrderDialog();

    void showOneMarker(Address address);

    void showMarkers(List<Address> addressList);

    void showNoSearchResultsFound(String query);
}

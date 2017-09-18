package com.example.vmm408.taxiuserproject.map.presenter;

public interface MapPresenter {
    void onMapClickListener();

    void onMapLongClickListener(double latitude, double longitude);

    void onMarkerClickListener();

    void onMyLocationClickListener();

    void onSearchAutoCompleteListItemClick(int position);

    void onToolbarClick();

    void onBtnBackClick();

    void onBtnClearSearchClick();

    void onBtnProfileClick();

    void onSearchQueryTextSubmit(String query);

    void onSearchQueryTextChange(String newText);

    void onProfileMenuSelected(int key);

    void onFabNewOrderClick();

    void onBtnLocationClick();
}

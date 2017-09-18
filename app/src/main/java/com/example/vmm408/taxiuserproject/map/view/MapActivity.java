package com.example.vmm408.taxiuserproject.map.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.archiveorders.ArchiveOrderActivity;
import com.example.vmm408.taxiuserproject.ratings.RatingActivity;
import com.example.vmm408.taxiuserproject.settings.SettingActivity;
import com.example.vmm408.taxiuserproject.utils.ArrayAdapterUtil;
import com.example.vmm408.taxiuserproject.utils.GoogleMapUtils;
import com.example.vmm408.taxiuserproject.map.model.MapModelImpl;
import com.example.vmm408.taxiuserproject.map.presenter.MapPresenter;
import com.example.vmm408.taxiuserproject.map.presenter.MapPresenterImpl;
import com.example.vmm408.taxiuserproject.profile.view.ProfileActivity;
import com.example.vmm408.taxiuserproject.service.FirebaseService;
import com.example.vmm408.taxiuserproject.utils.DialogUtil;
import com.example.vmm408.taxiuserproject.utils.BitmapUtil;
import com.example.vmm408.taxiuserproject.utils.ToasterUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MapActivity extends AppCompatActivity implements MapView {
    @BindView(R.id.search_view_app_bar)
    SearchView searchViewAppBar;
    @BindView(R.id.im_btn_clear_search)
    ImageView imBtnClearSearch;
    @BindView(R.id.im_btn_profile)
    CircleImageView imBtnProfile;
    @BindView(R.id.search_view_container)
    LinearLayout searchViewContainer;
    @BindView(R.id.fab_new_order)
    FloatingActionButton fabNewOrder;
    @BindString(R.string.toast_no_search_results_found)
    String toastNoSearchResultsFound;
    private MapPresenter mapPresenter;
    private Location currentLocation;
    private GoogleMap map;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private GoogleMap.OnMapClickListener onMapClickListener = latLng -> mapPresenter.onMapClickListener();
    private GoogleMap.OnMapLongClickListener onMapLongClickListener =
            latLng -> mapPresenter.onMapLongClickListener(latLng.latitude, latLng.longitude);
    private GoogleMap.OnMarkerClickListener onMarkerClickListener = marker -> {
        mapPresenter.onMarkerClickListener();
        return false;
    };
    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener = () -> {
        mapPresenter.onMyLocationClickListener();
        return false;
    };
    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mapPresenter.onSearchQueryTextSubmit(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            mapPresenter.onSearchQueryTextChange(newText);
            return false;
        }
    };
    private AlertDialog.OnClickListener onClickProfileMenu = (dialogInterface, i) -> mapPresenter.onProfileMenuSelected(i);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        ButterKnife.bind(this);
        mapPresenter = new MapPresenterImpl(this, new MapModelImpl());
    }

    @Override
    public boolean selfPermissionGranted() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
    }

    // permission is checked before this method. This warning is okay.
    @SuppressWarnings("MissingPermission")
    @Override
    public void setLastLocation() {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnCompleteListener(this, task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                currentLocation = task.getResult();
            }
        });
    }

    @Override
    public void setUserAvatarOnButton(String avatar) {
        imBtnProfile.setImageBitmap(BitmapUtil.getStringToBitmap(avatar));
    }

    @Override
    public void startCurrentOrderListenerService() {
        startService(new Intent(this, FirebaseService.class));
    }

    @Override
    public void setOrderButtonClickable() {
        fabNewOrder.setClickable(true);
    }

    // permission is checked before this method. This warning is okay.
    @SuppressWarnings("MissingPermission")
    @Override
    public void createMapAsync() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            this.map = googleMap;
            this.map.setOnMapClickListener(onMapClickListener);
            this.map.setOnMapLongClickListener(onMapLongClickListener);
            this.map.setOnMarkerClickListener(onMarkerClickListener);
            this.map.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
            this.map.setMyLocationEnabled(true);
        });
    }

    @Override
    public void initSearchAutoComplete() {
        searchAutoComplete = (SearchView.SearchAutoComplete) searchViewAppBar.findViewById(R.id.search_src_text);
        searchAutoComplete.setOnItemClickListener((adapterView, view, i, l) -> mapPresenter.onSearchAutoCompleteListItemClick(i));
    }

    @Override
    public void setSearchAutoCompleteList(List list) {
        searchAutoComplete.setAdapter(ArrayAdapterUtil.initAdapter(this, list));
        searchAutoComplete.showDropDown();
    }

    @Override
    public void showQueryInSearchWidget(String query, boolean submit) {
        searchViewAppBar.setQuery(query, submit);
    }

    @OnClick(R.id.toolbar)
    void onToolbarClick() {
        mapPresenter.onToolbarClick();
    }

    @OnClick(R.id.image_view_btn_back)
    void onBtnBackClick() {
        mapPresenter.onBtnBackClick();
    }

    @OnClick(R.id.im_btn_clear_search)
    void onBtnClearSearchClick() {
        mapPresenter.onBtnClearSearchClick();
    }

    @OnClick(R.id.im_btn_profile)
    void onBtnProfileClick() {
        mapPresenter.onBtnProfileClick();
    }

    @Override
    public void showSearchViewContainer(boolean isShowing) {
        if (isShowing) {
            searchViewContainer.setVisibility(View.VISIBLE);
        } else {
            searchViewContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSearchViewIconified(boolean isIconified) {
        if (isIconified) {
            searchViewAppBar.setIconified(true);
        } else {
            searchViewAppBar.setIconified(false);
        }
    }

    @Override
    public void showSearchClearButton(boolean isShowing) {
        if (isShowing) {
            imBtnClearSearch.setVisibility(View.VISIBLE);
        } else {
            imBtnClearSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void setOnSearchQueryListener() {
        searchViewAppBar.setOnQueryTextListener(onQueryTextListener);
    }

    @Override
    public void showProfileMenuDialog() {
        DialogUtil.menuProfile(this, onClickProfileMenu).show();
    }

    @Override
    public void clearMap() {
        map.clear();
    }

    @Override
    public void clearFocus() {
        searchViewAppBar.clearFocus();
    }

    @Override
    public void navigateToProfileActivity() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }

    @Override
    public void navigateToRatingActivity() {
        startActivity(new Intent(this, RatingActivity.class));
        finish();
    }

    @Override
    public void navigateToArchiveOrderActivity() {
        startActivity(new Intent(this, ArchiveOrderActivity.class));
        finish();
    }

    @Override
    public void navigateToSettingActivity() {
        startActivity(new Intent(this, SettingActivity.class));
        finish();
    }

    @Override
    public void showMyLocation() {
        map.animateCamera(GoogleMapUtils.getMyLocation(currentLocation));
    }

    @OnClick(R.id.fab_new_order)
    void fabNewOrder() {
        mapPresenter.onFabNewOrderClick();
    }

    @Override
    public void showNewOrderDialog() {

    }

    @Override
    public void showCurrentOrderDialog() {

    }

    @Override
    public void showOneMarker(Address address) {
        map.animateCamera(GoogleMapUtils.getMarkerPosition(address, map));
    }

    @Override
    public void showMarkers(List<Address> addressList) {
        map.animateCamera(GoogleMapUtils.getMarkerBoundsPosition(addressList, map));
    }

    @Override
    public void showNoSearchResultsFound(String query) {
        ToasterUtil.makeToast(this, toastNoSearchResultsFound + " '" + query + "'");
    }

    @OnClick(R.id.fab_my_location)
    void onBtnLocationClick() {
        mapPresenter.onBtnLocationClick();
    }
}

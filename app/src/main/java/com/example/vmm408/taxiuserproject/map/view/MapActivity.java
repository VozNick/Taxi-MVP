package com.example.vmm408.taxiuserproject.map.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.map.model.MapModelImpl;
import com.example.vmm408.taxiuserproject.map.presenter.MapPresenter;
import com.example.vmm408.taxiuserproject.map.presenter.MapPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private MapPresenter mapPresenter;

//
//    private FusedLocationProviderClient client;
//    private Location currentLocation;
//    private GoogleMap map;
//    private Geocoder geocoder;
//    private SearchView.SearchAutoComplete searchAutoComplete;
//    private List<String> searchAddressList = new ArrayList<>();
//
//
//    private GoogleMap.OnMapClickListener mapClickListener = new GoogleMap.OnMapClickListener() {
//        @Override
//        public void onMapClick(LatLng latLng) {
//            map.clear();
//            searchViewAppBar.clearFocus();
//        }
//    };
//    private GoogleMap.OnMarkerClickListener markerClickListener = marker -> false;
//    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
//        @Override
//        public boolean onQueryTextSubmit(String query) {
//            searchViewAppBar.clearFocus();
//            initMarker(query);
//            return false;
//        }
//
//        @Override
//        public boolean onQueryTextChange(String newText) {
//            imBtnClearSearch.setVisibility(newText.length() > 0 ? View.VISIBLE : View.GONE);
//            if (newText.length() > 2) {
//                initAutoCompleteList(newText);
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        ButterKnife.bind(this);
        if (mapPresenter == null) {
            mapPresenter = new MapPresenterImpl(this, new MapModelImpl());
        }
    }


//    private ValueEventListener getUserFromBase = new DatabaseValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            EventBus.getDefault().post(new EventSignInApi(dataSnapshot.getValue(UserModel.class)));
//        }
//    };
//
//    @Override
//    public void findUserInDataBase(String userId) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference(USERS_REF_KEY).child(userId);
//        reference.addListenerForSingleValueEvent(getUserFromBase);
//    }
}

package com.x.metiz.ui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.x.metiz.HotelModel;
import com.x.metiz.R;
import com.x.metiz.databinding.FragmentLocationBinding;
import com.x.metiz.network.ApiService;
import com.x.metiz.network.RetroClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class LocationFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {


    ApiService apiService;
    FragmentLocationBinding binding;
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_LOCATION = 1;
    private GoogleMap googleMap;
    private static final String TAG = "LocationFragment";
    boolean zoomed=false;
    Bitmap markerIconDeactive, markerIconActive;
    ArrayList<HotelModel.HotelData.Hotel.PlaceData> arrPlaceData=new ArrayList<>();
    ArrayList<HotelModel.HotelData.Hotel.PlaceData.LocationData> arrLocationData=new ArrayList<>();
    Marker lastClicked = null;
    String startDate ="", endDate="";
    public LocationFragment() {
        // Required empty public constructor
    }

    public static LocationFragment newInstance(Bundle bundle) {
        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Calendar currentCal = Calendar.getInstance();
        startDate=dateFormat.format(currentCal.getTime());
        currentCal.add(Calendar.DATE, 3);
        endDate=dateFormat.format(currentCal.getTime());

        markerIconDeactive = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.marker_deactive);
        markerIconActive=BitmapFactory.decodeResource(getContext().getResources(),R.drawable.marker_active);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location, container, false);
        init(savedInstanceState);
        return binding.getRoot();
    }

    private void init(Bundle savedInstanceState) {
        initGoogleClient();
        initMapView(savedInstanceState);
        apiService= RetroClient.getApiService();
        getHotelData();
    }

    private void getHotelData() {
        showLoading("Please wait..", getActivity());
        apiService.getHotelData("2", startDate,endDate, "USD", "50", "22.965515078282593,72.51908888477644,23.077732321717406,72.64032471522356")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<HotelModel>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        Log.e(TAG, "onSubscribe: " );
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Response<HotelModel> hotelModelResponse) {
                        hideLoading(getActivity());
                        Log.e(TAG, "onNext: "+hotelModelResponse.body() );
                        Log.e(TAG, "onNext: "+hotelModelResponse.code() );
                        if (hotelModelResponse.body()!=null){
                            HotelModel.HotelData.Hotel.PlaceData placeData;
                            for (int i = 0; i <hotelModelResponse.body().getData().getHotels().size() ; i++) {
                                placeData=hotelModelResponse.body().getData().getHotels().get(i).getPlace();
                                arrPlaceData.add(placeData);
                            }

                            setLocationData(hotelModelResponse.body());
                        }


                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        hideLoading(getActivity());
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onComplete() {
                        hideLoading(getActivity());
                        Log.e(TAG, "onComplete: " );
                    }
                });

    }

    private void setLocationData(HotelModel hotelRes) {
        HotelModel.HotelData.Hotel.PlaceData.LocationData locationData;
        HotelModel.HotelData.Hotel.PlaceData placeData=null;
        for (int i = 0; i <hotelRes.getData().getHotels().size() ; i++) {
            locationData=hotelRes.getData().getHotels().get(i).getPlace().getLocation();

            setHotelMarker(locationData.getLat(), locationData.getLng(), hotelRes.getData().getHotels().get(i).getPlace().getId());
            arrLocationData.add(locationData);

        }
        Log.e(TAG, "setLocationData: "+arrLocationData.toString() );

      //  googleMap.clear();

        if (!zoomed) {
            zoomed = true;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(new LatLng(arrLocationData.get(0).getLat(), arrLocationData.get(0).getLng()));
            //Animate to the bounds
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), getResources().getDimensionPixelSize(R.dimen.map_dp));
            googleMap.moveCamera(cameraUpdate);
            googleMap.animateCamera( CameraUpdateFactory.zoomTo( 15.00f ) );
        }


    }



    public void setHotelMarker(double lat, double lng, String id) {
        Log.e(TAG, "setMarkerDCRStation: "+lat+","+lng );
        googleMap.setOnMarkerClickListener(this);
        if (lat!=0 && lng!=0) {
            this.googleMap.addMarker(new MarkerOptions().
                    icon(BitmapDescriptorFactory.fromBitmap(markerIconDeactive))
                    .flat(true)
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_64))
                    .snippet(id)
                    .position(new LatLng(lat, lng)));
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        binding.cardHotelInfo.setVisibility(View.VISIBLE);
        String id=marker.getSnippet();
        Log.e(TAG, "onMarkerClick: "+id );

        if (lastClicked!=null)
            lastClicked.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_deactive));
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_active));
        lastClicked = marker;

        for (int i = 0; i <arrPlaceData.size() ; i++) {

            if (arrPlaceData.get(i).getId().equals(id)){

                Glide.with(getContext()).load(arrPlaceData.get(i).getThumbnail_url()).into(binding.imgHotel);
                binding.tvHotelName.setText(arrPlaceData.get(i).getName_suffix());
                binding.ratting.setRating((float) arrPlaceData.get(i).getStar_rating());
                binding.tvHotelAddress.setText(arrPlaceData.get(i).getName());
            }

        }
        return false;
    }



    private void initGoogleClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .build();
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        mGoogleApiClient.connect();

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Failed" + connectionResult.getErrorMessage());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        this.googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(),R.raw.googstyle));

        googleMap.setMyLocationEnabled(false);
    }

    private void initMapView(Bundle savedInstanceState) {
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    return;
                }
                onMapReady(googleMap);
                googleMap.setMyLocationEnabled(true);

            } else {
                // Permission was denied or request was cancelled
            }
        }


    }

    public static void showLoading(String msg, Activity activity) {
        try {
            View loader = activity.findViewById(R.id.viewLoader);
            if (loader != null) {
                loader.setVisibility(View.VISIBLE);
                try {
                    TextView lblLoaderMsg = (TextView) activity.findViewById(R.id.lblLoaderMsg);
                    lblLoaderMsg.setTextSize(18f);
                    lblLoaderMsg.setText(msg);
                }catch (Exception ex){

                }

            }
        }catch (Exception ex){

        }
    }

    public static void hideLoading(Activity activity) {

        try {
            View loader = activity.findViewById(R.id.viewLoader);
            if (loader != null) {
                loader.setVisibility(View.GONE);
                try {
                    TextView lblLoaderMsg = (TextView) activity.findViewById(R.id.lblLoaderMsg);
                    lblLoaderMsg.setText("");
                }catch (Exception ex){

                }

            }
        }catch (Exception ex){

        }
    }


}
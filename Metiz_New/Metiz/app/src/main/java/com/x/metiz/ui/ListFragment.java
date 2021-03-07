package com.x.metiz.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.x.metiz.HotelModel;
import com.x.metiz.R;
import com.x.metiz.databinding.FragmentListBinding;
import com.x.metiz.network.ApiService;
import com.x.metiz.network.RetroClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class ListFragment extends Fragment {

    FragmentListBinding binding;
    ApiService apiService;
    private static final String TAG = "ListFragment";
    ArrayList<HotelModel.HotelData.Hotel.PlaceData> arrPlaceData=new ArrayList<>();
    String startDate="", endDate="";
 public ListFragment() {
        // Required empty public constructor
    }
    public static ListFragment newInstance(Bundle bundle) {
        ListFragment fragment = new ListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService= RetroClient.getApiService();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Calendar currentCal = Calendar.getInstance();
        startDate=dateFormat.format(currentCal.getTime());
        currentCal.add(Calendar.DATE, 3);
        endDate=dateFormat.format(currentCal.getTime());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
     getHotelData();


    }

    private void getHotelData() {
        showLoading("Please wait..", getActivity());
        apiService.getHotelData("2",startDate,endDate, "USD", "50", "22.965515078282593,72.51908888477644,23.077732321717406,72.64032471522356")
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

                            setHotelData(arrPlaceData);
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

    private void setHotelData(ArrayList<HotelModel.HotelData.Hotel.PlaceData> arrPlaceData) {
        HotelListAdapter hotelListAdapter=new HotelListAdapter(getContext(), arrPlaceData);
        binding.rvHotels.setAdapter(hotelListAdapter);
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
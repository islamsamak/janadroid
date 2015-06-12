/*
 * Copyright (C) 2013 OneTeam (IslamSamak : islamsamak01@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jana.android.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jana.android.model.MapModel;
import com.jana.android.ui.R;
import com.jana.android.ui.impl.view.MapInfoWindowAdapter;
import com.jana.android.utils.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.HashMap;
import java.util.List;

/**
 * @author islam
 */
public abstract class AbstractMapFragment<T extends MapModel> extends
        AbstractFragment implements OnInfoWindowClickListener {

    public static final String EXTRA_CURRENT_LOCATION = "extra_current_location";

    private GoogleMap mGoogleMap;

    private List<? extends MapModel> mItemsList;

    private LatLng mCurrentLocation;

    private boolean mMarkerClickableEnabled = true;

    private ImageLoader mImageLoader;

    private ImageLoaderConfiguration mConfigs;

    private DisplayImageOptions mOptions;

    private LayoutInflater mInflater;

    private HashMap<Marker, ImageView> mMarkerViewMap;

    private int mFragmentLayoutId = R.layout.fragment_map;

    private int mMapFragmentId = R.id.map;

    private int mMarkerIconId = -1;

    @Override
    public void onAttach(Activity activity) {

        Logger.i("AbstractMapFragment.onAttach()");

        super.onAttach(activity);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Logger.i("AbstractMapFragment.onCreate()");

        Bundle args = getArguments();

        if (args != null && args.size() > 0) {
            mCurrentLocation = args.getParcelable(EXTRA_CURRENT_LOCATION);
        }

        mImageLoader = ImageLoader.getInstance();

        mConfigs = new ImageLoaderConfiguration.Builder(getActivity()
                .getApplicationContext()).memoryCacheSize(5 * 1024 * 1024)
                .discCacheSize(10 * 1024 * 1024).build();
        mImageLoader.init(mConfigs);

        mOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .resetViewBeforeLoading(true).cacheOnDisc(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        mInflater = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Logger.i("AbstractMapFragment.onCreateView()");

        View view = inflater.inflate(mFragmentLayoutId, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Logger.i("AbstractMapFragment.onViewCreated()");

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        Logger.i("AbstractMapFragment.onActivityCreated()");

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {

        Logger.i("AbstractMapFragment.onResume()");

        super.onResume();

        showMap();
    }

    /**
     * @param savedInstanceState
     */
    protected void showMap() {
        showProgress();

        FragmentManager fmanager = getFragmentManager();

        Fragment fragment = fmanager.findFragmentById(mMapFragmentId);

        if (fragment == null) {
            Logger.e("No fragment is set for holding Map");
            return;
        }

        SupportMapFragment supportMapfragment = (SupportMapFragment) fragment;

        mGoogleMap = supportMapfragment.getMap();

        mGoogleMap.getUiSettings().setCompassEnabled(true);

        mGoogleMap.setMyLocationEnabled(true);

        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

        MapInfoWindowAdapter infoWindowAdapter = new MapInfoWindowAdapter(
                mInflater, this);

        mGoogleMap.setInfoWindowAdapter(infoWindowAdapter);

        if (mMarkerClickableEnabled) {
            mGoogleMap.setOnInfoWindowClickListener(this);
        }

        if (mCurrentLocation != null) {

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    mCurrentLocation, 15));

            mGoogleMap
                    .animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }

        hideProgress();
    }

    public void hideProgress() {

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                View view = getView();

                if (view == null) {
                    return;
                }

                View progress = view.findViewById(R.id.progress);

                progress.setVisibility(View.GONE);
            }
        });
    }

    public void showProgress() {

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                View view = getView();

                if (view == null) {
                    return;
                }

                View progress = view.findViewById(R.id.progress);

                progress.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onStart() {

        Logger.i("AbstractMapFragment.onStart()");

        super.onStart();

        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());

        if (status != ConnectionResult.SUCCESS) {
            Toast.makeText(getActivity(), R.string.msg_no_google_play_service,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {

        Logger.i("AbstractMapFragment.onPause()");

        super.onPause();
    }

    @Override
    public void onStop() {

        Logger.i("AbstractMapFragment.onStop()");

        super.onStop();
    }

    @Override
    public void onDestroyView() {

        Logger.i("AbstractMapFragment.onDestroyView()");

        FragmentManager fmanager = getFragmentManager();

        Fragment fragment = fmanager.findFragmentById(mMapFragmentId);

        if (fragment != null) {
            fmanager.beginTransaction().remove(fragment).commit();
        }

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {

        Logger.i("AbstractMapFragment.onDestroy()");

        super.onDestroy();
    }

    @Override
    public void onDetach() {

        Logger.i("AbstractMapFragment.onDetach()");

        super.onDetach();
    }

    public List<? extends MapModel> getItemsList() {
        return mItemsList;
    }

    public void setItemsList(List<? extends MapModel> itemsList) {
        mItemsList = itemsList;

        if (mItemsList != null && mItemsList.size() > 0) {
            mMarkerViewMap = new HashMap<Marker, ImageView>(mItemsList.size());
        }
    }

    public HashMap<Marker, ImageView> getMarkerViewMap() {
        return mMarkerViewMap;
    }

    public void drawMarkers() {

        if (mItemsList == null || mItemsList.size() == 0) {

            hideProgress();

            return;
        }

        for (int i = 0; i < mItemsList.size(); i++) {

            MapModel model = mItemsList.get(i);

            Marker marker = createMarker(model);

            loadMarkerImage(model, marker);
        }
    }

    protected void loadMarkerImage(MapModel model, Marker marker) {

        if (mMarkerViewMap == null) {

            Logger.e("Failed to load marker model image due to the hashmap is not initialized");

            return;
        }

        View view = mInflater.inflate(R.layout.popup_map_info_window, null,
                false);

        final ImageView imageView = (ImageView) view
                .findViewById(R.id.img_thumbnail);

        String url;

        if (model.getThumbnail() != null) {

            url = model.getThumbnail();

            mImageLoader.displayImage(url, imageView, mOptions);

        } else {

            imageView.setImageResource(R.drawable.ic_launcher);
        }

        mMarkerViewMap.put(marker, imageView);
    }

    private Marker createMarker(MapModel model) {

        if (model == null || mGoogleMap == null) {
            return null;
        }

        String title = model.getTitle();

        String snippet = model.getSnippet();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(snippet)) {
            return null;
        }

        LatLng position = new LatLng(model.getLatitude(), model.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(position);

        markerOptions.title(title);

        markerOptions.snippet(snippet);

        if (mMarkerIconId > 0) {
            markerOptions.icon(BitmapDescriptorFactory
                    .fromResource(mMarkerIconId));
        }

        Marker marker = mGoogleMap.addMarker(markerOptions);

        return marker;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        String rawSnippet = marker.getSnippet();

        if (TextUtils.isEmpty(rawSnippet)) {
            return;
        }

        String[] items = rawSnippet.split(";SEP;");

        onInfoWindowClick(marker, items);
    }

    /**
     * This method will be used instead of onInfoWindowClick(String[] items)
     *
     * @param marker
     * @param items
     */
    public void onInfoWindowClick(Marker marker, String[] items) {

        // This method will be used instead of onInfoWindowClick(String[] items)

        onInfoWindowClick(items);
    }

    /**
     * This method will removed in next version 0.0.4
     *
     * @param items
     */
    @Deprecated
    public void onInfoWindowClick(String[] items) {
        // This method will removed in next version 0.0.4
    }

    public void clearMap() {

        if (mGoogleMap == null) {
            return;
        }

        mGoogleMap.clear();
    }

    /**
     * @return the mGoogleMap
     */
    public GoogleMap getGoogleMap() {
        return mGoogleMap;
    }

    public void setMarkerClickable(boolean enabled) {
        mMarkerClickableEnabled = enabled;
    }

    public void setFragmentLayoutId(int layoutId) {
        mFragmentLayoutId = layoutId;
    }

    public void setMapFragmentId(int fragementId) {
        mMapFragmentId = fragementId;
    }

    public void setMarkerIconId(int iconId) {
        mMarkerIconId = iconId;
    }

    public LatLng getCurrentLocation() {
        return mCurrentLocation;
    }

    public void setCurrentLocation(LatLng location) {
        mCurrentLocation = location;
    }

    public DisplayImageOptions getDisplayImageOptions() {
        return mOptions;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public LayoutInflater getLayoutInflater() {
        return mInflater;
    }
}

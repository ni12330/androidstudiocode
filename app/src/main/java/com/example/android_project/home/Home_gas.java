package com.example.android_project.home;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.Katec.GeoPoint;
import com.example.android_project.Katec.GeoTrans;
import com.example.android_project.Katec.Katec;
import com.example.android_project.Katec.OilAdapter;
import com.example.android_project.Katec.Oil_DTO;
import com.example.android_project.Katec.TranslateXY;
import com.example.android_project.Katec.TranslatexyVO;
import com.example.android_project.Katec.Weather;
import com.example.android_project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class Home_gas extends AppCompatActivity{

    Button btn_back;

    ListView listView;
    ArrayList<Oil_DTO> oillist;
    OilAdapter oilAdapter;



    SupportMapFragment mapFragment;
    GoogleMap map;

    String x_tude;    //X축
    String y_tude;   //Y축

    Double x;
    Double y;


    Spinner spinner;
    String oil_kind;

    MarkerOptions myMarker = null;

    String result = "";

    String oil_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_gas);
        //어뎁터 깡통만들기
        listView = findViewById(R.id.oil_list);
        oillist = new ArrayList<Oil_DTO>();
        oilAdapter = new OilAdapter(this, oillist, R.layout.oil_item);
        listView.setAdapter(oilAdapter);

        //뒤로 가기 버튼
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //지도 사용하기
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d("mainLmapcallback","GoogleMap is Ready");
                map = googleMap;
                try {
                    map.setMyLocationEnabled(true);
                }catch (SecurityException e){
                    Log.d("main :mapcallback2",e.getMessage());
                }
            }
        });//구글 맵 붙여주기

        MapsInitializer.initialize(this);


        //스피너 경유 휘발유
        final ArrayList arrayList = new ArrayList<>();
        arrayList.add("휘발유");
        arrayList.add("경유");

        spinner = findViewById(R.id.oil_spinner);


        ArrayAdapter<String> spinnerAdap = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(spinnerAdap);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), arrayList.get(i) + "가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
                oil_kind = arrayList.get(i).toString();
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        requestMyLocation();
    }



    private void requestMyLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            long minTime = 5000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            showCurrentLocation(latLng);


                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }

            );



            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            showCurrentLocation(latLng);


                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }

            );

        }catch (SecurityException e){
            Log.d("main:mylocation",e.getMessage());
        }


    }//메인

    private void showCurrentLocation(LatLng latLng) {
        /*  LatLng curPoint = new LatLng(location.getLatitude(),location.getLongitude());*/


        x = latLng.latitude;
        y = latLng.longitude;

        GeoPoint in_pt = new GeoPoint(y, x);

      //  Log.d("좌표 변환 되면 안되겠니?","geo in : xGeo="  + in_pt.getX() + ", yGeo=" + in_pt.getY());
        GeoPoint tm_pt = GeoTrans.convert(GeoTrans.GEO, GeoTrans.TM, in_pt);
      //  Log.d("좌표 변환 되면 안되겠니?","tm : xTM=" + tm_pt.getX() + ", yTM=" + tm_pt.getY());
        GeoPoint katec_pt = GeoTrans.convert(GeoTrans.TM, GeoTrans.KATEC, tm_pt);
      //  Log.d("좌표 변환 되면 안되겠니?","katec : xKATEC=" + katec_pt.getX() + ", yKATEC=" + katec_pt.getY());

        x_tude = String.valueOf(katec_pt.getX());
        y_tude = String.valueOf(katec_pt.getY());

        //현재내위치로 확대
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        //주유소 정보 받기
        Katec katec = new Katec(oillist, oilAdapter,x_tude,y_tude,oil_kind);
        ArrayList<Oil_DTO> dtos = null;
        try {
            dtos = katec.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("list size",dtos.size()+"");
        //현재위치 마커 찍기
        Location tagetlocation = new Location("");
        tagetlocation.setLatitude(x);
        tagetlocation.setLongitude(y);
        showMyMarker(tagetlocation);




        //주유소 위치를 받아서 마커 찍는다
        for (int i = 0; i < dtos.size();i++){
            Double goole_x = Double.valueOf(dtos.get(i).getGis_x());
            Double goole_y = Double.valueOf(dtos.get(i).getGis_y());
            //Log.d("마커좌표?","geo out : xGeo=" + goole_x + ", yGeo=" + goole_y);

            katec_pt = new GeoPoint(goole_x, goole_y);
            GeoPoint out_pt = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, katec_pt);
            //Log.d("구글 좌표 안되겠니?","geo out : xGeo=" + out_pt.getX() + ", yGeo=" + out_pt.getY());

            Double loastion_x = out_pt.getY();
            Double loastion_y = out_pt.getX();
            //Log.d("마커좌표?"+i,"geo out : xGeo=" + loastion_x + ", yGeo=" + loastion_y);

            Location teget = new Location("");
            teget.setLatitude(loastion_x);
            teget.setLongitude(loastion_y);
            //주유소 사진 추가하기
            String oil_name = dtos.get(i).getOil_name();
            String oil_cost = dtos.get(i).getOil_price();

            OILMarker(teget,oil_name,oil_cost);
        }
    }


    //내위치 찍기
    private void showMyMarker(Location location){
        //마커 찍는 방법
        if(myMarker == null){
            myMarker = new MarkerOptions();
            myMarker.position(new LatLng(location.getLatitude(),location.getLongitude()));

            myMarker.title("♬내 위치\n");
            myMarker.snippet("♬ GPS로 확인한 위치");
            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myMarker);
        }else {
            myMarker.position(new LatLng(location.getLatitude(),location.getLongitude()));
        }

    }

    //내위치 찍기
    private void OILMarker(Location location,String oil_name,String oil_cost){
        //마커 찍는 방법
        myMarker = new MarkerOptions();
        myMarker.position(new LatLng(location.getLatitude(),location.getLongitude()));

        myMarker.title(oil_name+"\n");
        myMarker.snippet("   ￦"+oil_cost+"원");
        myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark));
        map.addMarker(myMarker);


    }


}

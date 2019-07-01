package com.example.android_project.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.Common.DatePickerPop;
import com.example.android_project.Katec.Compy_oil;
import com.example.android_project.Katec.GeoPoint;
import com.example.android_project.Katec.GeoTrans;
import com.example.android_project.Katec.OilAdapter;
import com.example.android_project.Katec.Oil_DTO;
import com.example.android_project.Katec.Oil_record;
import com.example.android_project.R;
import com.example.android_project.chart.Ins;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.android_project.Login_user.LoginMemberDTO.car_mileage;

public class Home_ins extends AppCompatActivity{

    Button btn_back;
    Spinner oil_spinner;
    Spinner comp_spinner;
    Spinner mymileage_spinner;
    TextView mymileage;
    String oil_kind = "휘발유";
    ArrayList<Oil_DTO> oillist;
    String x_tude;    //X축
    String y_tude;   //Y축
    TextView oil_compname;
    ArrayList<Oil_DTO> dtos;
    OilAdapter oilAdapter;
    Double x;
    Double y;
    String compy_code = "";
    String result = "";
    TextView oil_price;
    Button calendar_btn;
    TextView now;
    TextView oil_liter;
    Button oil_insert;
    java.text.SimpleDateFormat tmpDateFormat;

    EditText oilCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ins);
        tmpDateFormat = new java.text.SimpleDateFormat("yyyy년M월dd일");
        oil_spinner = findViewById(R.id.oil_spinner);
        comp_spinner = findViewById(R.id.comp_spinner);
        oil_liter = findViewById(R.id.oil_liter);
        mymileage_spinner = findViewById(R.id.mymileage_spinner);
        oil_price = findViewById(R.id.oil_price);
        mymileage = findViewById(R.id.mymileage);
        btn_back = findViewById(R.id.btn_back);
        calendar_btn = findViewById(R.id.calendar_btn);
        oil_compname = findViewById(R.id.oil_compname);
        now = findViewById(R.id.now);
        String tonow = tmpDateFormat.format(new Date());
        now.setText(tonow);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        oillist = new ArrayList<Oil_DTO>();
        oilCost = findViewById(R.id.oilCost);
        oil_insert = findViewById(R.id.oil_insert);


        //인설트 하러 간다 진짜로 입력한다
        oil_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = String.valueOf(now.getText());
                String mileage = String.valueOf(mymileage.getText());
                if (mileage.equals("")) {
                    mileage = "0";
                }
                int mileage2 = Integer.parseInt(mileage);
                String price = String.valueOf(oil_price.getText()).trim();
                String liter = String.valueOf(oil_liter.getText());
                String cost = String.valueOf(oilCost.getText());
                int car_mileage2 = Integer.parseInt(car_mileage);
                String com_nm = String.valueOf(oil_compname.getText());

                // Toast.makeText(Home_ins.this, price, Toast.LENGTH_SHORT).show();
                //Toast.makeText(Home_ins.this, car_mileage2+"", Toast.LENGTH_SHORT).show();
                //Toast.makeText(Home_ins.this, mileage2+"", Toast.LENGTH_SHORT).show();

                if (mileage2 < car_mileage2 || mileage2 > car_mileage2 + 5000) {
                    Toast.makeText(Home_ins.this, "입력란을 확인하세요", Toast.LENGTH_SHORT).show();
                } else {
                    if (cost.equals("")) {
                        Toast.makeText(Home_ins.this, "주유금액을 확인해주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        Ins ins = new Ins(day, mileage, price, liter,com_nm);
                        try {
                            result = ins.execute().get();
                            if(result.contains("success")){
                                Toast.makeText(Home_ins.this, "등록성공", Toast.LENGTH_SHORT).show();
                                car_mileage = mileage;
                                finish();
                            }else {
                                Toast.makeText(Home_ins.this, "동록실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });





        //팝업형식의 달력
        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DatePickerPop.class);
                startActivityForResult(intent, 1);
            }
        });




        requestMyLocation();





        final ArrayList mileageList = new ArrayList<>();
        mileageList.add("자가 등록");
        mileageList.add("최근 기록");

        ArrayAdapter<String> spinnerAdap3 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,mileageList);

        mymileage_spinner.setAdapter(spinnerAdap3);
        mymileage_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mileageList.get(i).toString().equals("최근 기록")){
                    mymileage.setText(car_mileage);
                }else {
                    mymileage.setText("");
                }
                ((TextView)adapterView.getChildAt(0)).setTextSize(13);
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        oilCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String cost = String.valueOf(oilCost.getText()).trim();
                String price = String.valueOf(oil_price.getText()).trim();
                if(!price.equals("")||price.equals("0")) {
                    if (cost.length() >= 4) {
                        String oilprice = String.valueOf(oil_price.getText());

                        String liter01 = String.valueOf(Integer.parseInt(cost) / Integer.parseInt(oilprice));

                        //liter01 = df.format(Double.parseDouble(liter01));

                        oil_liter.setText(liter01);
                    } else {
                        oil_liter.setText("0");
                    }
                }else{
                    Toast.makeText(Home_ins.this, "금액과 L를 바르게 입력하여주세요", Toast.LENGTH_SHORT).show();

                }
            }
        });



     /*   ArrayAdapter<String> spinnerAdap3 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,mileageList);
        mymileage_spinner.setAdapter(spinnerAdap3);


*/

    }//onCreate()

    private void requestMyLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            long minTime = 50000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            Log.d("asdasd",latLng+"");
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


        //주유소 정보 받기
        Oil_record record = new Oil_record(oillist,x_tude,y_tude,oil_kind);

        //Toast.makeText(this, "주유소 갔다왓다", Toast.LENGTH_SHORT).show();
        try {
            dtos = record.execute().get();

            final ArrayList compList = new ArrayList<>();
            if(dtos.size()>1){
                for(int i = 0; i < dtos.size(); i++){
                    compList.add(dtos.get(i).getOil_name().trim());
                }
            }else{
                compList.add("없음");
            }


            ArrayAdapter<String> spinnerAdap2 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,compList);
            comp_spinner.setAdapter(spinnerAdap2);

            comp_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    view.setPadding(0, 0, 0, 0);
                    if(compList.get(i).toString().equals("없음")){
                        oil_compname.setText("-");
                        //oil_price.setText("0");

                    }else{
                        oil_compname.setText(dtos.get(i).getOil_name());
                        compy_code = dtos.get(i).getComp_code();
                        //주유소 금액 가져와요
                        Compy_oil oil = new Compy_oil(compy_code);

                        try {
                            result = oil.execute().get().trim();
                            //  Toast.makeText(Home_ins.this, result, Toast.LENGTH_SHORT).show();
                            changespinner(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });











        } catch (Exception e) {
            e.printStackTrace();
        }


        // Log.d("list size",oillist.size()+"");

    }

    public void changespinner(String result) {

        String[] sp = result.split("!!");

        final ArrayList oilcompy = new ArrayList<>();
        final ArrayList oilprice = new ArrayList<>();

        for(int i=0; i<sp.length; i++) {
            String oil_kind = sp[i].split(",")[0].trim();
            //  Toast.makeText(this, oil_kind, Toast.LENGTH_SHORT).show();
            if(oil_kind.equals("B027")){
                oilcompy.add("휘발유");
            }else if(oil_kind.equals("D047")){
                oilcompy.add("경유");
            }else if(oil_kind.equals("B034")){
                oilcompy.add("고급휘발유");
            }else if(oil_kind.equals("C004")){
                oilcompy.add("실내등유");
            }

            oilprice.add(sp[i].split(",")[1]);
        }


        ArrayAdapter<String> spinnerAdap = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,oilcompy);
        oil_spinner.setAdapter(spinnerAdap);
        oil_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                view.setPadding(0, 0, 0, 0);
                //Toast.makeText(getApplicationContext(), oilList.get(i) + "가 선택되었습니다.",Toast.LENGTH_SHORT).show();
                //oil_kind = oilList.get(i).toString();
                oil_price.setText(oilprice.get(i).toString());
                //String prodce = oilcompy.get(i).toString();




                ((TextView)adapterView.getChildAt(0)).setTextSize(15);
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }





    //팝업창 star 값 돌려받기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                String result = data.getStringExtra("result");
                if(result != null){
                    now.setText(result);
                }
                // Toast.makeText(this, result, Toast.LENGTH_SHORT).show();


                //getSupportFragmentManager().beginTransaction().replace(R.id.,garage).commit();
            }
        }
    }







}//Home_ins
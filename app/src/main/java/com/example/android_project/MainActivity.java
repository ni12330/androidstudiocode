package com.example.android_project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.Katec.TranslateXY;
import com.example.android_project.Katec.TranslatexyVO;
import com.example.android_project.Katec.Weather;
import com.example.android_project.Login_user.LoginMember;
import com.example.android_project.Member.MemberIdCheck;
import com.example.android_project.Member.MemberSignUp;
import com.example.android_project.Member.MemberSingIn;
import com.example.android_project.carInfo.CarSignUp;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.maps.model.LatLng;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;




import org.json.JSONObject;



import static com.example.android_project.Login_user.LoginMemberDTO.comp_id;
import static com.example.android_project.Login_user.LoginMemberDTO.rain;
import static com.example.android_project.Login_user.LoginMemberDTO.sky;
import static com.example.android_project.Login_user.LoginMemberDTO.temperature;
import static com.example.android_project.Login_user.LoginMemberDTO.weather;

public class MainActivity extends AppCompatActivity {

    Button btn_goSignup;
    Button btn_SignIn;

    TextView user_id, user_pw;
    String result = "";
    String id,pw,fbid,fbname,fbpw,fbtel;
    Double weather_x;
    Double weather_y;
    public static SessionCallback kakao;


    //카톡
    private Button btn_custom_login;
    //페이스북
    private CallbackManager callbackManager;
    LoginButton loginButton;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Log.d("날씨",weather);
        if(weather.contains("false")){
            requestMyLocation();
        }else{
            Log.d("날씨","후하후하");
        }

        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
*/

        checkDangerousPermissions();

        //페이스북 로그인동시에 회원가입까지
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("페이스북 ", "getUserId :"+loginResult.getAccessToken().getUserId());
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("페이스북",response.toString());
                        try{
                            Log.d("페이스북",object.getString("name"));
                            Log.d("페이스북",object.getString("id"));
                            fbid = object.getString("id");
                            fbpw = "asgsadf";
                            fbname = object.getString("name");
                            fbtel = "010-8855-8855";
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                request.executeAsync();
                snslogin(fbid,fbpw,fbname,fbtel);
            }
            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        //카톡로그인 동시에 회원가입까지
        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);
        kakao = new SessionCallback();
        Session.getCurrentSession().addCallback(kakao);
        btn_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, MainActivity.this);
            }
        });

        //그냥 로그인,회원가입
        btn_goSignup=findViewById(R.id.btn_goSignup);
        btn_SignIn=findViewById(R.id.btn_signIn);
        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = findViewById(R.id.user_id);
                user_pw = findViewById(R.id.user_pw);
                id = user_id.getText().toString();
                pw = user_pw.getText().toString();

                MemberSingIn member = new MemberSingIn(id, pw);
                try {
                    result = member.execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(result.contains("success")){
                    Toast.makeText(MainActivity.this, "로그인성공", Toast.LENGTH_SHORT).show();
                    LoginMember loginMember = new LoginMember(id);
                    try {
                        if(loginMember.execute().get().contains("success")){
                            Intent intent = new Intent(getApplicationContext(), Controller.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
                        }else {
                            if(comp_id==null){
                                Intent intent = new Intent(getApplicationContext(), CarSignUp.class);
                                intent.putExtra("user_id",id);
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }else{
                    Toast.makeText(MainActivity.this, "로그인실패", Toast.LENGTH_SHORT).show();
                    overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
                }
            }
        });

        btn_goSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        SignUp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
            }
        });

    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //카톡하기위한 이너클래스 무조건 이너클래스로만들어야됨 ㅅㅂ
    class SessionCallback implements ISessionCallback {

        public String id,pw,name,tel,result;

        // 로그인에 성공한 상태
        @Override
        public void onSessionOpened() {
            requestMe();

        }
        // 로그인에 실패한 상태
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }

        // 사용자 정보 요청
        public void requestMe() {
            // 사용자정보 요청 결과에 대한 Callback
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                // 세션 오픈 실패. 세션이 삭제된 경우,
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
                }

                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Log.e("SessionCallback :: ", "onSuccess");
                    id = String.valueOf(result.getId());
                    pw = "asgsfdqas";
                    name = result.getNickname();
                    tel = "010-2516-1612";
                    Log.d("Profile : ", id + "");
                    Log.d("Profile : ", pw + "");
                    Log.d("Profile : ", name + "");
                    Log.d("Profile : ", tel + "");

                    snslogin(id,pw,name,tel);
                }
            });
        }
    }
    public void snslogin(String id,String pw,String name,String tel){
        //아이디 유효성검사하는부분 검사한뒤 DB에 중복이없거나 유효성검사가 잘되면 success가 반환됨
        MemberIdCheck idCheck = new MemberIdCheck(id);
        try {
            result = idCheck.execute().get().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result.contains("success")) {
            //유효성검사후 회원가입을 하는부분
            MemberSignUp memberInsert = new MemberSignUp(id,pw,name,tel);
            memberInsert.execute();
        }

        LoginMember loginMember = new LoginMember(id);
        try {
            if(loginMember.execute().get().contains("success")){
                Intent intent = new Intent(getApplicationContext(), Controller.class);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
            }else {
                if(comp_id==null){
                    Intent intent = new Intent(getApplicationContext(), CarSignUp.class);
                    intent.putExtra("user_id",id);
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //좌표 받아오기
    private void requestMyLocation() {
        LocationManager manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try{
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            weather_x = latLng.latitude;
                            weather_y = latLng.longitude;

                            TranslateXY xy = new TranslateXY();
                            TranslatexyVO vo = xy.getTransXY(weather_x,weather_y);

                            String weather_xx = String.valueOf(vo.getX());
                            String weather_yy = String.valueOf(vo.getY());
                            Weather weather2 = new Weather(weather_xx,weather_yy);

                            try {
                                String[] sp =  weather2.execute().get().split(",");
                                if(sp.length>2) {
                                    rain = sp[0].trim();
                                    sky = sp[1].trim();
                                    temperature = sp[2].trim();
                                    weather = "true";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


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
                            weather_x = latLng.latitude;
                            weather_y = latLng.longitude;

                            TranslateXY xy = new TranslateXY();
                            TranslatexyVO vo = xy.getTransXY(weather_x,weather_y);
                            String weather_xx = String.valueOf(vo.getX());
                            String weather_yy = String.valueOf(vo.getY());
                            Weather weather2 = new Weather(weather_xx,weather_yy);

                            try {
                                String[] sp =  weather2.execute().get().split(",");
                                if(sp.length>1) {
                                    rain = sp[0].trim();
                                    sky = sp[1].trim();
                                    temperature = sp[2].trim();
                                    weather = "true";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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


}


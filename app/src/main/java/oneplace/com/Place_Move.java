package oneplace.com;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class Place_Move extends AppCompatActivity {

    TextView kn;
    TextView kb;
    TextView cb;
    TextView cn;
    TextView kkd;
    TextView jn;
    TextView jb;
    TextView kwd;
    TextView jjd;

    Dialog dialog03,dialog02,dialog01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_move);

        dialog03 = new Dialog(this);  //다이어로그 초기화
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 제거
        dialog03.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이어로그 배경 투명처리(이렇게해야 둥글게처리한거보임)
        dialog03.setContentView(R.layout.dialog_choice3);

        dialog02 = new Dialog(this);  //다이어로그 초기화
        dialog02.requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 제거
        dialog02.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이어로그 배경 투명처리(이렇게해야 둥글게처리한거보임)
        dialog02.setContentView(R.layout.dialog_choice2);

        dialog01 = new Dialog(this);  //다이어로그 초기화
        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 제거
        dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이어로그 배경 투명처리(이렇게해야 둥글게처리한거보임)
        dialog01.setContentView(R.layout.dialog_choice1);


        kn = findViewById(R.id.kn);
        kn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog03_1();
            }
        });

        kb = findViewById(R.id.kb);
        kb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog02_1();
            }
        });

        cb = findViewById(R.id.cb);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01_1();
            }
        });

        cn = findViewById(R.id.cn);
        cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog02_2();
            }
        });

        kkd = findViewById(R.id.kkd);
        kkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog03_2();
            }
        });

        jn = findViewById(R.id.jn);
        jn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog02_3();
            }
        });

        jb = findViewById(R.id.jb);
        jb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01_2();
            }
        });

        kwd = findViewById(R.id.kwd);
        kwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01_3();
            }
        });

        jjd = findViewById(R.id.jjd);
        jjd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01_4();
            }
        });
    }


    public void showDialog03_1(){
        dialog03.show();
        TextView choice1 = dialog03.findViewById(R.id.choice1);
        choice1.setText("부산");
        TextView choice2 = dialog03.findViewById(R.id.choice2);
        choice2.setText("울산");
        TextView choice3 = dialog03.findViewById(R.id.choice3);
        choice3.setText("외 경남");

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"부산에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(부산,울산-경남권)");
                intent.putExtra("Place_Chat_Main","채팅(부산,울산-경남권)");
                intent.putExtra("area","부산(51)");
                intent.putExtra("board_address","경상남도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog03.dismiss(); //다이어로그 닫기


            }
        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"울산에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(부산,울산-경남권)");
                intent.putExtra("Place_Chat_Main","채팅(부산,울산-경남권)");
                intent.putExtra("area","울산(52)");
                intent.putExtra("board_address","경상남도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog03.dismiss(); //다이어로그 닫기


            }
        });
        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"경남에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(부산,울산-경남권)");
                intent.putExtra("Place_Chat_Main","채팅(부산,울산-경남권)");
                intent.putExtra("area","경남(55)");
                intent.putExtra("board_address","경상남도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog03.dismiss(); //다이어로그 닫기


            }
        });


    }


    public void showDialog02_1(){
        dialog02.show();
        TextView choice1 = dialog02.findViewById(R.id.choice1);
        choice1.setText("대구");
        TextView choice2 = dialog02.findViewById(R.id.choice2);
        choice2.setText("외 경북");

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"대구에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(대구-경북권)");
                intent.putExtra("Place_Chat_Main","채팅(대구-경북권)");
                intent.putExtra("area","대구(53)");
                intent.putExtra("board_address","경상북도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog02.dismiss(); //다이어로그 닫기


            }
        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"경북에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(대구-경북권)");
                intent.putExtra("Place_Chat_Main","채팅(대구-경북권)");
                intent.putExtra("area","경북(54)");
                intent.putExtra("board_address","경상북도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog02.dismiss(); //다이어로그 닫기


            }
        });
    }

    public void showDialog01_1(){
        dialog01.show();
        TextView choice1 = dialog01.findViewById(R.id.choice1);
        choice1.setText("충북");

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"충북에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(충북권)");
                intent.putExtra("Place_Chat_Main","채팅(충북권)");
                intent.putExtra("area","충북(43)");
                intent.putExtra("board_address","충청북도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog01.dismiss(); //다이어로그 닫기


            }
        });

    }


    public void showDialog02_2(){
        dialog02.show();
        TextView choice1 = dialog02.findViewById(R.id.choice1);
        choice1.setText("대전");
        TextView choice2 = dialog02.findViewById(R.id.choice2);
        choice2.setText("외 충남");

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"대전에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(대전-충남권)");
                intent.putExtra("Place_Chat_Main","채팅(대전-충남권)");
                intent.putExtra("area","대전(42)");
                intent.putExtra("board_address","충청남도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog02.dismiss(); //다이어로그 닫기


            }
        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"충남에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(대전-충남권)");
                intent.putExtra("Place_Chat_Main","채팅(대전-충남권)");
                intent.putExtra("area","충남(41)");
                intent.putExtra("board_address","충청남도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog02.dismiss(); //다이어로그 닫기


            }
        });
    }



    public void showDialog03_2(){
        dialog03.show();
        TextView choice1 = dialog03.findViewById(R.id.choice1);
        choice1.setText("서울");
        TextView choice2 = dialog03.findViewById(R.id.choice2);
        choice2.setText("인천");
        TextView choice3 = dialog03.findViewById(R.id.choice3);
        choice3.setText("외 경기도");

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"서울에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(서울,인천-경기권)");
                intent.putExtra("Place_Chat_Main","채팅(서울,인천-경기권)");
                intent.putExtra("area","서울(02)");
                intent.putExtra("board_address","경기도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog03.dismiss(); //다이어로그 닫기


            }
        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"인천에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(서울,인천-경기권)");
                intent.putExtra("Place_Chat_Main","채팅(서울,인천-경기권)");
                intent.putExtra("area","인천(32)");
                intent.putExtra("board_address","경기도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog03.dismiss(); //다이어로그 닫기


            }
        });
        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"경기도에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(서울,인천-경기권)");
                intent.putExtra("Place_Chat_Main","채팅(서울,인천-경기권)");
                intent.putExtra("area","경기(31)");
                intent.putExtra("board_address","경기도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog03.dismiss(); //다이어로그 닫기


            }
        });

    }


    public void showDialog02_3(){
        dialog02.show();
        TextView choice1 = dialog02.findViewById(R.id.choice1);
        choice1.setText("광주");
        TextView choice2 = dialog02.findViewById(R.id.choice2);
        choice2.setText("외 전남");

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"광주에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(광주-전남권)");
                intent.putExtra("Place_Chat_Main","채팅(광주-전남권)");
                intent.putExtra("area","광주(62)");
                intent.putExtra("board_address","전라남도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog02.dismiss(); //다이어로그 닫기


            }
        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"전남에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(광주-전남권)");
                intent.putExtra("Place_Chat_Main","채팅(광주-전남권)");
                intent.putExtra("area","전남(61)");
                intent.putExtra("board_address","전라남도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog02.dismiss(); //다이어로그 닫기


            }
        });
    }



    public void showDialog01_2(){
        dialog01.show();
        TextView choice1 = dialog01.findViewById(R.id.choice1);
        choice1.setText("전북");

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"전북에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(전북권)");
                intent.putExtra("Place_Chat_Main","채팅(전북권)");
                intent.putExtra("area","전북(63)");
                intent.putExtra("board_address","전라북도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog01.dismiss(); //다이어로그 닫기


            }
        });

    }


    public void showDialog01_3(){
        dialog01.show();
        TextView choice1 = dialog01.findViewById(R.id.choice1);
        choice1.setText("강원도");

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"강원도에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(강원도)");
                intent.putExtra("Place_Chat_Main","채팅(강원도)");
                intent.putExtra("area","강원(33)");
                intent.putExtra("board_address","강원도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog01.dismiss(); //다이어로그 닫기

            }
        });

    }


    public void showDialog01_4(){
        dialog01.show();
        TextView choice1 = dialog01.findViewById(R.id.choice1);
        choice1.setText("제주도");

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Move.this, Place_Shop.class);
                Toast.makeText(getApplicationContext(),"제주도에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                intent.putExtra("board","자유 게시판(제주도)");
                intent.putExtra("Place_Chat_Main","채팅(제주도)");
                intent.putExtra("area","제주(64)");
                intent.putExtra("board_address","제주도");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                dialog01.dismiss(); //다이어로그 닫기


            }
        });
    }
}
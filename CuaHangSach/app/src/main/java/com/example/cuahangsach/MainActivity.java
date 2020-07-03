package com.example.cuahangsach;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ValueEventListener {
    // khai báo 3 gridview dùng chung cho menu Navigation Buttom
    GridView grDanhSachSach;
    GridView grDanhSachSach2;
    GridView grDanhSachSach3;

    // khai báo 3 adapter của gridview dùng chung trong 3 menu trang chủ và danh sách đặt và lịch sử
    SachAdapter2 sachAdapter2;
    SachAdapter1 sachAdapter1;
    SachAdapter2 sachAdapter3;

    // mảng dùng lưu các phần tử đối tượng sách trong mang tim kiem
    ArrayList<SachOnThi>arrayListSach=new ArrayList<SachOnThi>();

    //khai báo database firebase và đường dẫn
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //mảng dùng lưu vị trí đăt mua
    List<SachOnThi>list=new ArrayList<SachOnThi>();

    //mảng dùng lưu mảng đối tượng sách đã đặt trong firebase
    //danh sach
    ArrayList<SachOnThi>arrayListSach2=new ArrayList<SachOnThi>();

    ArrayList<SachOnThi>arrayListSach3=new ArrayList<SachOnThi>();
    //xoa
    ArrayList<SachOnThi>arrayListSach4=new ArrayList<SachOnThi>();

    // email đăng nhập
    String emailDangNhap="";

    // vị trí xóa trong danh sách dặt
    int position1=0;

    Button btnUser;

    //menu navigation Buttom trong danh sách đặt
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                //menu trang chủ, dùng setContentView() để trở về màn hình các cuốn sách
                case R.id.navigation_home:
                    setContentView(R.layout.activity_main);
                    addControls();
                    addEvents();
                    fakeData();
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    databaseReference=firebaseDatabase.getReference().child("List");
                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    return true;
                    //menu danh sách đặt
                case R.id.navigation_dashboard:

                    // gridview 2 dùng chung một gridview cùng id R.id.grDanhSachSach
                    grDanhSachSach2=(GridView)findViewById(R.id.grDanhSachSach);
                    sachAdapter2=new SachAdapter2(MainActivity.this,R.layout.itemrow1);
                    sachAdapter2.addAll(arrayListSach2);
                    grDanhSachSach2.setAdapter(sachAdapter2);
                    grDanhSachSach2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SachOnThi sachOnThi=sachAdapter2.getItem(position);
                            position1=position;
                            Toast.makeText(MainActivity.this,"So Luong: "+sachOnThi.getSoLuong()+",Dia Chi : "+sachOnThi.getDiaChiMua()
                                    +"Email :"+sachOnThi.getEmailDangNhap()+"vị trí "+position,Toast.LENGTH_LONG).show();

                        }
                    });
                    return true;
                    //menu đăng nhập đăng kí
                case R.id.navigation_notifications:
                    Intent intentChuyeSangDangNhap=new Intent(MainActivity.this,DangNhapActivity.class);
                    startActivity(intentChuyeSangDangNhap);
                    return true;
                    // menu lịch sử xóa
                case R.id.mnu_lichsu:
                    // gridview 3 dùng chung trong Navigation Buttom
                    grDanhSachSach3=(GridView) findViewById(R.id.grDanhSachSach);
                    sachAdapter3=new SachAdapter2(MainActivity.this,R.layout.itemrow1);
                    sachAdapter3.addAll(arrayListSach4);
                    grDanhSachSach3.setAdapter(sachAdapter3);
                    grDanhSachSach3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SachOnThi sachOnThi=sachAdapter3.getItem(position);
                            Toast.makeText(MainActivity.this,sachOnThi.getTenSach()+sachOnThi.getDiaChiMua(),Toast.LENGTH_LONG).show();
                        }
                    });
                return true;
            }
            return false;

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        fakeData();

        // nếu bật phần mềm khi đó danh sách đặt rỗng, khi đó hiển thị lời chào
        if(list.size()==0) {
            Toast.makeText(MainActivity.this, "Xin chào các bạn!", Toast.LENGTH_SHORT).show();
        }
        // khởi tạo đường dẫn và database
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("List");
        databaseReference.addValueEventListener(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void fakeData() {

        //thêm các  phần tử đối tượng sách vào Adapter
        //hai lenh nay dung xoa du lieu trong Adapter va mang khi dung setContenView quay tro ve man hinh trang chu
        sachAdapter1.clear();
        arrayListSach.clear();
        sachAdapter1.add(new SachOnThi(1,"Bất Đẳng Thức","",120000,"Giao Duc",0,R.drawable.batdangthuc,"",""));
        sachAdapter1.add(new SachOnThi(2,"Bất Phương Trình","",120000,"Giao Duc",0,R.drawable.batphuongtrinh,"",""));
        sachAdapter1.add(new SachOnThi(3,"Điểm 10 Toán","",120000,"Giao Duc",0,R.drawable.biquyetdat10montoan1,"",""));
        sachAdapter1.add(new SachOnThi(4,"Giải Tích","",120000,"Giao Duc",0,R.drawable.giaitich,"",""));
        sachAdapter1.add(new SachOnThi(5,"Hóa Học","",120000,"Giao Duc",0,R.drawable.hoahoc1,"",""));
        sachAdapter1.add(new SachOnThi(6,"Lịch Sử","",120000,"Giao Duc",0,R.drawable.lichsu1,"",""));
        sachAdapter1.add(new SachOnThi(7,"Ngữ Văn","",120000,"Giao Duc",0,R.drawable.nguvan1,"",""));
        sachAdapter1.add(new SachOnThi(8,"Ngữ Văn","",120000,"Giao Duc",0,R.drawable.nguvan2,"",""));
       sachAdapter1.add(new SachOnThi(9,"Tiếng Anh","",120000,"Giao Duc",0,R.drawable.tienganh1,"",""));
        sachAdapter1.add(new SachOnThi(10,"Tiếng Anh","",120000,"Giao Duc",0,R.drawable.tienganh2,"",""));
        sachAdapter1.add(new SachOnThi(11,"Tiếng Anh","",120000,"Giao Duc",0,R.drawable.tienganh3,"",""));
        sachAdapter1.add(new SachOnThi(12,"Tiếng Anh","",120000,"Giao Duc",0,R.drawable.tienganh4,"",""));
        sachAdapter1.add(new SachOnThi(13,"Toán","",120000,"Giao Duc",0,R.drawable.toan1,"",""));
        sachAdapter1.add(new SachOnThi(14,"Toán Cao Cấp","",120000,"Giao Duc",0,R.drawable.toancaocap,"",""));
       sachAdapter1.add(new SachOnThi(15,"Vật Lý Đại Cương","",120000,"Giao Duc",0,R.drawable.vatlydaicuong,"",""));
       // thêm các đối tượng sách vào mảng
       for(int i=0;i<sachAdapter1.getCount();i++){
            SachOnThi sachOnThi=sachAdapter1.getItem(i);
            arrayListSach.add(sachOnThi);
        }
    }

    private void addControls() {

        // gridview 1 dùng chung một gridview cùng id R.id.grDanhSachSach
        grDanhSachSach=(GridView)findViewById(R.id.grDanhSachSach);
        sachAdapter1=new SachAdapter1(MainActivity.this,R.layout.itemrow1);
        grDanhSachSach.setAdapter(sachAdapter1);
    }

    private void addEvents() {
        grDanhSachSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SachOnThi sachOnThi=sachAdapter1.getItem(position);
                moThongTin(sachOnThi);
            }
        });
    }
    // phương thức hiển thị layout chi tiết và đặt mua
    private void moThongTin(final SachOnThi sachOnThi) {
        setContentView(R.layout.itemrow2);
        final EditText edtDiaChiMua=(EditText) findViewById(R.id.edtDiaChi);
        ImageView imgHinhAnhSach2=(ImageView)findViewById(R.id.imgHinhAnhSach2);
        TextView txtTenSach2=(TextView)findViewById(R.id.txtTenSach2);
        TextView txtGiaSach2=(TextView)findViewById(R.id.txtGiasAch2);
        TextView txtNhaXuatBan2=(TextView)findViewById(R.id.txtNhaXuatBan2);
        TextView txtThongTin2=(TextView)findViewById(R.id.txtGioiThieuSach);
        Button btnMua=(Button)findViewById(R.id.btnMua);
        Button btnTroVe=(Button) findViewById(R.id.btnTroVe);
        
        imgHinhAnhSach2.setImageResource(sachOnThi.getHinhAnhSach());
        txtTenSach2.setText(sachOnThi.getTenSach());
        txtGiaSach2.setText(sachOnThi.getGiaSach()+"VND");
        txtNhaXuatBan2.setText(sachOnThi.getNhaXuatBan());
        txtThongTin2.setText(sachOnThi.getGioiThieuSach());
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nếu chưa đặt hàng thêm danh sách đặt để tiếp tục vị trí đặt tiếp theo
                if (list.size() == 0) {
                    list.addAll(arrayListSach3);
                }
                sachOnThi.setDiaChiMua(edtDiaChiMua.getText().toString());
                // nhận email từ màn hình dăng nhập
                    Intent intentNhanEmail = getIntent();
                    emailDangNhap = intentNhanEmail.getStringExtra("emailDangNhap");
                sachOnThi.setEmailDangNhap(emailDangNhap);
                //nếu có email mới thêm đối tượng sách, nếu không hiển thị thông báo
                    if (emailDangNhap != null) {
                            sachOnThi.setSoLuong(sachOnThi.getSoLuong() + 1);
                            list.add(sachOnThi);
                            Toast.makeText(MainActivity.this,"So Luong"+sachOnThi.getSoLuong(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Bạn Phải Đăng Nhập!", Toast.LENGTH_LONG).show();

                    }
                    // thêm mảng đặt vào cơ sở dữ liệu
                    databaseReference.setValue(list);



            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                addControls();
                addEvents();
                fakeData();
                firebaseDatabase=FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference().child("List");
                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mnu_search,menu);

        MenuItem mnusearch=menu.findItem(R.id.mnu_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(mnusearch);
        MenuItemCompat.setOnActionExpandListener(mnusearch, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<SachOnThi>dsTim=new ArrayList<SachOnThi>();
                for(SachOnThi sachOnThi1:arrayListSach){
                    //tìm theo tên sách
                    if(sachOnThi1.getTenSach().contains(newText)) {
                        dsTim.add(sachOnThi1);
                    }
                }
                    // xóa Adapter danh sách tìm
                    sachAdapter1.clear();
                //thêm mảng tìm thấy vào Adapter
                    sachAdapter1.addAll(dsTim);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnu_thoat:
                // thoát toàn bộ trương trình
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                finish();
                break;
            case R.id.mnu_XoaItem:
                    // lấy vị trí position1 trong Adapter danh sách đặt
                    SachOnThi sachOnThiXoa = sachAdapter2.getItem(position1);
                    sachAdapter2.remove(sachOnThiXoa);
                    // do mảng trong cơ sở dữ liệu firebase lấy theo mảng trong gridview nên có vị trí giống nhau
                    databaseReference.child(sachOnThiXoa.getId() + "").child("tenSach").setValue("Đã xóa!");
                    list.clear();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        // lấy email từ màn hình đăng nhập
        Intent intentNhanEmail=getIntent();
        String emailDangNhap2=intentNhanEmail.getStringExtra("emailDangNhap");
        ArrayList<SachOnThi> sachOnThi3=new ArrayList<SachOnThi>();
        ArrayList<SachOnThi> sachOnThi4=new ArrayList<SachOnThi>();
        ArrayList<SachOnThi> sachOnThi5=new ArrayList<SachOnThi>();
        // các phần tử trong cơ sở dữ liệu firebase và ép kiểu về đối tượng sách ôn thi
        Iterable<DataSnapshot> dataSnapshots=dataSnapshot.getChildren();
        int i=-1;
        for(DataSnapshot dataSnapshot1:dataSnapshots) {
            i=i+1;
            SachOnThi sachOnThi = dataSnapshot1.getValue(SachOnThi.class);
            sachOnThi.setId(i);
            sachOnThi4.add(sachOnThi);
            // nếu email đã có thì mới thêm phần tử sách theo email tương ứng vào danh sách đặt theo user
            if((emailDangNhap2!=null)){
                if((!sachOnThi.getTenSach().contains("Đã xóa!"))&&sachOnThi.getEmailDangNhap().contains(emailDangNhap2)){
                        sachOnThi3.add(sachOnThi);
                }
                // nếu đã xóa đối tượng thì thêm đối tượng xóa vào lịch sử
                if(sachOnThi.getTenSach().contains("Đã xóa!")&&(sachOnThi.getEmailDangNhap().contains(emailDangNhap2))){
                    sachOnThi5.add(sachOnThi);
                }

            }

        }
        // thêm danh sách đặt theo user
        arrayListSach2.clear();
        arrayListSach2.addAll(sachOnThi3);

        arrayListSach3.clear();
        arrayListSach3.addAll(sachOnThi4);
        // thêm danh sách lịch sử xóa theo user
        arrayListSach4.clear();
        arrayListSach4.addAll(sachOnThi5);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
}

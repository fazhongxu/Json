package xxl.com.json.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import xxl.com.json.R;
import xxl.com.json.bean.User;
import xxl.com.json.gen.GreenDaoManager;
import xxl.com.json.gen.UserDao;
import xxl.com.json.ui.base.BaseActivity;

public class GreenDaoTestActivity extends BaseActivity implements View.OnClickListener{

    private Button mBtnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao_test);
        
        initView();

    }

    private UserDao getUserDao(){
        return GreenDaoManager.getInstance().getDaoSession().getUserDao();
    }


    protected void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //增
       // insert();
        //删
       // delete();
        //改
       // update();
        //查
        select();
    }

    private void select() {
        List<User> users = getUserDao().loadAll();
        Toast.makeText(this, ""+users.size(), Toast.LENGTH_SHORT).show();
    }

    private void update() {
        List<User> users = getUserDao().loadAll();
        User user = new User(users.get(0).getId(), 22, "改名为Tony");
        getUserDao().update(user);
    }

    private void delete() {
        getUserDao().deleteByKey(1L);
    }

    private void insert() {
        User hello = new User(null, 18, "hello");
        getUserDao().insert(hello);
    }
}

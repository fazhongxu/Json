package xxl.com.json.gen;

import xxl.com.json.App;

/**
 * Created by xxl on 2018/1/2.
 * GreenDao管理类
 */

public class GreenDaoManager {

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private GreenDaoManager() {
        init();
    }

    private void init() {
        DaoMaster.DevOpenHelper daogreen = new DaoMaster.DevOpenHelper(App.getInstance().getContext(), "daogreen");

        mDaoMaster = new DaoMaster(daogreen.getReadableDatabase());

        mDaoSession = mDaoMaster.newSession();

    }

    private static class SingleInstanceHolder {
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
    }

    public static GreenDaoManager getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}

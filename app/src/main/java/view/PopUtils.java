package view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.zhangyang.photoselectdemo.R;

import java.util.List;

import adapter.PictureAlbumDirectoryAdapter;
import adapter.PopAdapter;
import bean.LocalMediaFolder;

/**
 * Created by zhangyang on 17/3/14.
 */

public class PopUtils implements PopAdapter.PopItemClickListener {
    private Activity activity;
    private View view;
    private List<LocalMediaFolder> list;
    private PictureAlbumDirectoryAdapter.OnItemClickListener onItemClickListener;
    private PopupWindow window;


    public PopUtils(Activity activity, View view, List<LocalMediaFolder> list) {
        this.activity = activity;
        this.view = view;
        this.list = list;
        background(activity, 0.5f);

        View content = activity.getLayoutInflater().inflate(R.layout.pop_window_list, null);
        window = new PopupWindow(content);
        setView(content, view, list);
    }


    private void setView(View content, View view, List<LocalMediaFolder> list) {

        ListView listview = (ListView) content.findViewById(R.id.lv_selection);
        PopAdapter adapter = new PopAdapter(activity, list);
        listview.setAdapter(adapter);
        adapter.setPopItemClickListener(this);

        window.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        window.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);

        //   设置背景颜色
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e0F8F8F8")));
        //   设置可以获取焦点
        window.setFocusable(true);
        //  设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        //  更新popupwindow的状态
        window.update();
        //  以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(view, 0, 30);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                background(activity, 1.0f);
            }
        });
    }

    private void background(Activity activity, float f) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = f; //0.0-1.0
        activity.getWindow().setAttributes(lp);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void OnClick(LocalMediaFolder folder) {
        Log.e("asd", "123123123");
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(folder.getName(), folder.getImages());
            window.dismiss();
        }

    }

    public void setOnItemClickListener(PictureAlbumDirectoryAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
